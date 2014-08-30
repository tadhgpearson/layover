/**
 * 
 */
package sandbox;

import java.util.ArrayList;
import java.util.List;

import model.SearchResult;
import model.Segment;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import play.libs.ws.WS;
import play.libs.ws.WSRequestHolder;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author tpearson
 *
 *	Connector class to perform flight searches on the Travel Innovation Sandbox APIs
 */
public class Search {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = ISODateTimeFormat.dateHourMinute();
	private static final DateTimeFormatter DATE_FORMAT = ISODateTimeFormat.date();
	
	static public List<SearchResult> flights(String origin, String destination, DateTime departure){
		WSRequestHolder rq = buildRequest(origin, destination, departure);

		JsonNode rs = rq.get().get(18000).asJson();
		
		List<SearchResult> results = parseResponse(rs, origin, destination);
		CollectionUtils.filter(results, new StopOverPredicate());
		
		return results;
	}

	static List<SearchResult> parseResponse(JsonNode rs, String rqOrigin, String rqDestination) {
		String currency = rs.get("currency").asText();
		
		List<SearchResult> results = new ArrayList<SearchResult>();
		for(JsonNode r : rs.get("results")){
			String price = r.get("fare").get("total_price").asText();
			
			for(JsonNode itin : r.get("itineraries")){
				List<Segment> segs = new ArrayList<Segment>();

				JsonNode flightList = itin.path("outbound").get("flights");
				for(JsonNode flight : flightList){
					Segment seg = parseFlight(flight);
					segs.add(seg);
				}

				if(segs.size() > 0){
					SearchResult result = new SearchResult(rqOrigin, rqDestination, segs, price, currency);
					results.add(result);
				}
			}
		}
		return results;
	}

	private static Segment parseFlight(JsonNode flight) {
		String departs = flight.get("departs_at").asText();
		String airline = flight.get("operating_airline").asText();
		String flightNumber = flight.get("flight_number").asText();
		String arrives = flight.get("arrives_at").asText();
		String origin = flight.get("origin").get("airport").asText();
		String destination = flight.get("destination").get("airport").asText();
		DateTime departsAt = DATE_TIME_FORMAT.parseDateTime(departs);
		DateTime arrivesAt = DATE_TIME_FORMAT.parseDateTime(arrives);
		Segment seg = new Segment(airline, flightNumber, departsAt, origin, arrivesAt, destination);
		return seg;
	}

	private static WSRequestHolder buildRequest(String origin,
			String destination, DateTime departure) {
		WSRequestHolder rq = WS.url(Keys.searchURL());
		rq.setQueryParameter("apikey", Keys.sandboxKey());
		rq.setQueryParameter("origin", origin);
		rq.setQueryParameter("destination", destination);
		rq.setQueryParameter("departure_date", DATE_FORMAT.print(departure));
		rq.setQueryParameter("adults", "1");
		rq.setQueryParameter("currency", "USD");
		return rq;
	}

}

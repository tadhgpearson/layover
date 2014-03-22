/**
 * 
 */
package rometorio;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import model.SearchResult;
import model.Segment;

import org.apache.commons.collections4.CollectionUtils;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import play.libs.WS;
import play.libs.WS.WSRequestHolder;

import com.fasterxml.jackson.databind.JsonNode;

/**
 * @author tpearson
 *
 *	Connector class to perform flight searches on RomeToRio
 */
public class Search {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyMMddHHmm");
	private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyMMdd");
	
	static public List<SearchResult> flights(String origin, String destination, DateTime departure, DateTime ret){
		WSRequestHolder rq = buildRequest(origin, destination, departure, ret);

		JsonNode rs = rq.get().get(18000).asJson();
		
		List<SearchResult> results = parseResponse(rs, origin, destination);
		CollectionUtils.filter(results, new StopOverPredicate());
		
		return results;
	}

	static List<SearchResult> parseResponse(JsonNode rs, String rqOrigin, String rqDestination) {
		List<SearchResult> results = new ArrayList<SearchResult>(); // fakeResults();
		for(JsonNode rec : rs.get("recs").get("rec")){
			List<Segment> segs = new ArrayList<Segment>();
			
			//Navigate tedious structure dirtily
			Iterator<JsonNode> solutions = rec.get("sols").get("sol").iterator();
			if(solutions.hasNext()){
				JsonNode sol = solutions.next();
				if(sol != null && sol.hasNonNull("outbound")){
					JsonNode flights = sol.get("outbound").get("flights");
					if(flights != null && flights.hasNonNull("flight")){
						for(JsonNode flight : flights.get("flight")){
							if(flight.hasNonNull("dd")){
								Segment seg = parseFlight(flight);
								segs.add(seg);
							}
						}
					}
				}
			}
			
			String price = rec.get("prices").get("price").get("fare").asText();
			if(segs.size() > 0){
				SearchResult result = new SearchResult(rqOrigin, rqDestination, segs, price, "USD");
				results.add(result);
			}
		}
		return results;
	}

	private static Segment parseFlight(JsonNode flight) {
		String departs = flight.get("dd").asText() + flight.get("dtime").asText();
		String airline = flight.get("marketing").asText();
		String flightNumber = flight.get("nb").asText();
		String arrives = flight.get("ad").asText() + flight.get("atime").asText();
		String origin = flight.get("oport").asText();
		String destination = flight.get("dport").asText();
		DateTime departsAt = DATE_TIME_FORMAT.parseDateTime(departs);
		DateTime arrivesAt = DATE_TIME_FORMAT.parseDateTime(arrives);
		Segment seg = new Segment(airline, flightNumber, departsAt, origin, arrivesAt, destination);
		return seg;
	}

	private static List<SearchResult> fakeResults() {
		DateTime departs1 = new DateTime(2014, 4, 15, 21, 45);
		DateTime arrives1 = new DateTime(2014, 4, 16, 10, 45);
		Segment outSeg1 = new Segment("LX", "53", departs1, "BOS", arrives1, "ZRH");
		Segment outSeg2 = new Segment("LX", "1324", new DateTime(2014, 4, 16, 21, 0), "ZRH", new DateTime(2014, 4, 17, 2, 20), "DME");
		List<Segment> out = Arrays.asList(outSeg1, outSeg2);
		SearchResult r = new SearchResult("BOS", "MOW", out, "385.00", "USD");
		List<SearchResult> results = Arrays.asList(r, r);
		return results;
	}

	private static WSRequestHolder buildRequest(String origin,
			String destination, DateTime departure, DateTime ret) {
		WSRequestHolder rq = WS.url(Keys.searchURL());
		rq.setQueryParameter("apikey", Keys.skyscannerKey());
		rq.setQueryParameter("origin", origin);
		rq.setQueryParameter("destination", destination);
		rq.setQueryParameter("departureDate", DATE_FORMAT.print(departure));
		rq.setQueryParameter("returnDate", DATE_FORMAT.print(ret));
		rq.setQueryParameter("adtNb", "1");
		rq.setQueryParameter("recNb", "499");
		return rq;
	}

}

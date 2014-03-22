/**
 * 
 */
package model;

import java.util.List;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;



/**
 * @author tpearson
 *
 */
public class SearchResult {
	
	String origin;
	String destination;
	
	List<Segment> outbound;
	
	String price;
	String currency;
	
	public SearchResult(String origin, String destination, List<Segment> outbound, String price, String currency) {
		super();
		this.origin = origin;
		this.destination = destination;
		this.outbound = outbound;
		this.price = price;
		this.currency = currency;
	}

	public JsonNode toJSON() {
		ObjectNode out = Json.newObject();
		out.put("origin", origin);
		out.put("destination", destination);
		ArrayNode segments = out.putArray("segments");
		for (Segment s : outbound) {
			segments.add(s.toJSON());
		}
		out.put("price", price);
		out.put("currency", currency);
		return out;
	}
	
}

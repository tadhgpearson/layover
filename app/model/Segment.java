/**
 * 
 */
package model;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import play.libs.Json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * @author tpearson
 *
 */
public class Segment {
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-mm-dd'T'HH:MM");
	String airline;
	String flightNumber;
	DateTime departs;
	String origin;
	DateTime arrives;
	String destination;
	
	

	public Segment(String airline, String flightNumber, DateTime departs,
			String origin, DateTime arrives, String destination) {
		super();
		this.airline = airline;
		this.flightNumber = flightNumber;
		this.departs = departs;
		this.origin = origin;
		this.arrives = arrives;
		this.destination = destination;
	}



	public JsonNode toJSON() {
		ObjectNode out = Json.newObject();
		out.put("airline", airline);
		out.put("flight_number", flightNumber);
		out.put("departs", DATE_TIME_FORMAT.print(departs));
		out.put("origin", origin);
		out.put("arrives", DATE_TIME_FORMAT.print(arrives));
		out.put("destination", destination);
		return out;
	}


}

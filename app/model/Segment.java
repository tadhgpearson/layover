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
	
	private static final DateTimeFormatter DATE_TIME_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd'T'HH:mm");
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



	public String getAirline() {
		return airline;
	}



	public String getFlightNumber() {
		return flightNumber;
	}



	public DateTime getDeparts() {
		return departs;
	}



	public String getOrigin() {
		return origin;
	}



	public DateTime getArrives() {
		return arrives;
	}



	public String getDestination() {
		return destination;
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



	@Override
	public String toString() {
		return "Segment [airline=" + airline + ", flightNumber=" + flightNumber
				+ ", departs=" + departs + ", origin=" + origin + ", arrives="
				+ arrives + ", destination=" + destination + "]";
	}

	
}

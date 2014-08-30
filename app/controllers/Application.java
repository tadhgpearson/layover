package controllers;

import java.util.List;

import model.SearchResult;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import sandbox.Search;
import views.html.index;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Application extends Controller {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormat.forPattern("yyyy-MM-dd");

	public static Result index() {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result flightSearch(String origin, String destination, String departureDate){
    	ObjectNode out = Json.newObject();
    	DateTime departs = DATE_FORMAT.parseDateTime(departureDate);
		List<SearchResult> results = Search.flights(origin, destination, departs);
    	ArrayNode aResults = out.putArray("results");
		for (SearchResult r : results) {
			aResults.add(r.toJSON());
		}
		return ok(out);
    }

}

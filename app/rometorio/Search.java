/**
 * 
 */
package rometorio;

import java.util.Arrays;
import java.util.List;

import model.SearchResult;
import model.Segment;

import org.joda.time.DateTime;

import play.libs.WS;
import play.libs.WS.WSRequestHolder;

/**
 * @author tpearson
 *
 *	Connector class to perform flight searches on RomeToRio
 */
public class Search {
	
	static final private String KEY = "PMrNXeaI";
	
	static public List<SearchResult> flights(String origin, String destination, DateTime departure, DateTime ret){
		WSRequestHolder rq = WS.url("http://free.rome2rio.com/api/1.2/xml/Search");
		rq.setQueryParameter("key", KEY);
		rq.setQueryParameter("oName", origin);
		rq.setQueryParameter("dName", destination);
		//
		
		DateTime departs1 = new DateTime(2014, 4, 15, 21, 45);
		DateTime arrives1 = new DateTime(2014, 4, 16, 10, 45);
		Segment outSeg1 = new Segment("LX", "53", departs1, "BOS", arrives1, "ZRH");
		Segment outSeg2 = new Segment("LX", "1324", new DateTime(2014, 4, 16, 21, 0), "ZRH", new DateTime(2014, 4, 17, 2, 20), "DME");
		List<Segment> out = Arrays.asList(outSeg1, outSeg2);
		SearchResult r = new SearchResult("BOS", "MOW", out, "385.00", "USD");
		return Arrays.asList(r, r);
	}

}

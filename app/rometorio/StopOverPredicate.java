package rometorio;

import java.util.List;

import model.SearchResult;
import model.Segment;

import org.apache.commons.collections4.Predicate;
import org.joda.time.DateTime;
import org.joda.time.Duration;



public class StopOverPredicate implements Predicate<SearchResult> {

	public boolean evaluate(SearchResult results) {
		boolean out;
		List<Segment> segs = results.getOutbound();
		if(segs.size() == 1){
			out = true;
		}
		else if(segs.size() == 2){
			DateTime arrives = segs.get(0).getArrives();
			DateTime departs = segs.get(1).getDeparts();
			Duration stopoverDuration = new Duration(arrives, departs);
			out = Duration.standardHours(4).isShorterThan(stopoverDuration);
		}
		else{
			out = false;
		}
		return out;
	}



}

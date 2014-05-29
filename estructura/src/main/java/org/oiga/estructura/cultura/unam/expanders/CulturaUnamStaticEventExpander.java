package org.oiga.estructura.cultura.unam.expanders;

import java.util.Date;
import java.util.List;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.oiga.estructura.expanders.EventExpander;
import org.oiga.estructura.parser.CulturaUnamDateParser;
import org.oiga.estructura.services.VenueNameService;
import org.oiga.estructura.utils.KeywordUtils;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.SimpleVenue;
import org.oiga.model.entities.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CulturaUnamStaticEventExpander extends EventExpander{
	public static Logger logger = LoggerFactory.getLogger(CulturaUnamStaticEventExpander.class);
	private static final int THRESHOLD = 3;
	private int okCounter = 0;
	private int errorCounter = 0;
	private VenueNameService nameService = new VenueNameService();
	
	@Override
	public void expandEvent(Event e) {
		tagExpander(e);
		venueExpander(e);
	}
	
	private void tagExpander(Event e){
		String descipcion = e.getDescription();
		List<Tag> tags = KeywordUtils.extractKeywords(descipcion, THRESHOLD);
		
		KeywordUtils.addTag(tags, "cultura-unam");
		
		e.getTags().addAll(tags);
	}
	
	public void venueExpander(Event e){
		SimpleVenue v = nameService.findByName(e.getLocation());
		e.setVenue(v);
	}
	
	@Deprecated
	private void dateExpander(Event e){
		String hoursDetails = e.getHoursDetails();
		try{
			List<Interval> intervals = CulturaUnamDateParser.parse(hoursDetails); 
			List<String> hours = CulturaUnamDateParser.extractHours(intervals); 
			e.setStartDate(intervals.get(0).getStart().toDate());
			e.setEndDate(intervals.get(intervals.size() - 1).getEnd().toDate());
			e.setHours(hours);
			okCounter++;
		}catch(Exception ex){
			logger.warn("Parece que hubo un error al parsear las fechas "
					+"'"+hoursDetails+"',\n"
					+"Causa:"+ex.toString());
			e.setStartDate(new Date());
			e.setEndDate(new LocalDate().dayOfMonth().withMaximumValue().toDate());
			errorCounter++;
		}
	}
	
	public Integer[] getDateParsingResutl(){
		return new Integer[]{okCounter, errorCounter};
	}
}

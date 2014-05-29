package org.oiga.estructura.analysis;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.oiga.estructura.parser.CulturaUnamDateParser;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.EventCategory;
import org.oiga.model.entities.SimpleVenue;
import org.oiga.model.services.SimpleVenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class CulturaUnamRepositoryAnalyser implements AnalysisEngine<Event>{
	static Logger logger = LoggerFactory.getLogger(CulturaUnamRepositoryAnalyser.class);
	
	@Autowired
	private SimpleVenueService simpleVenueService;
	
	public List<Event>  analyse(List<Map<String,String>> data)
	{
		List<Event> eventos = new ArrayList<>();
		for(Map<String,String> d: data){
			Event e = new Event();
			EventCategory ec = new EventCategory();
			SimpleVenue sv = null;
			try{
				sv = simpleVenueService.loadExternal(d.get("recinto"), "Ciudad de Mexico");
				e.setLocation(d.get("recinto"));
			}catch(Exception ex){
				logger.warn("Parece que hubo un error al cargar la ubicacion "
						+"'"+d.get("recinto")+"',\n"
						+"Causa:"+ex.getMessage());
				continue;
			}
			try{
				List<Interval> intervals = CulturaUnamDateParser.parse(d.get("horarios")); 
				e.setStartDate(intervals.get(0).getStart().toDate());
				e.setEndDate(intervals.get(intervals.size() - 1).getEnd().toDate());
				List<String> hours = new ArrayList<>();
				for(Interval s:intervals){
					hours.add(s.toString());
				}
				e.setHours(hours);
			}catch(Exception ex){
				logger.warn("Parece que hubo un error al parsear las fechas "
						+"'"+d.get("horarios")+"',\n"
						+"Causa:"+ex.getMessage());
				e.setStartDate(new Date());
				e.setEndDate(new LocalDate().dayOfMonth().withMaximumValue().toDate());
			}
			ec.setName(d.get("tipo-evento"));
			ec.setIcon("http://");
			e.setName(d.get("titulo"));
			//e.setCategory(ec);
			/*FIXME: quitar acento de descripcion*/
			e.setDescription(d.get("descripción"));
			e.setHost(d.get("organiza"));
			e.setVenue(sv);
//			e.setTags(Arrays.asList(new String[]{"cultura","UNAM", ec.getName()}));
			e.setUrl("http://www.cultura.unam.mx/");
			eventos.add(e);
		}
		logger.info("Se analizaron '"+eventos.size()+"' eventos");
		return eventos;
	}
}

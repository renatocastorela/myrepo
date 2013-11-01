package org.oiga.web.controllers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.oiga.model.entities.Event;
import org.oiga.model.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/events")
public class EventController {
	static Logger logger = LoggerFactory.getLogger(EventController.class);
	
	@Autowired
	private EventRepository eventRepository;
	
	@RequestMapping( value="find/{lt}/{ln}", method=RequestMethod.GET)
	public @ResponseBody  List<Event> findWithinDistance(@PathVariable double lt,@PathVariable double ln)
	{
		List<Event> events = new ArrayList<Event>();
		String function = String.format(EventRepository.WITHIN_DISTANCE, lt, ln, 25.0);
		logger.debug("Argumentos de entrada "+lt+", "+ln+" formato "+ function);
		
		Iterable<Event> result = eventRepository.getLocation(function);
		for(Event e:result){
			events.add(e);
		}
		return events;
		
	}
	
	@RequestMapping( value="search/like/{query}", method=RequestMethod.GET)
	public @ResponseBody  List<Event> likeSearch(@PathVariable String query)
	{
		List<Event> events = new ArrayList<Event>();
		StringBuilder expandedQuery = new StringBuilder();
		Iterator<String> terms = Arrays.asList(query.split(" ")).iterator();
	
		while(terms.hasNext()){
			String term = terms.next();
			expandedQuery.append("name:").append(term);
			if(terms.hasNext() == false){
				expandedQuery.append("*");
			}else{
				expandedQuery.append(" AND ");
			}
		}
		logger.debug("Query: "+expandedQuery.toString());
		
		Iterable<Event> result = eventRepository.findAllByQuery("name", expandedQuery.toString());
		for(Event e:result)
		{
			events.add(e);
		}
		return events;
		
	}
	
	@RequestMapping( value="search/exact/{query}", method=RequestMethod.GET)
	public @ResponseBody  List<Event> exactSearch(@PathVariable String query)
	{
		List<Event> events = new ArrayList<Event>(); 
		//EndResult<Event> result = eventRepository.findAllByPropertyValue("name", query);
		EndResult<Event> result = eventRepository.findAllByName(query);
		for(Event e:result)
		{
			events.add(e);
		}
		return events;
		
	}
	
	@RequestMapping( value="search/lucene/{query}", method=RequestMethod.GET)
	public @ResponseBody  List<Event> luceneSearch(@PathVariable String query)
	{
		List<Event> events = new ArrayList<Event>();
		Iterable<Event> result = eventRepository.findByNameLike(query, new PageRequest(0, 10));
		for(Event e:result)
		{
			events.add(e);
		}
		return events;
		
	}
}

package org.oiga.model.repositories;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.EventCategory;
import org.oiga.model.entities.SimpleVenue;
import org.oiga.model.services.SimpleVenueService;
import org.oiga.vertex.services.FoursquareVenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.foyt.foursquare.api.entities.CompactVenue;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:db-context.xml"})
public class EventRepositoryTest {
	private static final Logger logger = LoggerFactory
			.getLogger(EventRepositoryTest.class.getName());
	private Event event = new Event();
	private FoursquareVenueService venueService = new FoursquareVenueService();
	@Autowired 
	private EventRepository eventRepository;
	
	
	
	public void setUp(){
		EventCategory category = new EventCategory();
		category.setName("Artes");
		category.setIcon("http://icon");
		CompactVenue compactVenue = venueService.findOneByName( "Javier Barros Sierra", "Ciudad de Mexico");
		SimpleVenue simpleVenue = SimpleVenueService.toSimpleVenue(compactVenue);
		//event.setCategory(category);
		event.setVenue(simpleVenue);
		event.setDescription("Exposicion interesante");
		event.setEndDate(new Date());
		event.setHost("Facultad de Ingenieria");
		event.setHours(Arrays.asList(new String[]{"11:30/12:30"}));
		event.setName("Exposicion de Arte Moderno y Sueco");
		event.setStartDate(new Date());
//		event.setTags(Arrays.asList(new String[]{"arte","sueco","Ingenieria","FI"}));
		event.setUrl("http://");
		
	}
	
	@Test
	public void test() throws JsonProcessingException {
//		System.out.println("Iniciando test");
//		String function = String.format(EventRepository.WITHIN_DISTANCE, 19.54, -99.20, 35.0);
//		System.out.println("Func"+function);
//		Iterable<Event> result = eventRepository.getLocation(function);
//		
//		for(Event r:result){
//			System.out.println(r.getName());
//			assertNotNull(r.getName());
//		}
		ObjectMapper mapper = new ObjectMapper();
		Pageable page = new PageRequest(0, 25);
		logger.debug("	A "+ new Date());
		//Page<Event> result = eventRepository.findAll(page);
		Iterable<Event> resutl = eventRepository.getLocation("withinDistance:[ 19.43 -99.13, 25.00]");
		logger.debug("	B "+ new Date()+" l");
		ArrayList<Event> arrayList = new ArrayList<Event>();
		for (Event event : resutl) {
			arrayList.add(event);
		}
		logger.debug("	C "+ new Date()+" size "+arrayList.size());
//		List<Event> content = result.getContent();
//		String ss = mapper.writeValueAsString( content);
		//System.out.println(ss);
	}
	
	
	

}

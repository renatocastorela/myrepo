package org.oiga.model.services;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.oiga.model.entities.Event;
import org.oiga.model.entities.Interaction;
import org.oiga.model.entities.User;
import org.oiga.model.repositories.EventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.neo4j.support.Neo4jTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;


@Service
public class EventService {
	private static final Logger logger = LoggerFactory.getLogger(EventService.class.getName());
	@Autowired
	private EventRepository eventRepository;
	@Autowired
	private Neo4jTemplate template;
	
	@Autowired
	private SimpleVenueService venueService;
	private ObjectMapper mapper = new ObjectMapper();
	
	
	public List<Event> getByGeolocation(){
		ArrayList<Event> events = new ArrayList<Event>();
		
		return events;
	}
	@Transactional
	public Interaction liked(Event e, User u){
		//Validamos que no exista la relacion
		Interaction interaction = getRelationShip(e, u);
		if(interaction == null){
			logger.debug("Creando una nueva interaccion entre "+e.getNodeId() + " y "+u.getNodeId());
			interaction = new Interaction();
			interaction.setUser(u);
			interaction.setEvent(e);
		}else{
			logger.debug("Se cargo una nueva relacion entre "+e.getNodeId() + " y "+u.getNodeId());
			interaction.setLastInteraction(new Date());
		}
		interaction.setLiked(true);
		template.save(interaction);
		return interaction;
	}
	public Interaction getRelationShip(Event e, User u) {
		return template.getRelationshipBetween(u, e,Interaction.class ,"INTERACTS");
	}
	
	@Transactional
	public void importEvents(String fileName){
		List<Event> events;
		try {
			events = mapper.readValue(new File(fileName), new TypeReference<List<Event>>(){});
			for (Event event : events) {
				eventRepository.save(event);
				logger.debug("Se inserto "+event.getNodeId());
			}
			logger.info("Actualizando id de eventos");
			mapper.enable(SerializationFeature.INDENT_OUTPUT);
			mapper.writeValue(new File(fileName), events);
			
		} catch (IOException e) {
			logger.error("No se pudo cargar el archivo "+fileName+":"+e.getMessage());
			throw new RuntimeException(e);
		}
		
	}

	public EventRepository getEventRepository() {
		return eventRepository;
	}

	public void setEventRepository(EventRepository eventRepository) {
		this.eventRepository = eventRepository;
	}
	
	
}

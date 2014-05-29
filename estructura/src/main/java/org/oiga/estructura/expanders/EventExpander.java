package org.oiga.estructura.expanders;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.oiga.model.entities.Event;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public abstract class EventExpander {
	private String eventFileName;
	private String expFileName;
	private ObjectMapper mapper = new ObjectMapper();
	public abstract void expandEvent(Event e);
	
	public void expand(){
		List<Event> events;
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		if(eventFileName == null){
			throw new RuntimeException("El nombre del archivo a expandir no puede ser nulo");
		}
		if(expFileName == null){
			throw new RuntimeException("El nombre del archivo expandido a guardar no puede ser nulo");
		}
		try {
			events = readJsonFile(eventFileName);
		}catch (IOException e) {
			throw new RuntimeException("No se pudo leer el archivo json "+eventFileName+", Error:"+e.getMessage());
		}
		for(Event e:events){
			expandEvent(e);
		}
		try {
			updateJsonFile(expFileName, events);
		} catch (IOException e1) {
			throw new RuntimeException("No se pudo escribir el archivo json "+expFileName+", Error:"+e1.getMessage());
		}
		
	}
	
	
	
	public List<Event> readJsonFile(String fileName) throws JsonParseException, JsonMappingException, IOException{
		return mapper.readValue(new File(fileName), mapper.getTypeFactory().constructCollectionType(List.class, Event.class));
	}
	public void updateJsonFile(String fileName, List<Event> events) throws JsonGenerationException, JsonMappingException, IOException{
		mapper.writeValue(new File(fileName), events);
	}

	public String getEventFileName() {
		return eventFileName;
	}

	public void setEventFileName(String eventFileName) {
		this.eventFileName = eventFileName;
	}

	public String getExpFileName() {
		return expFileName;
	}

	public void setExpFileName(String expFileName) {
		this.expFileName = expFileName;
	}
	
}

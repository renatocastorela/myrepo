package org.oiga.vertex.scrappers;

import javax.ws.rs.ext.ContextResolver;

import org.codehaus.jackson.map.DeserializationConfig.Feature;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonObjectMapperProvider implements ContextResolver<ObjectMapper>{

	@Override
	public ObjectMapper getContext(Class<?> arg0) {
		ObjectMapper mapper = new ObjectMapper()
			.configure(Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
		return mapper;
	}

}

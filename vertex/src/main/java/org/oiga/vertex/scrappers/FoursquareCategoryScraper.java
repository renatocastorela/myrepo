package org.oiga.vertex.scrappers;

import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.GenericType;
import javax.ws.rs.core.MediaType;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FoursquareCategoryScraper {
	Logger logger = LoggerFactory.getLogger(FoursquareCategoryScraper.class);
	private final String clientId = "M3XC102K5CRRC4OZLGQTXWICDAQUCB2MWQY0Q1WADK2STNMU";
	private final String clientSecret = "EEVCZRRIOTLRJM5Z1JUXQVJ2L3RWLZ53D3YSOH1ZAI221ZQL";
	private final String v="20131014";
	private static final String _CLIENT_ID_PARAM = "client_id";
	private static final String _CLIENT_SECRET_PARAM = "client_secret";
	private static final String _V_PARAM = "v";
	public void scrape(){
		Client client = ClientBuilder.newBuilder()
				.register(JacksonObjectMapperProvider.class)
				.register(JacksonFeature.class)
				.build();
		
		String response = client.target("https://api.foursquare.com/v2")
				.path("/venues/categories")
				.queryParam(_CLIENT_ID_PARAM, clientId)
				.queryParam(_CLIENT_SECRET_PARAM, clientSecret)
				.queryParam(_V_PARAM, v)
				.request(MediaType.TEXT_PLAIN_TYPE)
				.get(String.class);
		logger.debug("Respuesta recibida: "+response);
		/*TODO: Mover la clase response*/
		/*
		FoursquareResponse<List<Category>> fr = client.target("https://api.foursquare.com/v2")
				.path("/venues/categories")
				.queryParam(_CLIENT_ID_PARAM, clientId)
				.queryParam(_CLIENT_SECRET_PARAM, clientSecret)
				.queryParam(_V_PARAM, v)
								.request(MediaType.APPLICATION_JSON_TYPE)
								
								.get(new GenericType<FoursquareResponse<List<Category>>>(){});  
		for(Category c: fr.getResponse()){
			if(!c.getCategories().isEmpty()){
				logger.debug("Is not empty!");
				for(Category j:c.getCategories()){
					System.out.println(j.getName());
				}
			}
			
		}*/
	}
}

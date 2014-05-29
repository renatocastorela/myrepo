package org.oiga.vertex.services;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.oiga.vertex.credentials.FoursquareApiCredential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompactVenue;
import fi.foyt.foursquare.api.entities.VenuesSearchResult;

public class FoursquareVenueService implements VenueService<CompactVenue> {
	Logger logger = LoggerFactory.getLogger(FoursquareVenueService.class);
	
	private FoursquareApiCredential credential;
	private FoursquareApi api;
	private ObjectMapper mapper = new ObjectMapper();
	public FoursquareVenueService() {
		super();
		init();
	}
	
	private void init(){
		credential = new FoursquareApiCredential();
		api = new FoursquareApi(credential.getClientId(), credential.getClientSecret(), "");
	}
	
	private JsonNode exploreOneByName(String name, String near){
		Client client = ClientBuilder.newClient().register(JacksonFeature.class);
		JsonNode venue  = null;
		String result;
		try {
			result = client.target("https://api.foursquare.com/v2/").path("venues/explore")
					.queryParam("near", near)
					.queryParam("intent", "browse")
					.queryParam("query", name)
					.queryParam("client_id", credential.getClientId())
					.queryParam("client_secret", credential.getClientSecret())
					.queryParam("v", credential.getVersion())
					.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
			JsonNode root = mapper.readTree(result);
			venue = root.path("response").path("groups").get(0).path("items").get(0).get("venue");
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		return venue;
		
	}
	
	public String[] exploreVenueName(String name, String near){
		JsonNode v = exploreOneByName(name, near);
		if(v == null){ throw new RuntimeException("Venue no encontrada!	");}
		JsonNode l = v.get("location");
		String[] r = new String[10];
		try{
			r[0] = v.path("name").asText();
			r[1] = v.path("id").asText();
			r[2] = l.path("address").asText();
			r[3] = l.path("crossStreet").asText();
			r[4] = l.path("city").asText();
			r[5] = l.path("state").asText();
			r[6] = l.path("postalCode").asText();
			r[7] = l.path("country").asText();
			r[8] = l.path("lat").asText();
			r[9] = l.path("lng").asText();
		}catch(Exception e){
			throw new RuntimeException(e);
		}
		return r;
	}
	
	public List<CompactVenue> findByName(String name, String near){
		List<CompactVenue> venues = new ArrayList<>();
		try {
			logger.debug("Looking for.. "+name);
			HashMap<String, String> params = new HashMap<String, String>();
			params.put("near", near);
			params.put("query", name);
			params.put("intent", "browse");
			//params.put("radius", "100000");
			Result<VenuesSearchResult> result = api.venuesSearch(params);
			
			//Result<VenuesSearchResult> result = api.venuesExplore(arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8)(near, name, 1, null, null, null, null, null);
			CompactVenue[] v= result.getResult().getVenues();
			System.out.println(">"+v[0].getName());
			System.out.print("Len: "+result.getResult().getVenues().length);
			if(result.getMeta().getCode() == 200){
				logger.debug("Where found "+result.getResult().getVenues().length+" venues.");
				venues.addAll(Arrays.asList(result.getResult().getVenues()));
			}else{
				String error = "Error ocurred: \n"
						+" code: "+result.getMeta().getCode()+"\n"
						+" type: "+result.getMeta().getErrorType()+"\n"
						+" detail: "+result.getMeta().getErrorDetail();
				logger.error(error);
				throw new RuntimeException(error);
			}
		}catch(ArrayIndexOutOfBoundsException e){
			throw new RuntimeException("Venue not found");
		}
		catch (FoursquareApiException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return venues;
	}
	
	public CompactVenue findOneByName(String name, String near)
	{
		return findByName(name, near).get(0);
	}
}

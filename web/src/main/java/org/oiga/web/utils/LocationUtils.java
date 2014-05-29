package org.oiga.web.utils;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.MediaType;


public class LocationUtils {
	
	public static String getLocation(String ip){
		Client client = ClientBuilder.newClient();
		String result =
				client.target("http://freegeoip.net/json").path(ip)
				.request(MediaType.APPLICATION_JSON_TYPE).get(String.class);
		return result;
	}
	
	public static String getUserLocation(HttpServletRequest request){
		String ip = request.getHeader("X-Forwarded-For") == null? request.getRemoteAddr(): request.getHeader("X-Forwarded-For");
		return getLocation(ip);
		
	}
}

package org.oiga.model.services;


import java.util.List;

import org.oiga.model.entities.Adress;
import org.oiga.model.entities.SimpleVenue;
import org.oiga.vertex.services.FoursquareVenueService;
import org.springframework.stereotype.Service;

import fi.foyt.foursquare.api.entities.CompactVenue;

@Service
public class SimpleVenueService {
	
	private FoursquareVenueService externalVenueService = new FoursquareVenueService();
	
	public SimpleVenue loadExternal(String name, String near)
	{
		List<CompactVenue> v = externalVenueService.findByName(name, near);
		CompactVenue cv = v.get(0);
		return toSimpleVenue(cv);
	}
	
	public static SimpleVenue toSimpleVenue(CompactVenue v)
	{
		SimpleVenue venue = new SimpleVenue();
		Adress adress = new Adress();
		adress.setStreetAdress(v.getLocation().getAddress());
		adress.setCity(v.getLocation().getCity());
		adress.setCountry(v.getLocation().getCountry());
		adress.setCrossStreet(v.getLocation().getCrossStreet());
		adress.setWkt(v.getLocation().getLng(), v.getLocation().getLat());
		adress.setPostalCode(v.getLocation().getPostalCode());
		adress.setState(v.getLocation().getState());
		venue.setFoursquareId(v.getId());
		venue.setName(v.getName());
		venue.setAdress(adress);
		return venue;
	} 
}

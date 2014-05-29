package org.oiga.web.controllers;

import org.oiga.vertex.services.FoursquareVenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import fi.foyt.foursquare.api.FoursquareApi;
import fi.foyt.foursquare.api.FoursquareApiException;
import fi.foyt.foursquare.api.Result;
import fi.foyt.foursquare.api.entities.CompleteVenue;

@Controller
public class FoursquareController {
	private static Logger logger = LoggerFactory.getLogger(FoursquareController.class);
	
	@Autowired
	private FoursquareApi api;
	
	@RequestMapping(value="venues/{foursquareId}",method=RequestMethod.GET)
	public @ResponseBody  CompleteVenue searchVenue(@PathVariable String foursquareId){
		Result<CompleteVenue> result;
		CompleteVenue venue = null;
		try {
			result = api.venue(foursquareId);
			venue = result.getResult();
		} catch (FoursquareApiException e) {
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
		return venue;
	} 
	
	
}

package org.oiga.estructura.venuenames;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Pattern;

import org.oiga.vertex.services.FoursquareVenueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;
import au.com.bytecode.opencsv.CSVWriter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import fi.foyt.foursquare.api.entities.CompactVenue;

public class EventVenueNamesExtractor {
	public static Logger logger = LoggerFactory.getLogger(EventVenueNamesExtractor.class);
	private static FoursquareVenueService venueService = new FoursquareVenueService();
	
	
	public static void extractNewVenues(String eventFileName, String resultFileName) {
		CSVWriter writer;
		try {
			writer = new CSVWriter(new FileWriter(resultFileName), '\t', CSVWriter.NO_QUOTE_CHARACTER);
			HashSet<String> keys = new HashSet<>();
			ObjectMapper mapper = new ObjectMapper();
			List<JsonNode> values = mapper.readValue(
					new File(eventFileName),
					mapper.getTypeFactory().constructCollectionType(List.class,
							JsonNode.class));
			writer.writeNext(new String[]{"location","locationToSearch","locationAdress"});
			for (JsonNode node : values) {
				String location = node.get("location").asText();
				String locationAdress = node.get("locationAdress").asText();
				String key = location + locationAdress;
				if (!keys.contains(key)) {
					String[] l = new String[3];
					l[0] = location;
					l[1] = EventVenueNamesExtractor.nameFixing(location);
					l[2] = locationAdress;
					writer.writeNext(l);
					keys.add(key);
				}
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
	
	public static void extractVenuesGeoref(String resultFileName, String near){
		CSVReader reader;
		CSVWriter writer;
		try {
			reader = new CSVReader(new FileReader(resultFileName), '\t');
			List<String[]> entries = reader.readAll();
			reader.close();
			
			writer = new CSVWriter(new FileWriter("4square_"+resultFileName), '\t', CSVWriter.NO_QUOTE_CHARACTER);
			writer.writeNext(new String[]{"location", "locationAdress", 
					"4squareLocation",
					"4squareId",
					"4squareAdress", 
					"4squareCrossStreet",
					"4squareCity",
					"4squareState",
					"4squarePostalCode",
					"4squareCountry",
					"4squareLat",
					"4squareLng"});
			for(String[] row:entries){
				String name = row[0];
				String searchName = row[1];
				String addr = row[2];
				if(name.equals("location")){ continue;}
				String[] newRow = new String[12];
				newRow[0] = name;
				newRow[1] = addr;
				try {
					String[] v = venueService.exploreVenueName(searchName, near);
					logger.debug("Se cargo "+v[0]);
					for(int i=0;i<10;i++){
						newRow[i+2] = v[i];
					}
				} catch (Exception e) {
					logger.warn("No se pudo cargar el nombre : {} ->"+e.getMessage(), name);
					for(int i=2;i<12;i++){
						newRow[i] = "--------";
					}
				}
				writer.writeNext(newRow);
			}
			writer.close();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
		
	}
	/*TODO: Crear una forma mas elegante de areglar los nombres*/
	private static String nameFixing(String venue){
		String fixedString = venue;
		if(venue.contains("ACSI") || venue.contains("Salón el Generalito")){
			fixedString = "Antiguo Colegio de San Ildefonso";
		}else if(venue.contains("Sala de exposiciones temporales, Col. Blaisten")){
			fixedString = "Centro Cultural Universitario Tlatelolco (CCUT)";
		}else if(venue.contains("Casa del Lago")){
			fixedString = "Casa del Lago";
		}else if(venue.contains("CCU Tlateloloco")){
			fixedString = "Centro Cultural Universitario Tlatelolco (CCUT)";
		}else if(venue.contains("Centro de información")){
			fixedString = "Museo del Chopo";
		}
		return fixedString;
		
	}
}

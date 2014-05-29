package org.oiga.estructura.services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import org.oiga.model.entities.Adress;
import org.oiga.model.entities.SimpleVenue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import au.com.bytecode.opencsv.CSVReader;

public class VenueNameService {
	/*TODO: Substituir el archivo de registros por una BD */
	public static Logger logger = LoggerFactory.getLogger(VenueNameService.class);
	private final String fileDb = "4square_venues_result.csv";
	private HashMap<String, String[]> inMemoryData = new HashMap<>();
	
	
	public VenueNameService() {
		super();
		try {
			init();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}


	private void init() throws IOException{
		CSVReader reader = new CSVReader(new FileReader(new File(fileDb)),'\t');
		String[] nextLine;
		while ((nextLine = reader.readNext()) != null) {
			inMemoryData.put(nextLine[0], nextLine);
		}
		logger.debug("Se cargaron en memoria '"+inMemoryData.size()+"' registros");
		reader.close();
	}
	
	
	public SimpleVenue findByName(String name){
		SimpleVenue v = new SimpleVenue();
		Adress a = new Adress();
		String[] r = inMemoryData.get(name);
		v.setAdress(a);
		v.setName(r[2]);
		v.setFoursquareId(r[3]);
		a.setStreetAdress(r[4]);;
		a.setCrossStreet(r[5]);
		a.setCity(r[6]);
		a.setState(r[7]);
		a.setPostalCode(r[8]);
		a.setCountry(r[9]);
		a.setWkt(Double.valueOf(r[11]), Double.valueOf(r[10]));
		return v;
	}

}

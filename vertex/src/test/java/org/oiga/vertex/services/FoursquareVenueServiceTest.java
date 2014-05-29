package org.oiga.vertex.services;

import java.util.List;

import org.junit.Test;

import fi.foyt.foursquare.api.entities.CompactVenue;

public class FoursquareVenueServiceTest {

	@Test
	public void test() {
		System.out.println("TESt");

		FoursquareVenueService service = new FoursquareVenueService();
	//	CompactVenue cv = service.findOneByName("Sala Nezahualcoyotl", "Mexico City DF");
		//List<CompactVenue> list = service.findByName("Sala Nezahualcoyotl", "Mexico City DF");
		String[] r;
		try {
			r = service.exploreVenueName("Sala Nezahualcoyotl", "Mexico City DF");
			System.out.println("Venue "+r[0]+" "+r[1]+" "+r[2]+" "+r[3]+" "+r[4]+" "+r[5]+" "+r[6]+" "+r[7]
					+" "+r[8]+" "+r[9]);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

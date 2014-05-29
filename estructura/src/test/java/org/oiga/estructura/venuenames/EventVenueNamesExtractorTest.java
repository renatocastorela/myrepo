package org.oiga.estructura.venuenames;

import static org.junit.Assert.*;

import org.junit.Test;

public class EventVenueNamesExtractorTest {

	@Test
	public void test() {
//		EventVenueNamesExtractor.extractNewVenues("event_cultura_unam_20140101_20140131.json", "venues_result.csv");
		EventVenueNamesExtractor.extractVenuesGeoref("venues_result.csv", "Mexico City, DF");
	}

}

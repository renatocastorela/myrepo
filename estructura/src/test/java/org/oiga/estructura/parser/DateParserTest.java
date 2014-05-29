package org.oiga.estructura.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.Interval;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DateParserTest {
	Logger logger = LoggerFactory.getLogger(DateParserTest.class); 
	static final String text = "El evento se llevará acabo en las siguientes fechas: "
			+ "20/09/2013 de 19:00 a 20:30,"
			+ "21/09/2013 de 19:00 a 20:30,"
			+ "22/09/2013 de 18:00 a 19:30,"
			+ "27/09/2013 de 19:00 a 20:30,"
			+ "28/09/2013 de 19:00 a 20:30,"
			+ "29/09/2013 de 18:00 a 19:30,";
	@Test
	public void testParse() {
		CulturaUnamDateParser parser = new CulturaUnamDateParser();
		logger.info(text);
		boolean res = parser.isParseable(text);
		assertTrue("No es parserable",res);
		List<Interval> intervalos = parser.parse(text);
		assertEquals(6, intervalos.size());
		
	}

}

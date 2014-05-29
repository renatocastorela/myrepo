package org.oiga.estructura.parser;

import static org.junit.Assert.*;

import java.util.List;

import org.joda.time.DateTime;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IngenieriaUnamDateParserTest {
	Logger logger = LoggerFactory.getLogger(IngenieriaUnamDateParserTest.class);
	
	
	@Test
	public void testParse() {
		IngenieriaUnamDateParser parser = new IngenieriaUnamDateParser();
		String text = "23 al 27 de septiembre Javier Barros Sierra 10:30 horas";
		List<DateTime> result = parser.parse(text);
		for(DateTime dt:result){
			logger.debug(dt.toString());
		}
		assertEquals(5, result.size());
		String text2 = "10 de octubre Sotero Prieto 10:00 y 16:00 horas";
		List<DateTime> result2 = parser.parse(text2);
		for(DateTime dt:result2){
			logger.debug(dt.toString());
		}
		assertEquals(2, result2.size());
	}

}

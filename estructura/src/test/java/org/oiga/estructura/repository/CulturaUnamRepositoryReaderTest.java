package org.oiga.estructura.repository;
import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class CulturaUnamRepositoryReaderTest {

	final static Logger logger = LoggerFactory.getLogger(CulturaUnamRepositoryReaderTest.class);
	CulturaUnamRepositoryReader reader;
	Document documment;
	
	@Before
	public void setUp() throws IOException {
		logger.info("Inicializando clase");
		reader = new CulturaUnamRepositoryReader();	
		InputStreamRepositoryExtractor extractor = new InputStreamRepositoryExtractor();
		extractor.setInputStreamName("CulturaUnamRepository.html");
		documment = extractor.extract();
		logger.info("Texto a procesar:\n************************************\n"
						+documment.text()+"\n************************************");
	}

	@Test
	public void testParseHtmlDocument() {
		List<Map<String,String>> contents = reader.read(documment);
		
		assertNotNull(contents);
		StringBuilder builder = new StringBuilder("\n");
		for(Map content:contents){
			builder.append(JSONValue.toJSONString(content)).append("\n");	
		}
		logger.info(builder.toString());
	}
}

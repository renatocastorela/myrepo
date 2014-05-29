package org.oiga.estructura.repository;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.List;

import org.json.simple.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IngenieriaUnamRepositoryReaderTest {
	final static Logger logger = LoggerFactory.getLogger(IngenieriaUnamRepositoryReaderTest.class);
	IngenieriaUnamRepositoryReader reader;
	Document documment;
	
	@Before
	public void setUp() throws IOException {
		logger.info("Inicializando clase");
		reader = new IngenieriaUnamRepositoryReader();
		InputStreamRepositoryExtractor extractor = new InputStreamRepositoryExtractor();
		extractor.setInputStreamName("IngenieriaUnamRepository.html");
		documment = extractor.extract();
		logger.info("Texto a procesar:\n************************************\n"
						+documment.text()+"\n************************************");
	}

	@Test
	public void testParseHtmlDocument() {
		List<JSONObject> contents = reader.read(documment);
		
		assertNotNull(contents);
		StringBuilder builder = new StringBuilder();
		for(JSONObject content:contents){
			builder.append(content.toJSONString()).append("\n");	
		}
		logger.info(builder.toString());
	}

}

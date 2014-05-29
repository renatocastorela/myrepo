package org.oiga.estructura.repository;

import static org.junit.Assert.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HtmlRepositoryExtractorTest {
	Logger logger = LoggerFactory.getLogger(HtmlRepositoryExtractorTest.class); 
	HtmlRepositoryExtractor extractor = new HtmlRepositoryExtractor();
	
	@Before
	public void setUp(){
		Map<String,String> params = new HashMap<String, String>();
		params.put("date","");
		params.put("date2","");
		params.put("fec", "mes");
		params.put("gen","");
		params.put("inicio", "1");
		params.put("operacion", "busqueda");
		params.put("publico","");
		extractor.setMethod(Method.POST);
		extractor.setParams(params);
		extractor.setUrl("http://www.cultura.unam.mx/Administracion/files/GestionBusqueda.html.php");
	}
	
	@Test
	public void testExtractCulturaUnam() throws IOException {
		Document doc = extractor.extract();
		logger.info("Contenido: \n"+doc.text());
		assertNotNull(doc);
		
	}

}

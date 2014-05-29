package org.oiga.estructura.repository;

import java.util.ArrayList;
import java.util.List;

import javax.management.RuntimeErrorException;

import org.json.simple.JSONObject;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class IngenieriaUnamRepositoryReader  implements RepositoryReader<JSONObject> {


	@Override
	public List<JSONObject> read(Document document) {
		List<JSONObject> documents = new ArrayList<JSONObject>();
		String text = document.text();
		/*Actividades internas*/
		Elements elements = document.select("table").get(7).select("tr");
		elements.remove(0);
		elements.remove(0);
		for (Element element : elements) {			
			JSONObject  json = new JSONObject();
			Elements tds = element.select("td");
			if(tds.isEmpty()){throw new RuntimeException("Elements vacia");}
			json.put("tipo", "interna");
			json.put("fecha", tds.get(0).text());
			json.put("actividad", tds.get(1).text());
			json.put("lugar", tds.get(2).text());
			documents.add(json);
		}
		
		return documents;
	}

}

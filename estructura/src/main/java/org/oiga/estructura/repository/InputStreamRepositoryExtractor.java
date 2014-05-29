package org.oiga.estructura.repository;

import java.io.IOException;
import java.io.InputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class InputStreamRepositoryExtractor implements RepositoryExtractor{
					
	private String inputStreamName;
	
	@Override
	public Document extract() throws IOException {
		InputStream inputStream = this.getClass().getResourceAsStream(inputStreamName);
		return Jsoup.parse(inputStream, "UTF-8", "");
	}

	public String getInputStreamName() {
		return inputStreamName;
	}

	public void setInputStreamName(String inputStreamName) {
		this.inputStreamName = inputStreamName;
	}

}

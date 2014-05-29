package org.oiga.estructura.repository;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.nodes.Document;

@Deprecated
public class HtmlRepositoryExtractor implements RepositoryExtractor{
	private String url;
	private Method method = Method.GET;
	private Document document = null;
	private Map<String,String> params = new HashMap<String, String>();
	
	@Override
	public Document extract() throws IOException {
		Connection conn = Jsoup.connect(url).data(params);
		if(method.equals(Method.POST)){
			this.document = conn.post();
		}else{
			this.document = conn.get();
		}
		return document;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
	}

	public Document getDocument() {
		return document;
	}

	public void setDocument(Document document) {
		this.document = document;
	}

	public Map<String, String> getParams() {
		return params;
	}

	public void setParams(Map<String, String> params) {
		this.params = params;
	}
	
	

}

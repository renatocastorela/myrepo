package org.oiga.estructura.model.beans;

import java.util.HashMap;
import java.util.Map;

public class Documento {
	private String id;
	private String fuente;
	private String contenido;
	private Map<String, Object> meta = new HashMap<String, Object>();
	
	public String getFuente() {
		return fuente;
	}
	public void setFuente(String fuente) {
		this.fuente = fuente;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Map<String, Object> getMeta() {
		return meta;
	}
	public void setMeta(Map<String, Object> meta) {
		this.meta = meta;
	}
}

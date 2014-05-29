package org.oiga.estructura.model.beans;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Extraccion {
	private String urlRepositorio;
	private String nombreRepositorio;
	private String nombreArchivo;
	private String logoRepositorio;
	private Date fechaExtraccion;
	
	private Map<String, Documento> documentos = new HashMap<String, Documento>();
	
	public Map<String, Documento> getDocumentos() {
		return documentos;
	}
	public void setDocumentos(Map<String, Documento> documentos) {
		this.documentos = documentos;
	}
	public String getUrlRepositorio() {
		return urlRepositorio;
	}
	public void setUrlRepositorio(String urlRepositorio) {
		this.urlRepositorio = urlRepositorio;
	}
	public String getNombreRepositorio() {
		return nombreRepositorio;
	}
	public void setNombreRepositorio(String nombreRepositorio) {
		this.nombreRepositorio = nombreRepositorio;
	}
	public Date getFechaExtraccion() {
		return fechaExtraccion;
	}
	public void setFechaExtraccion(Date fechaExtraccion) {
		this.fechaExtraccion = fechaExtraccion;
	}
	public String getNombreArchivo() {
		return nombreArchivo;
	}
	public void setNombreArchivo(String nombreArchivo) {
		this.nombreArchivo = nombreArchivo;
	}
	public String getLogoRepositorio() {
		return logoRepositorio;
	}
	public void setLogoRepositorio(String logoRepositorio) {
		this.logoRepositorio = logoRepositorio;
	}
}

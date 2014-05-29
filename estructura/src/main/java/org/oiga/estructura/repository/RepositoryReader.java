package org.oiga.estructura.repository;

import java.util.List;

import org.jsoup.nodes.Document;

public interface RepositoryReader<T> {
	
	
	/**
	 * Regresa la lista de documentos parseados cada documento sigue una
	 * estructura similar con el proposito de aplicarle el mismo analisis.
	 * @return Lista de documentos ya parseados.
	 */
	public List<T> read(Document document);
}

package org.oiga.estructura.populators;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.jsoup.nodes.Document;
import org.oiga.estructura.analysis.AnalysisEngine;
import org.oiga.estructura.repository.RepositoryExtractor;
import org.oiga.estructura.repository.RepositoryReader;
import org.oiga.model.services.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Clase que se encarga de gestionar la extraccion de informacion 
 * de un repositorio. 
 * @author jaime.renato
 *
 * @param <T>
 */
public abstract class AbstractPopulator<T> implements Populator{
	
	private RepositoryExtractor repositoryExtractor;
	private RepositoryReader<Map<String, String>> repositoryReader;
	private AnalysisEngine<T>	analysisEngine;
	
	public List<T> getData(){
		try {
			Document document = repositoryExtractor.extract();
			List<Map<String, String>> data = repositoryReader.read(document);
			List<T> parsedData =  analysisEngine.analyse(data);
			return parsedData;
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public RepositoryExtractor getRepositoryExtractor() {
		return repositoryExtractor;
	}

	public void setRepositoryExtractor(RepositoryExtractor repositoryExtractor) {
		this.repositoryExtractor = repositoryExtractor;
	}

	public RepositoryReader<Map<String, String>> getRepositoryReader() {
		return repositoryReader;
	}

	public void setRepositoryReader(RepositoryReader<Map<String, String>> repositoryReader) {
		this.repositoryReader = repositoryReader;
	}

	public AnalysisEngine<T> getAnalysisEngine() {
		return analysisEngine;
	}

	public void setAnalysisEngine(AnalysisEngine<T> analysisEngine) {
		this.analysisEngine = analysisEngine;
	}
	
	
	

}

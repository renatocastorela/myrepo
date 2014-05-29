package org.oiga.estructura.repository;

import java.io.IOException;

import org.jsoup.nodes.Document;

public interface RepositoryExtractor {
	public Document extract() throws IOException;
}

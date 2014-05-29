package org.oiga.vertex.services;

import java.util.List;


public interface VenueService<T> {
	public List<T> findByName(String name, String near);
}

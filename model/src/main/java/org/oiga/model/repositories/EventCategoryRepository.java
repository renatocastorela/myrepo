package org.oiga.model.repositories;

import java.util.Map;

import org.oiga.model.entities.EventCategory;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface EventCategoryRepository extends GraphRepository<EventCategory>{
	@Query("START e=node(*)  MATCH e-[:CATEGORIZED]->v RETURN v.name as name, count( e ) as count")
	Iterable<Map<String,Object>> getCategoriesCount();
}

package org.oiga.model.repositories;


import org.oiga.model.entities.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.conversion.EndResult;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface EventRepository extends GraphRepository<Event>{
	public static final String WITHIN_DISTANCE = "withinDistance:[ %.2f %.2f, %.2f]"; 
	@Query(
			"START e=node(*), a=node:geo_location({0}) "
			+"MATCH e-[:PERFORMED]->(v)-[:IS_LOCATED]->a "
			+"RETURN e" )
	Iterable<Event> getLocation(String function);
	@Query(
			"START n = node:event_name({0}), a=node:geo_location({1}) "
			+"MATCH n-[:PERFORMED]->(v)-[:IS_LOCATED]->a "
			+"RETURN n" )
	Iterable<Event> findAllByQueryAndLocation(String luceneQuery, String location);
	@Query("START e=node(*) "
			+"MATCH e-[:CATEGORIZED]->v "
			+"WHERE v.name={0} "
			+"RETURN e ")
	Iterable<Event> findByCategory(String category);
	Page<Event> findByNameLike(String name, Pageable page);
	EndResult<Event> findAllByName(String name);
	
}

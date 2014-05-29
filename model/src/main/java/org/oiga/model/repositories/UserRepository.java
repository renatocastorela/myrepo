package org.oiga.model.repositories;

import org.oiga.model.entities.User;
import org.springframework.data.neo4j.repository.GraphRepository;

public interface UserRepository extends GraphRepository<User>{
	User findByEmail(String email);
	User findByfacebookUsername(String facebookUsername);
}

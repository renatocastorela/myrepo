package org.oiga.model.services;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.User;
import org.oiga.model.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:db-context.xml")
public class EventServiceTest {
	@Autowired
	private EventService service;
	@Autowired
	private UserRepository userRepository;
	@Test
	public void test() {
		Event e = service.getEventRepository().findOne(5L);
		User u = userRepository.findOne(566L);
		
//		Interaction relationShip = service.getRelationShip(e, u);
//		System.out.println("Interacit:"+relationShip.getLastInteraction());
		//service.liked(e, u);
		//service.importEvents("exp_cultura_unam_20140101_20140131.json");
	}

}

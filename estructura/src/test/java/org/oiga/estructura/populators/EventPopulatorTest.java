package org.oiga.estructura.populators;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:db-context.xml","classpath:estructura-context.xml"})
@Transactional
public class EventPopulatorTest {
	
	@Autowired
	private EventPopulator eventPopulator;
	
	@Before
	public void setUp(){
		
	}
	
	
	@Test
	public void test() {
//		eventPopulator.populate();
	}

}

package org.oiga.estructura.cultura.unam.expanders;

import static org.junit.Assert.*;

import org.junit.Test;
import org.oiga.estructura.utils.FileNamingUtils;

public class CulturaUnamStaticEventExpanderTest {

	@Test
	public void test() {
		CulturaUnamStaticEventExpander expander = new CulturaUnamStaticEventExpander();
		expander.setEventFileName("event_cultura_unam_20140101_20140131.json");
		expander.setExpFileName("exp_cultura_unam_20140101_20140131.json");
		expander.expand();
	}

}

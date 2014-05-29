package org.oiga.estructura.cultura.unam.converters;

import static org.junit.Assert.*;

import org.junit.Test;
import org.oiga.estructura.cultura.unam.converters.CulturaUnamRepositoryEventConverter;
import org.oiga.estructura.cultura.unam.converters.EventConverter;
import org.oiga.estructura.utils.FileNamingUtils;

public class CulturaUnamRepositoryEventConverterTest {

	@Test
	public void test() {
		EventConverter ep = new CulturaUnamRepositoryEventConverter();
		ep.setHtmlFileName("html_cultura_unam_20140101_20140131.json");
		ep.setEventFileName(FileNamingUtils.getCulturaUnamFileName("event","20140101","20140131"));
		ep.persist();
	}

}

package org.oiga.estructura.utils;

import static org.junit.Assert.*;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.junit.Test;
import org.oiga.model.entities.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KeywordUtilsTest {
	public static Logger logger = LoggerFactory.getLogger(KeywordUtilsTest.class);
	@Test
	public void test() {
		List<Tag> tokens = KeywordUtils.extractKeywords("hola Hola Hola este es un Texto que se me esta ocurriendo y es algo musicoso aleatorio "
				+"pero podria ser interesante ver el resultado cuando es descompuesto en sus partes Música o la musica musical");
		Iterator<Tag> iterator = tokens.iterator();
		while(iterator.hasNext()){
			Tag t= iterator.next();
			logger.info(t.getKeyword()+", "+t.getCount());
		}
		
		
	}

}

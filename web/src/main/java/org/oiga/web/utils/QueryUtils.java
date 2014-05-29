package org.oiga.web.utils;

import java.util.regex.Pattern;

public class QueryUtils {
	private static final String LUCENE_ESCAPE_CHARS = "[\\\\+\\-\\!\\(\\)\\:\\^\\]\\{\\}\\~\\*\\?]";
	private static final Pattern LUCENE_PATTERN = Pattern.compile(LUCENE_ESCAPE_CHARS);
	private static final String REPLACEMENT_STRING = "\\\\$0";
	
	public static String escapeLuceneString(String userInput){
		String escaped = LUCENE_PATTERN.matcher(userInput).replaceAll(REPLACEMENT_STRING);
		return escaped;
	}
}

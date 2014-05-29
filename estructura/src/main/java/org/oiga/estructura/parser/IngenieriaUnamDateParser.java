package org.oiga.estructura.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class IngenieriaUnamDateParser implements Parser<List<DateTime>>{

	private final static String _INTERVAL_DATE_REG_EXP = "(\\d{1,2}) ?al (\\d{1,2}) de (\\w*)";
	private final static String _UNIQUE_DATE_REG_EXP = "(\\d{1,2}) de (\\w*)";
	private final static String _TIME_REG_EXP = "(\\d{2}:\\d{2})";
	private final static DateTimeFormatter dateFmt = DateTimeFormat.forPattern("dd MMMMM yyyy").withLocale(new Locale("es"));
	private final static DateTimeFormatter horaFmt = DateTimeFormat.forPattern("HH:mm");
	private final static Pattern intervalPattern = Pattern.compile(_INTERVAL_DATE_REG_EXP);
	private final static Pattern uniquePattern = Pattern.compile(_UNIQUE_DATE_REG_EXP);
	private final static Pattern timePattern = Pattern.compile(_TIME_REG_EXP);
	
	@Override
	public List<DateTime> parse(String text) {
		Matcher intervalMatcher = intervalPattern.matcher(text);
		Matcher uniqueMatcher = uniquePattern.matcher(text);
		Matcher timeMatcher = timePattern.matcher(text);
		List<DateTime> result = new ArrayList<DateTime>();
		LocalDate[] fechas = null;
		if(intervalMatcher.find()){
			fechas = parseIntervalPattern(intervalMatcher);
		}else if(uniqueMatcher.find()){
			fechas = parseUniquePattern(uniqueMatcher);
		}else{
			throw new RuntimeException("No se cumple con ningun patron de fecha");
		}
		List<LocalDate> dates = new ArrayList<LocalDate>();
		List<LocalTime> times = new ArrayList<LocalTime>();
		int days = Days.daysBetween(fechas[0], fechas[1]).getDays()+1;
		for (int i=0; i < days; i++) {
		    LocalDate d = fechas[0].withFieldAdded(DurationFieldType.days(), i);
		    dates.add(d);
		}
		while(timeMatcher.find()){
			LocalTime hora = LocalTime.parse(timeMatcher.group(1), horaFmt);
			times.add(hora);
		}
		for(LocalDate d:dates){
			for(LocalTime t:times){
				DateTime dt = d.toDateTime(t);
				result.add(dt);
			}
		}
		return result;
	}
	
	private LocalDate[] parseIntervalPattern(Matcher matcher){
		String year = String.valueOf(LocalDate.now().getYear());
		LocalDate[] fechas = new LocalDate[2];
		LocalDate fechaInicio = LocalDate.parse(matcher.group(1)+" "+matcher.group(3)+" "+year,dateFmt);
		LocalDate fechaFin = LocalDate.parse(matcher.group(2)+" "+matcher.group(3)+" "+year,dateFmt);
		
		fechas[0] = fechaInicio;
		fechas[1] = fechaFin;
		return fechas;
		
	}
	
	private LocalDate[] parseUniquePattern(Matcher matcher){
		String year = String.valueOf(LocalDate.now().getYear());
		LocalDate[] fechas = new LocalDate[2];
		LocalDate fechaInicio = LocalDate.parse(matcher.group(1)+" "+matcher.group(2)+" "+year, dateFmt);
		LocalDate fechaFin = LocalDate.parse(matcher.group(1)+" "+matcher.group(2)+" "+year, dateFmt);
		fechas[0] = fechaInicio;
		fechas[1] = fechaFin;
		return fechas;
		
	}

}

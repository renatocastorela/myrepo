package org.oiga.estructura.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.LocalDate;
import org.joda.time.LocalTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CulturaUnamDateParser{
	private static Logger logger = LoggerFactory.getLogger(CulturaUnamDateParser.class);
	private static final String CU_REG_EXP = "(\\d{2}/\\d{2}/\\d{4}) de (\\d{2}:\\d{2}) a (\\d{2}:\\d{2})";
	public boolean isParseable(String text){
		/*FIXME: Evitar que se compile la regexp cada vez que invoca el metodo*/
		Pattern pattern = Pattern.compile(CU_REG_EXP);
		return pattern.matcher(text).find();
	}
	
	public static List<Interval> parse(String text){
		Pattern pattern = Pattern.compile(CU_REG_EXP);
		DateTimeFormatter fechaFmt = DateTimeFormat.forPattern("dd/MM/yyyy");
		DateTimeFormatter horaFmt = DateTimeFormat.forPattern("HH:mm");
		Matcher m = pattern.matcher(text);
		List<Interval> fechas = new ArrayList<Interval>();
		
		while(m.find()){
			logger.debug(":"+m.group(1)+":"+m.group(2)+":"+m.group(3));
			LocalDate fecha = LocalDate.parse(m.group(1), fechaFmt);
			LocalTime horaInicio = LocalTime.parse(m.group(2),horaFmt); 
			LocalTime horaFin = LocalTime.parse(m.group(3),horaFmt);
			DateTime fechaInicio = fecha.toDateTime(horaInicio);
			DateTime fechaFin = fecha.toDateTime(horaFin); 
			Interval intervalo = new Interval(fechaInicio, fechaFin);
			fechas.add(intervalo);
		}
		return fechas;
	}
	
	public static List<String> extractHours(List<Interval> intervalos){
		List<String> hours = new ArrayList<>();
		for(Interval s:intervalos){
			hours.add(s.toString());
		}
		return hours;
	}

}

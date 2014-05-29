package org.oiga.estructura.cultura.unam.converters;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.LocalDate;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.oiga.estructura.cultura.unam.extractors.CulturaUnamRepositoryExtractor;
import org.oiga.estructura.model.beans.Documento;
import org.oiga.model.entities.Event;
import org.oiga.model.entities.EventCategory;

public class CulturaUnamRepositoryEventConverter extends EventConverter{
	private static Pattern colorPattern = Pattern.compile(".*(#[0-9aA-fF]*).*");
	
	@Override
	public Event parseEvent(Documento doc){
		Event e = new Event();
		EventCategory ec = new EventCategory();
		EventCategory sec = new EventCategory();
		List<EventCategory> categories = new ArrayList<EventCategory>();
		Document dom = Jsoup.parse(doc.getContenido());
		String style = dom.select("div[class=eventos-titulo]").attr("style");
		Matcher matcher = colorPattern.matcher(style);
		String color = "";
		if( matcher.find()){
			try{
				color = matcher.group(1);
			}catch(IndexOutOfBoundsException iob){
				logger.warn("No se pudo extraer el color de {} ", style);
			}
		}
		String categoryName = dom.select("div[class=eventos-columna1]:matchesOwn(Actividad.*) + div").text();
		String subcategoryName = dom.select("div[class=eventos-columna1]:matchesOwn(Categor.a.*) + div").text();
		String titulo = dom.select("div[class=eventos-titulo]").text();
		String host = dom.select("div[class=eventos-columna1]:matchesOwn(Organiza.*) + div").text();
		String descripcion =  dom.select("div[class=eventos-columna1]:matchesOwn(Descripci.n.*) + div").text();
		String recinto = dom.select("div[class=eventos-columna1]:matchesOwn(Recinto:.*) + div").text();
		String recintoDir = dom.select("div[class=eventos-columna1]:matchesOwn(Recinto direcci.n.*) + div").text();
		String horarios = dom.select("div[class=eventos-columna1]:matchesOwn(Horarios.*) + div").text();
		String tipoPublico = dom.select("div[class=eventos-columna1]:matchesOwn(Tipo de p.blico.*) + div").text();
		String precios = dom.select("div[class=eventos-columna1]:matchesOwn(Precios.*) + div").text();
		String img = dom.select("div[class=eventos-descripcion] a").attr("href");
		String[] otrosDetalles = dom.select("div[class=eventos-pleca] > strong").html().split("\\<br.*/\\>");
		/** Configuracion de la categoria del  evento**/
		//TODO: Usar un expansor para setear los iconos de categoria.
		ec.setColor(color);
		ec.setIcon("glyphicon-question-sign");
		ec.setName(categoryName);
		sec.setIcon("glyphicon-question-sign");
		sec.setName(subcategoryName);
		ec.getSubcategories().add(sec);
		categories.add(ec);
		/** Configuracion del evento **/		
		e.setName(titulo);
		e.setExternalId(doc.getId());
		e.setHost(host);
		e.setDescription(descripcion);
		e.getCategories().addAll(categories);
		e.setLocation(recinto);
		e.setLocationAdress(recintoDir);
		e.setHoursDetails(horarios);
		e.setAudience(tipoPublico);
		e.setTicketPrices(precios);
		e.setPicture(img);
		e.getDates().addAll( extractMetaDate(doc.getMeta()));
		try{
			e.setStartDate(e.getDates().get(0));
			e.setEndDate(e.getDates().get( e.getDates().size() - 1));
		}catch(Exception ex){
			logger.warn("Parece que hubo un error al cargar las fechas:"
					+ "Causa:"+ex.getMessage());
			ex.printStackTrace();
			e.setStartDate(new Date());
			e.setEndDate(new LocalDate().dayOfMonth().withMaximumValue().toDate());
		}
		e.setOtherDetails(Arrays.asList(otrosDetalles));
		return e;
	}
	
	private List<Date> extractMetaDate(Map<String, Object> meta){
		 List<Long> longdates = (List<Long>) meta.get(CulturaUnamRepositoryExtractor.META_DATE_LIST);
		 List<Date> dates = new ArrayList<Date>();
		 for(Long l:longdates){
			 dates.add(new Date(l*1000));
		 }
		 return dates;
		
	}
}

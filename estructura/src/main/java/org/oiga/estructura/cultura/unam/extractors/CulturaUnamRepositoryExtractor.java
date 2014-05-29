package org.oiga.estructura.cultura.unam.extractors;


import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.oiga.estructura.model.beans.Documento;
import org.oiga.estructura.model.beans.Extraccion;
import org.oiga.estructura.utils.JsoupUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class CulturaUnamRepositoryExtractor {
	public static Logger logger = LoggerFactory
			.getLogger(CulturaUnamRepositoryExtractor.class);
	private String pagesUrl = "http://www.cultura.unam.mx/Administracion/files/GestionBusqueda.html.php";
	private String eventUrl = "http://www.cultura.unam.mx/agenda/MostrarEvento.html.php?eve_id=";
	private String calendarioUrl = "http://www.cultura.unam.mx/agenda/calendarioInfo.html";
	private String repoName = "Cultura UNAM";
	private String repoUrl = "http://www.cultura.unam.mx";
	private String logoUrl = "http://www.cultura.unam.mx/inicio/imgs/logo.gif";
	private String fileName;
	private Map<String, String> params = new HashMap<String, String>();
	private final String FILE_NAME_EXP = "html_cultura_unam_%s.json";
	public final static String META_DATE_LIST = "dates";
	private static final SimpleDateFormat FILE_DATE_PREFIX_FORMAT = new SimpleDateFormat(
			"yyyy_MM");
	private static final Pattern PAGE_FUNCTION_PATTERN = Pattern
			.compile("\\((.*?)\\)");
	private static final Pattern EVENT_FUNCTION_PATTERN = Pattern
			.compile("mostrarEvento\\((.*?),.*?");
	private LocalDate startDate;
	private LocalDate endDate;
	private ObjectMapper mapper = new ObjectMapper();
	
	
	public CulturaUnamRepositoryExtractor() {
		super();
		init();
	}

	private void init(){
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}
	
	public void setUp() {
		params.put("date", "");
		params.put("date2", "");
		params.put("fec", "mes");
		params.put("gen", "");
		params.put("operacion", "busqueda");
		params.put("publico", "");
		/*
		 * TODO: Nombre del archivo dependiente de los parametros de entrada
		 */
		fileName = String.format(FILE_NAME_EXP,
				FILE_DATE_PREFIX_FORMAT.format(new Date()));
		logger.info("Se extraera del repositorio \""+repoUrl+"\" y se guardara el archivo con el nombre: " + fileName);
	}
	
	private Extraccion buildExtraccion(){
		Extraccion extraccion = new Extraccion();
		extraccion.setFechaExtraccion(new Date());
		extraccion.setNombreRepositorio(repoName);
		extraccion.setUrlRepositorio(repoUrl);
		extraccion.setNombreArchivo(fileName);
		extraccion.setLogoRepositorio(logoUrl);
		return extraccion;
	}
	
	public void extractByDateRange(){
		Extraccion extraccion = buildExtraccion();
		logger.debug("Range "+startDate+" - "+endDate);
		for (LocalDate date = startDate; date.isBefore(endDate) || date.isEqual(endDate); date = date.plusDays(1))
		{
			int day = date.getDayOfMonth();
			List<Document> documents = new ArrayList<Document>();
			try {
				Document dayDoc = extractPage(date);
				List<Integer> pages = getPages(dayDoc);
				logger.debug("Day:"+day+", "+date.toString()+" Pages: "+pages);
				documents.add(dayDoc);
				for(Integer num:pages){
					Document doc;
					if(num == 1){
						doc = dayDoc;
					}
					else{
						doc = extractPage(date, Integer.toString(num));
					}
					List<String> ids = getIds(doc);
					logger.debug("\tId: " + ids);
					Map<String, Documento> prevDocuments = extraccion.getDocumentos();
					for(String id:ids){
						if(prevDocuments.containsKey(id)){
							Documento d = prevDocuments.get(id);
							List<Date> dates = (List<Date>) d.getMeta().get(META_DATE_LIST);
							dates.add(date.toDate());
						}else{
							List<Date> dates = new ArrayList<Date>();
							Documento d = getDocumento(id);
							dates.add(date.toDate());
							d.getMeta().put(META_DATE_LIST, dates);
							prevDocuments.put(id, d);
						}
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		try {
			mapper.writeValue(new File(fileName), extraccion);
		} catch ( IOException e) {
			throw new RuntimeException("No se pudo escribir el archivo "+fileName+", Error:"+e.getMessage());
		}
	}
	
	@Deprecated
	public void extract() {
		setUp();

		/* Extraer Identificadores */
		List<String> ids = getIds();
		/* Por cada identificador generar un documento */
		List<Documento> docs = getDocuments(ids);
		/* Generar extraccion a persistir */
		Extraccion extraccion = new Extraccion();
		//extraccion.setDocumentos(docs);
		extraccion.setFechaExtraccion(new Date());
		extraccion.setNombreRepositorio(repoName);
		extraccion.setUrlRepositorio(repoUrl);
		extraccion.setNombreArchivo(fileName);
		extraccion.setLogoRepositorio(logoUrl);
		ObjectMapper mapper = new ObjectMapper();
		mapper.enable(SerializationFeature.INDENT_OUTPUT);
		try {
			mapper.writeValue(new File(fileName), extraccion);
		} catch ( IOException e) {
			throw new RuntimeException("No se pudo escribir el archivo "+fileName+", Error:"+e.getMessage());
		}
	}
	@Deprecated
	public List<Documento> getDocuments(List<String> ids) {
		ArrayList<Documento> docs = new ArrayList<Documento>();
		for (String id : ids) {
			String url = eventUrl + id;
			try {
				Document doc = JsoupUtils.get(url);
				Element e = doc.select("fieldset[class=eventos-field]").first();
				Documento d = new Documento();
				d.setContenido(e.html());
				d.setFuente(url);
				d.setId(id);
				docs.add(d);
				logger.info("Se extrajo el documento : '"+e.select("div[class=eventos-titulo]").text()+"'");
			} catch (IOException e) {
				throw new RuntimeException("Hubo un error al obtener la " + url
						+ ", error: " + e.getMessage());
			}
		}
		return docs;
	}
	
	public Documento getDocumento(String id) {
		String url = eventUrl + id;
		try {
			Document doc = JsoupUtils.get(url);
			Element e = doc.select("fieldset[class=eventos-field]").first();
			Documento d = new Documento();
			d.setContenido(e.html());
			d.setFuente(url);
			d.setId(id);
			logger.info("Se extrajo el documento : '"
					+ e.select("div[class=eventos-titulo]").text() + "'");
			return d;
		} catch (IOException e) {
			throw new RuntimeException("Hubo un error al obtener la " + url
					+ ", error: " + e.getMessage());
		}
	}
	
	@Deprecated	
	public List<String> getIds() {
		ArrayList<String> ids = new ArrayList<String>();
		int pagesSize = getLastPageNumber();
		for (int i = 1; i <= pagesSize; i++) {
			try {
				Document doc = extractPage(i);
				Elements tags = doc.select("a[onclick~=mostrarEvento.*]");
				for (Element e : tags) {
					String jsFunc = e.attr("onclick");
					Matcher m = EVENT_FUNCTION_PATTERN.matcher(jsFunc);
					if (m.find()) {
						String id = m.group(1);
						if(!ids.contains(id)){
							ids.add(id);
						}
					} else {
						throw new RuntimeException(
								"No se pudo encontrar el id en la cadena : "
										+ jsFunc);
					}
				}
			} catch (IOException e) {
				throw new RuntimeException(
						"Hubo un error al extraer la pagina " + i + ", error: "
								+ e.getMessage());
			}
		}
		logger.debug("Identificadores : "+ids);
		return ids;
	}
	
	public List<String> getIds(Document doc) {
		ArrayList<String> ids = new ArrayList<String>();
		Elements tags = doc.select("a[onclick~=mostrarEvento.*]");
		for (Element e : tags) {
			String jsFunc = e.attr("onclick");
			Matcher m = EVENT_FUNCTION_PATTERN.matcher(jsFunc);
			if (m.find()) {
				String id = m.group(1);
				if (!ids.contains(id)) {
					ids.add(id);
				}
			} else {
				throw new RuntimeException(
						"No se pudo encontrar el id en la cadena : " + jsFunc);
			}
		}
		return ids;
	}
	@Deprecated
	private int getLastPageNumber() {
		int pageNumber = 0;
		try {
			Document d = extractPage(1);
			Elements els = d.select("a[onclick~=busqueda.enviarDatos(.*);]");
			Element el = els.select("a:matchesOwn(>>)").first();
			String input = el.attr("onclick");
			logger.debug("Cadena con el valor \"{}\" ", input);
			Matcher matcher = PAGE_FUNCTION_PATTERN.matcher(input);
			if (matcher.find()) {
				pageNumber = Integer.valueOf(matcher.group(1));
			} else {
				logger.error("No se encontro el valor esperado al extraer de la funcion : "
						+ input);
			}
			logger.debug("El valor de la extraccion es \"{}\" ", pageNumber);
		} catch (IOException e) {
			logger.error("No se pudo extraer el numero de paginas del repositorio : "
					+ e.getMessage());
		}
		return pageNumber;
	}
	private List<Integer> getPages(Document document) {
		ArrayList<Integer> pages = new ArrayList<>();
		Element center = document.select("center:has(b:matchesOwn(1))").first();
		if(center != null){
			for(Element e:center.children()){
				String page = e.text();
				pages.add(Integer.valueOf(page));
			}
		}
		return pages;
	}
	
	@Deprecated
	private Document extractPage(int page) throws IOException {
		params.put("inicio", Integer.toString(page));
		return JsoupUtils.post(pagesUrl, params);
	}
	
	private Document extractPage(LocalDate date, String inicio) throws IOException
	{
		HashMap<String, String> params = new HashMap<String, String>();
		params.put("yearID", Integer.toString(date.getYear()) );
		params.put("monthID", Integer.toString(date.getMonthOfYear()) );
		params.put("dayID", Integer.toString(date.getDayOfMonth()) );
		params.put("inicio", inicio );
		return JsoupUtils.get(calendarioUrl,params);
	}
	
	private Document extractPage(LocalDate date) throws IOException
	{
		return extractPage(date,"undefined");
	}

	public LocalDate getStartDate() {
		return startDate;
	}


	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}


	public LocalDate getEndDate() {
		return endDate;
	}


	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}

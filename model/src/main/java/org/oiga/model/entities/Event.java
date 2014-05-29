package org.oiga.model.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.GraphProperty;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;
import org.springframework.data.neo4j.support.index.IndexType;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@NodeEntity
public class Event {
	@GraphId
	private Long nodeId;
	@Indexed(indexType=IndexType.FULLTEXT, indexName="event_name")
	private String name;
	@Indexed(indexType=IndexType.FULLTEXT, indexName="event_description")
	private String description;
	private String externalId;
	private String url;
	private String host;
	private String hoursDetails;
	private String location;
	private String locationAdress;
	private String picture;
	private String audience;
	private String ticketPrices;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@GraphProperty(propertyType = Long.class)
	private Date startDate;
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	@GraphProperty(propertyType = Long.class)
	private Date endDate;
	@GraphProperty(propertyType = Long.class)
	private Date updatedTime = new Date();
	@RelatedTo(type="PERFORMED")
	@Fetch
	private SimpleVenue venue;
	@JsonManagedReference
	@RelatedToVia(type="INTERACTS")
	private Set<Interaction> interactions = new HashSet<Interaction>();
	@RelatedTo(type = "SOURCED")
	@Fetch	
	private Repository repository;
	@RelatedTo(type="TAGGED")
	@Fetch
	private Set<Tag> tags = new HashSet<Tag>();
	@RelatedTo(type = "CATEGORIZED")
	@Fetch	
	private Set<EventCategory> categories = new HashSet<EventCategory>();
	private List<String> otherDetails;
	private List <String> hours;
	//@GraphProperty(propertyType = Long.class)
	private List<Long> dates = new ArrayList<Long>();
	
	public String getPicture() {
		return picture;
	}
	public void setPicture(String picture) {
		this.picture = picture;
	}
	public String getAudience() {
		return audience;
	}
	public void setAudience(String audience) {
		this.audience = audience;
	}
	public String getTicketPrices() {
		return ticketPrices;
	}
	public void setTicketPrices(String ticketPrices) {
		this.ticketPrices = ticketPrices;
	}
	public Set<Interaction> getInteractions() {
		return interactions;
	}
	public void setInteractions(Set<Interaction> interactions) {
		this.interactions = interactions;
	}
	public List<String> getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(List<String> otherDetails) {
		this.otherDetails = otherDetails;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getLocationAdress() {
		return locationAdress;
	}
	public void setLocationAdress(String locationAdress) {
		this.locationAdress = locationAdress;
	}
	public String getHoursDetails() {
		return hoursDetails;
	}
	public void setHoursDetails(String hoursDetails) {
		this.hoursDetails = hoursDetails;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getUpdatedTime() {
		return updatedTime;
	}
	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}
	public SimpleVenue getVenue() {
		return venue;
	}
	public void setVenue(SimpleVenue venue) {
		this.venue = venue;
	}
	public Set<EventCategory> getCategories() {
		return categories;
	}
	public void setCategories(Set<EventCategory> categories) {
		this.categories = categories;
	}
	public Set<Tag> getTags() {
		return tags;
	}
	public void setTags(Set<Tag> tags) {
		this.tags = tags;
	}
	public List<String> getHours() {
		return hours;
	}
	public void setHours(List<String> hours) {
		this.hours = hours;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Repository getRepository() {
		return repository;
	}
	public void setRepository(Repository repository) {
		this.repository = repository;
	}
	public String getExternalId() {
		return externalId;
	}
	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}
	public List<Long> getDates() {
		return dates;
	}
	public void setDates(List<Long> dates) {
		this.dates = dates;
	}
	
	public EventCategory getCategory(){
		if(!categories.isEmpty()){
			return categories.iterator().next();
		}else{
			EventCategory eventCategory = new EventCategory();
			eventCategory.setColor("#E0EAF1");
			eventCategory.setName("Misc");
			eventCategory.setIcon("glyphicon-question-sign");
			return eventCategory;
		}
	}
}

package org.oiga.model.entities;

import java.util.HashSet;
import java.util.Set;

import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;

import com.fasterxml.jackson.annotation.JsonIgnore;

@NodeEntity
public class EventCategory {
	@GraphId
	private Long nodeId;
	@Indexed(indexName="event_category_name", unique=true)
	private String name;
	private String icon;
	private String color;
	@RelatedTo(type="HAS")
	@JsonIgnore
	private Set<EventCategory> subcategories = new HashSet<EventCategory>();
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	public Long getNodeId() {
		return nodeId;
	}
	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public Set<EventCategory> getSubcategories() {
		return subcategories;
	}
	public void setSubcategories(Set<EventCategory> subcategories) {
		this.subcategories = subcategories;
	}
}

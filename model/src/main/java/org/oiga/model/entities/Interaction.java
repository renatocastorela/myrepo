package org.oiga.model.entities;

import java.util.Date;

import org.springframework.data.neo4j.annotation.EndNode;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.RelationshipEntity;
import org.springframework.data.neo4j.annotation.StartNode;

import com.fasterxml.jackson.annotation.JsonBackReference;

@RelationshipEntity(type="INTERACTS")
public class Interaction {
	public enum State{ATTENDING, DECLINED, MAYBE, NOREPLY, NOT_INVITED, NA};
	@GraphId
    private Long    nodeId;
	private Boolean liked = false;
	private Integer views = 0;
	private Date lastInteraction = new Date();
	private State state = State.NA;
	@StartNode
	private User user;
	@EndNode
	@JsonBackReference
	private Event event;
	
	public Boolean getLiked() {
		return liked;
	}
	public void setLiked(Boolean liked) {
		this.liked = liked;
	}
	public Integer getViews() {
		return views;
	}
	public void setViews(Integer views) {
		this.views = views;
	}
	public Date getLastInteraction() {
		return lastInteraction;
	}
	public void setLastInteraction(Date lastInteraction) {
		this.lastInteraction = lastInteraction;
	}
	public State getState() {
		return state;
	}
	public void setState(State state) {
		this.state = state;
	}
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
	public Event getEvent() {
		return event;
	}
	public void setEvent(Event event) {
		this.event = event;
	}
}

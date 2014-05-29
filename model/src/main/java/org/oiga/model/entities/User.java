package org.oiga.model.entities;

import java.util.HashSet;
import java.util.Set;

import org.neo4j.graphdb.Direction;
import org.springframework.data.neo4j.annotation.Fetch;
import org.springframework.data.neo4j.annotation.GraphId;
import org.springframework.data.neo4j.annotation.Indexed;
import org.springframework.data.neo4j.annotation.NodeEntity;
import org.springframework.data.neo4j.annotation.RelatedTo;
import org.springframework.data.neo4j.annotation.RelatedToVia;

@NodeEntity
public class User {
	@GraphId
	private Long nodeId;
	private String facebookUsername;
	private String facebookUid;
	private String facebookThirdPartyId;
	@Indexed(indexName = "user_email")
	private String email;
	private String firstName;
	private String lastName;
	private String password;
	private String signInProvider;
	private String imageUrl;
	@RelatedTo(type = "HAS_ROLE")
	@Fetch
	private Role role;
	@RelatedToVia(type = "INTERACTS", direction=Direction.BOTH)
	@Fetch
	private Set<Interaction> interactions = new HashSet<Interaction>();

	public User() {
	}

	public String getFacebookUsername() {
		return facebookUsername;
	}

	public void setFacebookUsername(String facebookUsername) {
		this.facebookUsername = facebookUsername;
	}

	public String getFacebookUid() {
		return facebookUid;
	}

	public void setFacebookUid(String facebookUid) {
		this.facebookUid = facebookUid;
	}

	public String getFacebookThirdPartyId() {
		return facebookThirdPartyId;
	}

	public void setFacebookThirdPartyId(String facebookThirdPartyId) {
		this.facebookThirdPartyId = facebookThirdPartyId;
	}

	public Long getNodeId() {
		return nodeId;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSignInProvider() {
		return signInProvider;
	}

	public void setSignInProvider(String signInProvider) {
		this.signInProvider = signInProvider;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Set<Interaction> getInteractions() {
		return interactions;
	}

	public void setInteractions(Set<Interaction> interactions) {
		this.interactions = interactions;
	}

	public static class Builder {
		private Long nodeId;
		private String facebookUsername;
		private String facebookUid;
		private String facebookThirdPartyId;
		private String email;
		private String firstName;
		private String lastName;
		private String password;
		private String signInProvider;
		private String imageUrl;
		private Role role;
		private Set<Interaction> interactions;

		public Builder nodeId(Long nodeId) {
			this.nodeId = nodeId;
			return this;
		}

		public Builder facebookUsername(String facebookUsername) {
			this.facebookUsername = facebookUsername;
			return this;
		}

		public Builder facebookUid(String facebookUid) {
			this.facebookUid = facebookUid;
			return this;
		}

		public Builder facebookThirdPartyId(String facebookThirdPartyId) {
			this.facebookThirdPartyId = facebookThirdPartyId;
			return this;
		}

		public Builder email(String email) {
			this.email = email;
			return this;
		}

		public Builder firstName(String firstName) {
			this.firstName = firstName;
			return this;
		}

		public Builder lastName(String lastName) {
			this.lastName = lastName;
			return this;
		}

		public Builder password(String password) {
			this.password = password;
			return this;
		}

		public Builder signInProvider(String signInProvider) {
			this.signInProvider = signInProvider;
			return this;
		}

		public Builder imageUrl(String imageUrl) {
			this.imageUrl = imageUrl;
			return this;
		}

		public Builder role(Role role) {
			this.role = role;
			return this;
		}

		public Builder interactions(Set<Interaction> interactions) {
			this.interactions = interactions;
			return this;
		}

		public User build() {
			return new User(this);
		}
	}

	private User(Builder builder) {
		this.nodeId = builder.nodeId;
		this.facebookUsername = builder.facebookUsername;
		this.facebookUid = builder.facebookUid;
		this.facebookThirdPartyId = builder.facebookThirdPartyId;
		this.email = builder.email;
		this.firstName = builder.firstName;
		this.lastName = builder.lastName;
		this.password = builder.password;
		this.signInProvider = builder.signInProvider;
		this.imageUrl = builder.imageUrl;
		this.role = builder.role;
		this.interactions = builder.interactions;
	}
}

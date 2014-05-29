package org.oiga.web.dto;


import java.util.Collection;
import java.util.Set;

import org.oiga.model.entities.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.social.security.SocialUser;

public class UserDetails extends SocialUser {
	private static final long serialVersionUID = 1L;
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

	public UserDetails(String username, String password, boolean enabled,
			boolean accountNonExpired, boolean credentialsNonExpired,
			boolean accountNonLocked,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, enabled, accountNonExpired,
				credentialsNonExpired, accountNonLocked, authorities);
	}

	public UserDetails(String username, String password,
			Collection<? extends GrantedAuthority> authorities) {
		super(username, password, authorities);
	}
	public String getFacebookUsername() {
		return facebookUsername;
	}

	public String getFacebookUid() {
		return facebookUid;
	}

	public String getFacebookThirdPartyId() {
		return facebookThirdPartyId;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getPassword() {
		return password;
	}

	public String getSignInProvider() {
		return signInProvider;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public Role getRole() {
		return role;
	}




	public static class Builder {
		
		private Set<GrantedAuthority> authorities;
		private String username;
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
		
		public Builder authorities(Set<GrantedAuthority> authorities) {
			this.authorities = authorities;
			return this;
		}
		
		public Builder username(String username) {
			this.username = username;
			return this;
		}

		public UserDetails build() {
			UserDetails userDetails = new UserDetails(facebookUsername, password, authorities);
			userDetails.facebookUsername = facebookUsername;
			userDetails.facebookUid = facebookUid;
			userDetails.facebookThirdPartyId = facebookThirdPartyId;
			userDetails.email = email;
			userDetails.firstName = firstName;
			userDetails.lastName = lastName;
			userDetails.password = password;
			userDetails.signInProvider = signInProvider;
			userDetails.imageUrl = imageUrl;
			userDetails.role = role;
			return userDetails;
		}

	
	}
}

package org.oiga.web.utils;

import java.util.HashSet;

import org.oiga.model.entities.Role;
import org.oiga.model.entities.User;
import org.oiga.web.dto.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;

public class UserUtils {
	private static Logger logger = LoggerFactory.getLogger(UserUtils.class);

	public static User prefillUser(Connection<?> connection) {
		User user = null;

		if (connection != null) {
			UserProfile up = connection.fetchUserProfile();
			logger.debug("Fetching user profile {} ", up.getEmail());
			user = new User.Builder()
					.email(up.getEmail())
					.firstName(up.getFirstName())
					.lastName(up.getLastName())
					.build();
			user.setImageUrl(connection.getImageUrl());
			Role role = new Role();
			role.setName("ROLE_OIGA_USER");
			user.setRole(role);
			try {
				Facebook f = (Facebook) connection.getApi();
				FacebookProfile p = f.userOperations().getUserProfile();
				user.setFacebookThirdPartyId(p.getThirdPartyId());
				user.setFacebookUid(p.getId());
				user.setFacebookUsername(p.getUsername());
				user.setPassword(p.getId());
			} catch (Exception e) {
				logger.error("There is a problem trying to fill facebook's user data: "
						+ e.getMessage());
				throw new RuntimeException(e);
			}
		}
		return user;
	}

	public static void signIn(User user) {
		logger.debug("Iniciando sesion con el usuario : {}", user.getFacebookUsername());
		HashSet<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority( user.getRole().getName()) );
		UserDetails userDetails = new UserDetails.Builder()
									.email(user.getEmail())
									.facebookThirdPartyId(user.getFacebookThirdPartyId())
									.facebookUid(user.getFacebookUid())
									.facebookUsername(user.getFacebookUsername())
									.firstName(user.getFirstName())
									.imageUrl(user.getImageUrl())
									.lastName(user.getLastName())
									.role(user.getRole())
									.signInProvider(user.getSignInProvider())
									.username(user.getFacebookUsername())
									.password(user.getEmail())
									.authorities(authorities)
									.build();
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(userDetails, user.getPassword(), authorities));
	}
}

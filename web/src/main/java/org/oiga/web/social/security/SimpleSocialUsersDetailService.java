package org.oiga.web.social.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUsersDetailService implements SocialUserDetailsService {
	
	static Logger logger = LoggerFactory.getLogger(SimpleSocialUsersDetailService.class); 
	
	private UserDetailsService userDetailsService;

	public SimpleSocialUsersDetailService(UserDetailsService userDetailsService) {
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String userId) throws UsernameNotFoundException, DataAccessException {
		logger.debug("Cargando al usuario '"+userId+"' del repositorio local");
		UserDetails userDetails = userDetailsService.loadUserByUsername(userId);
		if(userDetails == null){
			logger.error("Usuario No encontrado"+userId);
			throw new UsernameNotFoundException("Usuario no encontrado");
		}
		return new SocialUser(userDetails.getUsername(), userDetails.getPassword(), userDetails.getAuthorities());
	}

	
}

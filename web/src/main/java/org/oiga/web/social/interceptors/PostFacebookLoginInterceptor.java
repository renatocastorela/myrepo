package org.oiga.web.social.interceptors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class PostFacebookLoginInterceptor implements ConnectInterceptor<Facebook>{
	private Logger logger = LoggerFactory.getLogger(PostFacebookLoginInterceptor.class);
	
	@Override
	public void postConnect(Connection<Facebook> conn, WebRequest arg1) {
		String name = conn.getDisplayName();
		logger.debug("Usuario conectado "+name);
	}

	@Override
	public void preConnect(ConnectionFactory<Facebook> arg0,
			MultiValueMap<String, String> arg1, WebRequest arg2) {
		// TODO Auto-generated method stub
		
	}

}

package org.oiga.model.services;

import org.oiga.model.entities.User;
import org.oiga.model.exceptions.DuplicateUserException;
import org.oiga.model.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserRepository userRepository;
	
	@Transactional
	public User registerNewUser(User user) throws DuplicateUserException{
		logger.debug("Registering new user account {}"+user.getEmail());
		if(this.emailExist(user.getEmail())){
			  logger.debug("Email: {} exists. Throwing exception.", user.getEmail());
	          throw new DuplicateUserException("The email address: " + user.getEmail() + " is already in use.");
		}
		logger.debug("Persisting new user with email: {} ", user.getEmail());
		return userRepository.save(user);
	}
	
	public User findByUsername(String facebookUsername){
		User user = userRepository.findByfacebookUsername(facebookUsername);
		return user;
	}

	private boolean emailExist(String email) {
		User user = null;
		try {
			user = userRepository.findByEmail(email);
		} catch (Exception e) {
			logger.debug("Email not found");
		}
		if (user != null) {
			return true;
		}
		return false;
	}

}

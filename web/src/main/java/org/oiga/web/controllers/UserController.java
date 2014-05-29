package org.oiga.web.controllers;

import javax.validation.Valid;

import org.oiga.model.entities.User;
import org.oiga.model.services.UserService;
import org.oiga.web.social.signin.SignInUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.UserProfile;
import org.springframework.social.connect.web.ProviderSignInUtils;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.FacebookProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.WebRequest;


@Controller
@SessionAttributes("user")
@RequestMapping("users")
public class UserController {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    protected static final String ERROR_CODE_EMAIL_EXIST = "NotExist.user.email";
    protected static final String USER_ATTR = "user";
    protected static final String VIEW_NAME_REGISTRATION_PAGE = "user/signup";
    
    @Autowired
    private UserService userService;  
    
    /**
     * Renders the registration page.
     */
    @RequestMapping(value = "register", method = RequestMethod.GET)
    public String showRegistrationForm(WebRequest request, Model model) {
        logger.debug("Rendering registration page.");
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
        User user = prefillUserForm(connection);
        model.addAttribute(USER_ATTR, user);        
        return VIEW_NAME_REGISTRATION_PAGE;
    }
    
    /**
     * Processes the form submissions of the registration form.
     */
    @RequestMapping(value ="register", method = RequestMethod.POST)
    public String registerUserAccount(@Valid @ModelAttribute(USER_ATTR) User user,
    									BindingResult result,
    									WebRequest request) {
    	logger.debug("Registering user account with information: {}", user);
        if (result.hasErrors()) {
          logger.debug("Validation errors found. Rendering form view.");
            return VIEW_NAME_REGISTRATION_PAGE;
       }

        logger.debug("No validation errors found. Continuing registration process.");
        try{
        	userService.registerNewUser(user);
        }catch(Exception e){
        	  logger.debug("An email address was found from the database. Rendering form view.");
        }
        Connection<?> connection = ProviderSignInUtils.getConnection(request);
    	UserProfile socialMediaProfile = connection.fetchUserProfile();
        SignInUtils.signin(socialMediaProfile.getUsername());
        logger.debug("User {} has been signed in", socialMediaProfile);
        return "redirect:/";
    }
    
    private User prefillUserForm(Connection<?> connection){
    	User user = null;
    	
    	if(connection != null ){
    		logger.debug("Fetching user profile");
    		UserProfile up = connection.fetchUserProfile();
    		user = new User.Builder()
    						.email(up.getEmail())
    						.firstName(up.getFirstName())
    						.lastName(up.getLastName())
    						.build();
    		try{
    			Facebook f =  (Facebook) connection.getApi();
    			FacebookProfile p = f.userOperations().getUserProfile();
    			user.setFacebookThirdPartyId(p.getThirdPartyId());
    			user.setFacebookUid(p.getId());
    			user.setFacebookUsername(p.getUsername());
    		}catch(Exception e){
    			logger.warn("There is a problem trying to fill facebook's user data: "+e.getMessage());
    		}
    	}else{
    		logger.debug("Creating empty user");
    		user = new User();
    	}
    	return user;
    }

}

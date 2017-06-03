package com.pollapp.controller;

import java.security.Principal;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pollapp.service.UserService;
import com.pollapp.transfer.PasswordDTO;
/**
 * Controller that handles 
 * password changing.
 * Redirects to logout or 
 * error urls based on service 
 * method result
 *
 */
@RestController
public class UserPasswordController {

	private static final Logger logger = Logger.getLogger(UserPasswordController.class);

	private UserService service;
	
	@RequestMapping(value="/user/password",method=RequestMethod.POST)
	public ResponseEntity<Void> changePassword(@RequestBody PasswordDTO transfer, Principal principal,HttpServletRequest http){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info(String.format("Changing password for user %s \n", principal.getName()));
		    }
			service.changePassword(transfer.getNewPassword(),transfer.getOldPassword(),principal);
			http.logout();
			return ResponseEntity.ok().build();
		}catch(Exception ex){
			logger.error("Exception caught changing password for user " + principal.getName() + "\n" + ex);
		}
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

}

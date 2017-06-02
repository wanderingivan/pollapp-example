package com.pollapp.controller;


import java.security.Principal;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.pollapp.model.User;
import com.pollapp.service.UserService;


/**
 * Controller that handles basic REST requests 
 * for users
 */
@RestController
public class UserRestController {

	private static final Logger logger = Logger.getLogger(UserRestController.class);
	
	private UserService service;
	
	@RequestMapping(value="/user",method=RequestMethod.POST)
	public ResponseEntity<User> addUser(@RequestBody User user){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info("Creating user " + user);
		    }
			service.createUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK); 
		}catch(Exception ex){
			logger.error("Error caught creating user "+ user + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value="/user",method=RequestMethod.GET)
	public Principal user(Principal user){
		return user;
	}
	
	@RequestMapping(value="/users", method=RequestMethod.GET)
	public ResponseEntity<List<User>> latest(){
		try{
			return new ResponseEntity<>(service.findUsers(""),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching latest users" + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.GET)
	public ResponseEntity<User> getById(@PathVariable long id){
		try{
			return new ResponseEntity<>(service.getUserById(id),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching user with id " + id + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/user/view/{name}",method=RequestMethod.GET)
	public ResponseEntity<User> getByUsername(@PathVariable String name){
		try{
			return new ResponseEntity<>(service.getUserByUsername(name),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching users with name " + name + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/user/search/{name}",method=RequestMethod.GET)
	public ResponseEntity<List<User>> search(@PathVariable String name){
		try{
			return new ResponseEntity<>(service.findUsers(name),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching users with name " + name + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.PUT)
	public ResponseEntity<User> editUser(@PathVariable long id,
			                              @RequestBody User user,
			                                      Principal principal){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info(String.format("User %s editing user %s",principal.getName(), user));
		    }
			service.editUser(user);
			return new ResponseEntity<User>(user, HttpStatus.OK); 
		}catch(Exception ex){
			logger.error("Error caught editing user "+ user + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/user/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> deleteUser(@PathVariable long id,Principal principal){
		try{
		    if(logger.isInfoEnabled()){
		        logger.info(String.format("User %s deleting user with id %d",principal.getName(),id ));
		    }
			service.deleteUser(id);
			return new ResponseEntity<Void>(HttpStatus.NO_CONTENT); 
		}catch(Exception ex){
			logger.error("Error caught deleting user with id "+ id + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}
	
}

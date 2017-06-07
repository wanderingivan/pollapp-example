package com.pollapp.service;

import java.security.Principal;
import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.pollapp.exception.IncorrectPasswordException;
import com.pollapp.model.User;


/**
 * 
 * Service Layer interface providing
 * basic CRUD operations for objects
 * as well as a change password method
 * Defines method security checks.
 * @see com.pollapp.model.User
 */
public interface UserService {
	
	/**
	 * Creates a user if the acting user 
	 * is anonymous
	 */
	@PreAuthorize("isAnonymous()")
	long createUser(User user);
	
	/**
	 * Edits a user if the acting user
	 * has permission for that user or 
	 * has a valid admin role
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#user, 'WRITE')")	
	void editUser(User user);
	
	List<User> findUsers(String username);
	
	User getUserByUsername(String username);
	
	/**
	 * Retrieves a User by its id 
	 * @param userId
	 * @return User
	 * @throws MissingUserException if the userId is 
	 * not in the data store
	 */
	User getUserById(long pollId);

	/**
	 * Deletes a user if the acting user
	 * has a valid admin role
	 */	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	void deleteUser(long userId);

	/**
	 * Changes a password for a user 
	 * @param newPassword
	 * @param oldPassword
	 * @param principal
	 * @throws IncorrectPasswordException
	 */
	void changePassword(String newPassword, String oldPassword,
			Principal principal);

    void changeImage(String principal, String fileName);

}

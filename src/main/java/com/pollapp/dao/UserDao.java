package com.pollapp.dao;


import java.util.List;

import com.pollapp.exception.IncorrectPasswordException;
import com.pollapp.exception.MissingUserException;
import com.pollapp.model.User;


/**
 * 
 * DAO interface providing
 * basic CRUD operations for user objects
 * @see com.pollapp.model.User
 */
public interface UserDao {
	/**
	 * Creates a user in db
	 * @param user
	 * @return the newly create user's id
	 */
	long createUser(User user);
	
	User retrieveUserByUsername(String username);
	
	/**
	 * Retrieves a user by its id
	 * @param userId
	 * @return
	 * @throws MissingUserException
	 */
	User retrieveUserById(long userId);
	/**
	 * Updates an existing user
	 * @param user
	 */
	void updateUser(User user);
	
	void deleteUser(long userId);
	
	/**
	 * Searches for users with names like
	 * @param username
	 * @return
	 */
	List<User> findUsers(String username);

	/**
	 * Changes a user's password if the 
	 * oldPassword matches the password stored 
	 * in db
	 * @param newPassword
	 * @param oldPassword
	 * @param principal the user to change the password for
	 * @throws IncorrectPasswordException
	 */
	void changePassword(String principal, String newPassword);
	
	String getPassword(String principal);
	
}

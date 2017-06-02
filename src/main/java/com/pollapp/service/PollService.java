package com.pollapp.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.pollapp.model.Option;
import com.pollapp.model.Poll;


/**
 * 
 * Service Layer interface providing
 * basic CRUD operations for poll objects
 * as well as voting methods
 * @see com.pollapp.model.Poll
 * Defines method security checks.
 */
public interface PollService {

	/**
	 * Creates a poll if the acting user 
	 * is authenticated
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	long createPoll(Poll poll);
	
	/**
	 * Edits a poll if the acting user
	 * has permission for that poll or 
	 * has a valid admin role
	 * @param poll
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#poll,'WRITE')")
	void editPoll(Poll poll);
	
	/**
	 * Retrieves a Poll by its id 
	 * @param pollId
	 * @return Poll
	 * @throws MissingPollException if the pollId is 
	 * not in the data store
	 */
	Poll getPollById(long pollId);
	
	List<Poll> getPollbyName(String pollName);
	
	List<Poll> findPolls(String name);

	/**
	 * Returns the 8 most recent polls.
	 * 
	 */
	List<Poll> recent();

	/**
	 * Increases a poll's total vote count
	 * and the selected option's votes
	 * @param optionId
	 * @param pollId
	 */
	void vote(long optionId, long pollId);
	
	
	/**
	 * Adds an option to a poll
	 * if the user is authenticated
	 * @param o
	 * @param pollId
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	void addOption(Option o, long pollId);
	
	/**
	 * Deletes a poll if the acting user
	 * has permission for that poll or 
	 * has a valid admin role
	 * @param poll
	 */	
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasPermission(#poll,'WRITE')")
	void deletePoll(Poll poll);

	List<Poll> getUserPolls(long userId);

	/** 
	 * Load recent polls from index
	 * @param index the index to start from
	 * @return
	 */
	List<Poll> getPolls(Long index);

}

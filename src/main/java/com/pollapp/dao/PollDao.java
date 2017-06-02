package com.pollapp.dao;

import java.util.List;

import com.pollapp.model.Option;
import com.pollapp.model.Poll;


/**
 * DAO interface providing
 * basic CRUD operations for poll objects
 * as well as voting methods
 * @see com.pollapp.model.Poll
 */
public interface PollDao {

	/**
	 * Creates a poll in the database
	 * @param poll
	 * @return the newly created Poll's id
	 */
	long createPoll(Poll poll);
	
	/**
	 * Retrieve a poll from the database
	 * @param pollId the id of the poll to retrieve
	 * @return
	 * @throws MissingPollException
	 */
	Poll retrievePollById(long pollId);
	
	List<Poll> retrievePollByName(String pollName);
	
	/**
	 * Retrieve all polls owned by a user
	 * @param userId the id of the poll owning user
	 * @return
	 * @throws MissingUserException
	 */
	List<Poll> retrieveUserPolls(long userId);
	
	/**
	 * Retrieves the 10 most recent polls.
	 * @return
	 */
	List<Poll> retrieveRecentPolls();
	
	/**
	 * Updates an existing poll in db
	 * @param poll
	 */
	void updatePoll(Poll poll);
	
	/**
	 * Updates an option's name
	 * @param o
	 */
	void updateOption(Option o);
	
	/**
	 * Deletes a poll from the db
	 * @param pollId
	 */
	void deletePoll(long pollId);
	
	/**
	 * Increases a poll's total vote count
	 * and the selected option's votes
	 * @param optionId
	 * @param pollId
	 */
	void vote(long optionId, long pollId);
	
	
	/**
	 * Creates a new option and asociates it with an 
	 * existing poll
	 * @param o
	 * @param pollId
	 */
	void addOption(Option o, long pollId);

	/**
	 * Selects polls with name that include
	 * @param name
	 * in the db
	 * @return
	 */
	List<Poll> findPolls(String name);

	/** Load polls starting from index
	 * 
	 */
	List<Poll> loadPolls(Long index);
	

	
}

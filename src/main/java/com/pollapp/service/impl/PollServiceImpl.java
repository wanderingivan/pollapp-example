package com.pollapp.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.model.MutableAcl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pollapp.dao.PollDao;
import com.pollapp.model.Option;
import com.pollapp.model.Poll;
import com.pollapp.service.PollService;

/**
 * Implementation of PollService
 * that provides caching and transaction 
 * support and delegates CRUD options to a dao.
 * @see com.pollapp.dao.PollDao
 *
 */
@Service
public class PollServiceImpl implements PollService {

	private PollDao dao;
	private MutableAclService aclService;
	
	@Autowired
	public PollServiceImpl(PollDao dao, MutableAclService service) {
		super();
		this.dao = dao;
		this.aclService = service;
	}

	@Override
	@Transactional
	@CacheEvict(value="recent",allEntries=true)
	public long createPoll(Poll poll) {
		long pollId =dao.createPoll(poll);
		poll.setId(pollId);
		createAcl(poll);
		return pollId;
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value="polls", key="#poll.id"),
					@CacheEvict(value="recent",allEntries=true)})
	public void editPoll(Poll poll) {
		dao.updatePoll(poll);
	}

	@Override
	@Transactional
	@Cacheable(value="polls", key="#pollId")
	public Poll getPollById(long pollId) {
		return dao.retrievePollById(pollId);
	}

	@Override
	@Transactional
	public List<Poll> getPollbyName(String pollName) {
		throw new UnsupportedOperationException("Remove from spec");
	}
	
	@Override
	@Transactional
	public List<Poll> findPolls(String name) {
		return dao.findPolls(name);
	}
	
	@Override
	@Transactional
	@Cacheable(value="recent", key="#root.methodName")
	public List<Poll> recent(){
		return dao.retrieveRecentPolls();
	}
	
	@Override
	@Transactional
	@CacheEvict(value="polls", key="#pollId")
	public void vote(long optionId, long pollId) {
		dao.vote(optionId,pollId);
	}
	
	
	@Override
	@Transactional	
	@CacheEvict(value="polls", key="#pollId")
	public void addOption(Option o, long pollId) {
		dao.addOption(o, pollId);
	}

	@Override
	@Transactional
	@Caching(evict={@CacheEvict(value="polls", key="#poll.id"),
					@CacheEvict(value="recent",allEntries=true)})
	public void deletePoll(Poll poll) {
		dao.deletePoll(poll.getId());
		deleteAcl(poll.getId());
	}
	
	@Override
	@Transactional
	public List<Poll> getUserPolls(long userId){
		return dao.retrieveUserPolls(userId);
	}
	/**
	 * Adds an access control list 
	 * granting the acting user write and read permissions on the poll
	 * @param poll
	 */
	private void createAcl(Poll poll) {
		MutableAcl acl = aclService.createAcl(new ObjectIdentityImpl(poll.getClass(), poll.getId()));
		acl.insertAce(acl.getEntries().size(), BasePermission.WRITE, acl.getOwner(), true);
		acl.insertAce(acl.getEntries().size(), BasePermission.READ, acl.getOwner(), true);
		aclService.updateAcl(acl);
	}
	
	private void deleteAcl(long pollId){
		aclService.deleteAcl(new ObjectIdentityImpl(Poll.class, pollId), true);
	}

	@Override
	public List<Poll> getPolls(Long index) {
		return dao.loadPolls(index);
	}


}

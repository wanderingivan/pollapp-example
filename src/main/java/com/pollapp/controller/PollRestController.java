package com.pollapp.controller;

import java.security.Principal;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.WebUtils;

import com.pollapp.model.Option;
import com.pollapp.model.Poll;
import com.pollapp.model.User;
import com.pollapp.service.PollService;


/**
 * Controller that handles basic REST requests 
 * as well as voting
 * for polls
 */
@RestController
public class PollRestController {

	private static final Logger logger = Logger.getLogger(PollRestController.class);
	
	private PollService service;
	
	@RequestMapping(value="/polls",method=RequestMethod.GET)
	public ResponseEntity<List<Poll>> recent(){
	    if(logger.isTraceEnabled()){
	        logger.trace("Getting latest");
	    }
		try{
			return new ResponseEntity<>(service.recent(),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching latest polls" + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/polls",method=RequestMethod.POST)
	public ResponseEntity<Poll> createPoll(@RequestBody Poll poll,BindingResult bindingResult,Principal principal){
	    if(logger.isInfoEnabled()){
	        logger.info(String.format("User %s creating poll %s", principal.getName(), poll));
	    }
		try{
			poll.setOwner(new User(principal.getName()));
			long id = service.createPoll(poll);
			poll.setId(id);
			return new ResponseEntity<>(poll,HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught creating poll "+ poll + "\n" +ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/polls/{id}", method=RequestMethod.GET)
	public ResponseEntity<Poll> retrievePoll(@PathVariable Long id){
	    
		try{
			return new ResponseEntity<>(service.getPollById(id),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching poll with id " + id + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/polls/{id}",method=RequestMethod.PUT)
	public ResponseEntity<Poll> updatePoll(@PathVariable long id, @RequestBody Poll poll,Principal principal){
	    if(logger.isInfoEnabled()){
	        logger.info(String.format("User %s editing poll %s", principal.getName(), poll));
	    }
		try{
			service.editPoll(poll);
			return new ResponseEntity<>(poll,HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught editing poll "+ poll + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value="/polls/{id}",method=RequestMethod.DELETE)
	public ResponseEntity<Void> deletePoll(@PathVariable long id){
		try{
			service.deletePoll(new Poll(id));
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}catch(Exception ex){
			logger.error("Error caught deleting poll with id "+ id + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/polls/{id}/option",method=RequestMethod.POST)
	public ResponseEntity<Void> addOption(     @RequestBody Option option,
			                              @PathVariable("id") long pollId, 
			                                             Principal principal){
	    if(logger.isInfoEnabled()){
	        logger.info(String.format("User %s adding option %s to poll with id %d",principal.getName(),option,pollId));
	    }
		try{
			service.addOption(option, pollId);
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception ex){
			logger.error(String.format("Error caught adding option %s to poll with id %d \n %s ",option, pollId,ex));
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/polls/search/{name}",method=RequestMethod.GET)
	public ResponseEntity<List<Poll>> search(@PathVariable String name){
		try{
			return new ResponseEntity<>(service.findPolls(name),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching polls with name " + name + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@RequestMapping(value="/polls/index/{index}")
	public ResponseEntity<List<Poll>> loadPolls(@PathVariable Long index){
		try{
			List<Poll> polls = service.getPolls(index);
			if(polls.size() < 1){
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(polls,HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching polls from " + index + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);	
	}
	@RequestMapping(value="/polls/user/{id}",method=RequestMethod.GET)
	public ResponseEntity<List<Poll>> userPolls(@PathVariable Long id){
		try{
			return new ResponseEntity<>(service.getUserPolls(id),HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when fetching polls for user with id " + id + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@RequestMapping(value="/polls/{pollId}/vote/{optionId}",method=RequestMethod.POST)
	public ResponseEntity<Void> vote( @PathVariable("pollId") long pollId, 
			                        @PathVariable("optionId") long optionId,
			                                    HttpServletRequest request,
			                                   HttpServletResponse response){
		try{
			
			String pollCookieName = "poll" + pollId;
			
			Cookie cookie = WebUtils.getCookie(request, pollCookieName);
			if(cookie != null){
				return new ResponseEntity<>(HttpStatus.CONFLICT);
			}
		    if(logger.isDebugEnabled()){	
		        logger.debug(String.format("Adding a vote for option %d on poll %d ",optionId,pollId));
		    }
			service.vote(optionId, pollId); 
			cookie = new Cookie(pollCookieName, "1");
			response.addCookie(cookie);
			
			return new ResponseEntity<>(HttpStatus.OK);
		}catch(Exception ex){
			logger.error("Error caught when voting for poll " + pollId + "\n" + ex);
		}
		return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@Autowired
	public void setService(PollService service) {
		this.service = service;
	}
	
}

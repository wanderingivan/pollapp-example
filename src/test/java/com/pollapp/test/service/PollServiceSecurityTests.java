package com.pollapp.test.service;

import java.io.File;
import java.net.MalformedURLException;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.pollapp.dao.PollDao;
import com.pollapp.model.Option;
import com.pollapp.model.Poll;
import com.pollapp.service.PollService;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class PollServiceSecurityTests extends AbstractServiceTest {

	private PollService service;
	private MutableAclService aclService;
	private PollDao mockDao;
	

	@Test(expected=AccessDeniedException.class)
	@WithAnonymousUser
	public void testCreateAccessDenied(){
		Poll poll = new Poll("name", null, null, null);
		service.createPoll(poll);
	}
	
	@Test
	@WithMockUser(username="a username",password="",authorities={"ROLE_USER"})
	public void testCreatePollAcl(){	
		Poll poll = new Poll("name", null, null, null);
		long id = service.createPoll(poll);
		Mockito.verify(mockDao,Mockito.times(1)).createPoll(poll);
		Acl acl = aclService.readAclById(new ObjectIdentityImpl(Poll.class,id)); 
		assertNotNull(acl);
		assertEquals(new PrincipalSid("a username"),acl.getOwner());
		assertNotNull(acl.getEntries());
		assertEquals(2, acl.getEntries().size());
		for(AccessControlEntry ace : acl.getEntries()){
			assertEquals(new PrincipalSid("a username"), ace.getSid());
			assertTrue(ace.isGranting());
		}
		assertEquals(BasePermission.WRITE, acl.getEntries().get(0).getPermission());
		assertEquals(BasePermission.READ, acl.getEntries().get(1).getPermission());
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="user",password="",authorities={"ROLE_USER"})
	public void testDeleteAccessDenied(){
		service.deletePoll(new Poll(1));
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="user",password="",authorities={"ROLE_USER"})
	public void testEditAccessDenied(){
		Poll poll = new Poll("new name", null, null, null);
		poll.setId(1);
		service.editPoll(poll);
	}	

	@Test
	@WithMockUser(username="username3",password="password",authorities={"ROLE_USER"})
	public void testEditAccesGrantedToOwningUser(){
		Poll poll = new Poll("new name", null, null, null);
		poll.setId(1);
		service.editPoll(poll);
		Mockito.verify(mockDao,Mockito.times(1)).updatePoll(poll);
	}
	
	@Test
	@WithMockUser(username="user",password="",authorities={"ROLE_ADMIN"})
	public void testEditAccessGrantedToAdmin(){
		Poll poll = new Poll("new name", null, null, null);
		poll.setId(1);
		service.editPoll(poll);
		Mockito.verify(mockDao,Mockito.times(1)).updatePoll(poll);
	}
	
	@Test
	@WithMockUser(username="username3",password="",authorities={"ROLE_USER"})
	public void testDeleteAccessGrantedToOwningUser(){
		service.deletePoll(new Poll(1));
		Mockito.verify(mockDao,Mockito.times(1)).deletePoll(1);
	}
		
	@Test
	@WithMockUser(username="user",password="",authorities={"ROLE_ADMIN"})
	public void testDeleteAccessGrantedToAdmin(){
		service.deletePoll(new Poll(1));
		Mockito.verify(mockDao,Mockito.times(1)).deletePoll(1);
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithAnonymousUser
	public void testAccessDeniedAddOption(){
		service.addOption(new Option(), 1);
	}
	
	@Test
	@WithMockUser(username="user",password="",authorities={"ROLE_USER"})
	public void testAccessGrantedAddOption(){
		Option option = new Option();
		service.addOption(option, 1);
		Mockito.verify(mockDao,Mockito.times(1)).addOption(option,1);
	}
	
	@Autowired
	public void setService(PollService service) {
		this.service = service;
	}

	@Autowired
	public void setAclService(MutableAclService aclService) {
		this.aclService = aclService;
	}

	@Autowired
	public void setPollDao(PollDao pollDao) {
		this.mockDao = pollDao;
	}

	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("SecurityTestDataSet.xml")));
	}

}

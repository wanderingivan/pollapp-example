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
import org.springframework.security.acls.domain.ObjectIdentityImpl;
import org.springframework.security.acls.domain.PrincipalSid;
import org.springframework.security.acls.domain.BasePermission;
import org.springframework.security.acls.model.AccessControlEntry;
import org.springframework.security.acls.model.Acl;
import org.springframework.security.acls.model.MutableAclService;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;

import com.pollapp.dao.UserDao;
import com.pollapp.model.User;
import com.pollapp.service.UserService;

@DirtiesContext(classMode = ClassMode.AFTER_EACH_TEST_METHOD)
public class UserServiceSecurityTests extends AbstractServiceTest {

	private UserService service;
	private MutableAclService aclService;
	private UserDao userDao;
	
	@Test
	@WithAnonymousUser
	public void testCreateUserAcl(){
		User user = new User("a username","email","description","password");
		long id = service.createUser(user);
		Acl acl = aclService.readAclById(new ObjectIdentityImpl(User.class,id)); 
		Mockito.verify(userDao,Mockito.times(1)).createUser(user);
		assertNotNull(acl);
		assertNotNull(acl.getEntries());
		assertEquals(2, acl.getEntries().size());
		assertEquals(new PrincipalSid("a username"),acl.getOwner());
		for(AccessControlEntry ace : acl.getEntries()){
			assertEquals(new PrincipalSid("a username"), ace.getSid());
			assertTrue(ace.isGranting());
		}
		assertEquals(BasePermission.WRITE, acl.getEntries().get(0).getPermission());
		assertEquals(BasePermission.READ, acl.getEntries().get(1).getPermission());
	}
	
	/*
	 * Creating a user should only be allowed for anonymous users
	 */
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="user",password="",authorities={"ROLE_USER","ROLE_ADMIN"})
	public void testCreateAccessDeniedException(){
		service.createUser(new User("a username","email","description","password"));
	}
	
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="user",password="",authorities={"ROLE_USER"})
	public void testDeleteAccessDeniedException(){
		service.deleteUser(2);
	}
	
	@Test(expected=AccessDeniedException.class)
	@WithMockUser(username="user",password="",authorities={"ROLE_USER"})
	public void testEditAccessDenied(){
		User user = new User("a username","email","description","password");
		user.setId(2);
		service.editUser(user);
	}	
	
	@Test
	@WithMockUser(username="username3",password="password",authorities={"ROLE_USER"})
	public void testEditAccesGrantedToOwningUser(){
		User user = new User("a username","email","description","password");
		user.setId(2);
		service.editUser(user);
		Mockito.verify(userDao,Mockito.times(1)).updateUser(user);
	}
	
	@Test
	@WithMockUser(username="user",password="",authorities={"ROLE_ADMIN"})
	public void testEditAccessGrantedToAdmin(){
		User user = new User("a username","email","description","password");
		user.setId(2);
		service.editUser(user);
		Mockito.verify(userDao,Mockito.times(1)).updateUser(user);
	}

	@Test
	@WithMockUser(username="user",password="",authorities={"ROLE_ADMIN"})
	public void testDeleteAccessGrantedToAdmin(){
		service.deleteUser(2);
		Mockito.verify(userDao,Mockito.times(1)).deleteUser(2);
	}

	@Autowired
	public void setUserDao(UserDao userDao) {
		this.userDao = userDao;
	}

	@Autowired
	public void setService(UserService service) {
		this.service = service;
	}

	@Autowired
	public void setAclService(MutableAclService aclService) {
		this.aclService = aclService;
	}

	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("SecurityTestDataSet.xml")));
	}
	
}

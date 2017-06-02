package com.pollapp.test.dao;

import java.io.File;
import java.net.MalformedURLException;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pollapp.dao.UserDao;
import com.pollapp.exception.MissingUserException;
import com.pollapp.model.User;

public class UserDaoTests extends AbstractDaoTest{
	
	private UserDao dao;
	
	@Test
	@Transactional
	public void testCreateUser(){
		User test = new User();
		test.setUsername("name");
		test.setPassword("password");
		test.setEmail("email");
		long id = dao.createUser(test);
		test.setId(id);
		User saved = dao.retrieveUserById(id);
		assertTrue(test.equals(saved));
	}
	
	@Test
	@Transactional
	public void testRetrieveUserByUsername(){
		User test = dao.retrieveUserByUsername("username");
		assertNotNull(test);
		assertEquals("username", test.getUsername());
		assertEquals("test@email", test.getEmail());
		assertEquals(null,test.getDescription());
	}
	
	@Test
	@Transactional
	public void testSearchUsers(){
		List<User> test = dao.findUsers("");
		assertNotNull(test);
	}

	@Test
	@Transactional
	public void testRetrieveUserById(){
		User test = dao.retrieveUserById(1);
		assertNotNull(test);
		assertEquals("username", test.getUsername());
		assertEquals("test@email", test.getEmail());
		assertEquals(null,test.getDescription());
	}
	
	@Test
	@Transactional
	public void testUpdateUser(){
		User edit = new User();
		edit.setId(1);
		edit.setUsername("new username");
		edit.setEmail("test@email");
		edit.setDescription("new descritpion");
		dao.updateUser(edit);
		User test = dao.retrieveUserById(1);
		assertNotNull(test);
		assertEquals(edit.getUsername(), test.getUsername());
		assertNotNull(test.getDescription());
		assertEquals(edit.getDescription(), test.getDescription());
		
	}
	
	@Test(expected=MissingUserException.class)
	@Transactional
	public void testDeleteUser(){
		dao.deleteUser(1);
		User test = dao.retrieveUserById(1);
		assertNotNull("The object exists in the database after a delete",test);
	}
	
	@Test
	@Transactional
	public void testChangePassword(){
		dao.changePassword("username", "new password");
		User test = dao.retrieveUserByUsername("username");
		assertEquals("new password", test.getPassword());
	}
	
	@Autowired
	public void setUserDao(UserDao dao){
		this.dao = dao;
	}
	
	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DaoTestDataSet.xml")));
	}

}

package com.pollapp.test.dao;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.pollapp.dao.PollDao;
import com.pollapp.exception.MissingPollException;
import com.pollapp.model.Option;
import com.pollapp.model.Poll;
import com.pollapp.model.User;

public class PollDaoTests extends AbstractDaoTest {

	private PollDao dao;
	
	@Test
	@Transactional
	public void testCreatePoll(){
		Option[] options = new Option[]{new Option("opt1"),new Option("opt2")};
		long id = dao.createPoll(new Poll("name3",Arrays.asList(options),new User("username"),"description"));
		Poll test = dao.retrievePollById(id);
		assertNotNull(test);
		assertEquals("name3", test.getName());
		assertEquals(2, test.getOptions().size());
		assertEquals("opt1", test.getOptions().get(0).getOptionName());
		assertEquals("opt2", test.getOptions().get(1).getOptionName());
		assertEquals((long)1, test.getOwner().getId());
		assertEquals("username", test.getOwner().getUsername());
		assertEquals("description",test.getDescription());
		
	}
	
	@Test
	@Transactional
	public void testRetievePollById(){
		Poll test = dao.retrievePollById((long) 4);
		assertNotNull(test);
		assertEquals("name", test.getName());
	}
	
	@Test
	@Transactional
	public void testRetrieveLatest(){
		assertEquals(2, dao.retrieveRecentPolls().size());
	}
	
	@Test
	@Transactional
	public void testSearchPolls(){
		List<Poll> polls = dao.findPolls("name");
		assertNotNull(polls);
		assertEquals(2, polls.size());
	}
	
	
	
	@Test
	@Transactional
	public void testEditPoll(){
		Poll test = dao.retrievePollById(4);
		test.setName("new poll name");
		Option o = test.getOptions().get(0);
		o.setOptionName("edited option name");
		test.addOption(new Option("new option"));
		dao.updatePoll(test);
		test = dao.retrievePollById((long)4);
		assertEquals("new poll name", test.getName());
		assertEquals(3,test.getOptions().size());
		assertEquals("edited option name", test.getOptions()
				                            .get(0)
				                            .getOptionName());
		assertEquals("new option", test.getOptions()
				                            .get(2)
				                            .getOptionName());
	}

	@Test
	@Transactional
	public void testAddOption(){
		dao.addOption(new Option("opt1"), 4);
		Poll test= dao.retrievePollById(4);
		assertEquals("opt1", test.getOptions().get(0).getOptionName());
	}
	
	@Test
	@Transactional
	public void testEditOption(){
		Option o = new Option("new name");
		o.setId(1);
		dao.updateOption(o); 
		Poll test= dao.retrievePollById(5);
		assertEquals("new name", test.getOptions().get(0).getOptionName());
	}
	

	@Test(expected=MissingPollException.class)
	@Transactional
	public void testDeletePoll(){
		dao.deletePoll(4);
		dao.retrievePollById(4);
	}
	
	@Test
	@Transactional
	public void testGetUserPolls(){
		List<Poll> polls = dao.retrieveUserPolls(2);
		assertNotNull(polls);
		assertEquals(2,polls.size());
		Poll test= polls.get(0);
		assertEquals("name",test.getName());
		assertEquals(2,test.getOwner().getId());
	}
	
	@Test
	@Transactional
	public void testVote(){
		dao.vote(1,5);
		Poll test =dao.retrievePollById(5);
		assertEquals(1,test.getVotes());
		assertEquals(1,test.getOptions().get(0).getVoteCount());
	}

	
	@Autowired
	public void setDao(PollDao dao) {
		this.dao = dao;
	}

	@Override
	protected IDataSet getDataSet() throws MalformedURLException,
			DataSetException {
		return new FlatXmlDataSetBuilder().build(new File(defaultTestResourceFolder.concat("DaoTestDataSet.xml")));
	}

}

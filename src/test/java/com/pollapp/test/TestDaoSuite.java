package com.pollapp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.pollapp.test.dao.PollDaoTests;
import com.pollapp.test.dao.UserDaoTests;

@RunWith(Suite.class)
@Suite.SuiteClasses({PollDaoTests.class,
			         UserDaoTests.class})
public class TestDaoSuite {

}

package com.pollapp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

import com.pollapp.test.service.PollServiceSecurityTests;
import com.pollapp.test.service.UserServiceSecurityTests;


@RunWith(Suite.class)
@Suite.SuiteClasses({PollServiceSecurityTests.class,
	                 UserServiceSecurityTests.class})
public class TestServiceSecuritySuite {

}

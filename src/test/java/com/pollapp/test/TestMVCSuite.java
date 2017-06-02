package com.pollapp.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import com.pollapp.test.mvc.PollControllerUnitTests;
import com.pollapp.test.mvc.UserControllerUnitTests;

@RunWith(Suite.class)
@SuiteClasses({PollControllerUnitTests.class,
               UserControllerUnitTests.class})
public class TestMVCSuite {

}

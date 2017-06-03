package com.pollapp.test.web;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
    CreateUserPageTests.class,
    CreatePollPageTests.class,
    EditPollPageTests.class,
    EditUserPageTests.class
})
public class ITTestSuite {

}

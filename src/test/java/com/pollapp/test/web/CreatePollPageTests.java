package com.pollapp.test.web;

import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Test;

import com.pollapp.test.web.page.LoginPage;
import com.pollapp.test.web.page.PollFormPage;
import com.pollapp.test.web.page.PollPage;

import static org.junit.Assert.*;


public class CreatePollPageTests extends AbstractWebPageTest {
    
    @Page
    private PollFormPage formPage;
    
    @Page
    private PollPage pollPage;
    
    @Test
    public void testCreatePoll(@InitialPage LoginPage login){
        login.loginSpecific("username3", "password");
        loadPage("#/polls/new");
        
        formPage.submitForm("A new poll", "A description", "option1", "option2");
        
        assertEquals("Not on new poll page","A new poll",pollPage.getPollName());
        assertEquals("Incorrect description saved","A description",pollPage.getDescription());
        assertEquals("Incorrect first option","option1",pollPage.getOption1());
        assertEquals("Incorrect second option","option2",pollPage.getOption2());
        
    }

    @Test
    public void testCreatePollInputMessage(@InitialPage LoginPage login){
        login.loginSpecific("username3", "password");
        loadPage("#/polls/new");
        
        formPage.submitFormInput("", "A description", "option1", "option2");
        assertFalse("No title error was displayed", formPage.getTitleError().getText().isEmpty());
    }    

}

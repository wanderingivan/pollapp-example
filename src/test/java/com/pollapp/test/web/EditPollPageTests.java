package com.pollapp.test.web;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Test;

import com.pollapp.test.web.page.ErrorPage;
import com.pollapp.test.web.page.LoginPage;
import com.pollapp.test.web.page.PollFormPage;
import com.pollapp.test.web.page.PollPage;

public class EditPollPageTests extends AbstractWebPageTest {

    @Page
    private PollFormPage formPage;
    
    @Page
    private ErrorPage ePage;
    
    @Page
    private PollPage pollPage;
    
    @Test
    public void testEditPoll(@InitialPage LoginPage login){
        login.loginSpecific("username1", "password");
        loadPage("#/polls/1/edit");

        formPage.submitForm("Edited title", "New description", "new option1", "new option2");
        
        assertEquals("Not on new poll page","Edited title",pollPage.getPollName());
        assertEquals("Incorrect description saved","New description",pollPage.getDescription());
        assertEquals("Incorrect first option","new option1",pollPage.getOption1());
        assertEquals("Incorrect second option","new option2",pollPage.getOption2());
    }
    
    @Test
    public void testEditPollInputMessage(@InitialPage LoginPage login){
        login.loginSpecific("username1", "password");
        loadPage("#/polls/1/edit");
        
        formPage.submitFormInput("", "A description", "option1", "option2");
        assertFalse("No title error was displayed", formPage.getTitleError().getText().isEmpty());
    }   
    
    @Test
    public void testEditPollAccessDenied(@InitialPage LoginPage login){
        login.loginSpecific("username1", "password");
        loadPage("#/polls/11/edit");
        formPage.submitForm("Edited title", "New description", "new option1", "new option2");
        assertTrue("Not on error page",ePage.assertOnErrorPage());
    }
    
}

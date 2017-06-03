package com.pollapp.test.web;

import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Test;

import com.pollapp.test.web.page.CreateUserPage;
import com.pollapp.test.web.page.ErrorPage;
import com.pollapp.test.web.page.LoginPage;
import com.pollapp.test.web.page.UserPage;

import static org.junit.Assert.*;

public class CreateUserPageTests extends AbstractWebPageTest {

    
    @Page
    private UserPage uPage;
    
    @Page
    private CreateUserPage cPage;
    
    @Page
    private ErrorPage ePage;
    
    @Test
    public void testCreateUser(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("#/user/new");

        cPage.createUser("username6", "password", "email@email6.com", "description");

        assertEquals("Not on new user page","username6's Profile",uPage.getUsername());
        assertEquals("Description is missing","description",uPage.getDescription());
    }
   
    @Test
    public void testCreateUserInputErrors(@InitialPage LoginPage login){
        login.logoutIfAuthenticated();
        loadPage("#/user/new");

        cPage.createUserInput("", "", "email@emai6.com", "description");
        
        assertFalse("Username Error is missing", cPage.getUsernameError().getText().isEmpty());
        assertFalse("Password Error is missing", cPage.getPasswordError().getText().isEmpty());
        
    }
    
   @Test
    public void testCreateUserAccessDeniedError(@InitialPage LoginPage login){
        login.loginIfNotAuthenticated("username3", "password");
        loadPage("#/user/new");

        cPage.createUser("username4", "password", "email@email55.com", "description");
        assertTrue("Not on error page !",ePage.assertOnErrorPage());
    }
    
}

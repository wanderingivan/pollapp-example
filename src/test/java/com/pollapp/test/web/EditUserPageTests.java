package com.pollapp.test.web;

import static org.junit.Assert.*;


import org.jboss.arquillian.graphene.page.InitialPage;
import org.jboss.arquillian.graphene.page.Page;
import org.junit.Test;

import com.pollapp.test.web.page.EditUserPage;
import com.pollapp.test.web.page.ErrorPage;
import com.pollapp.test.web.page.LoginPage;
import com.pollapp.test.web.page.UserPage;


public class EditUserPageTests extends AbstractWebPageTests {

    @Page
    private EditUserPage ePage;
    
    @Page
    private UserPage uPage;
    
    @Page
    private ErrorPage errorPage;
    
    @Test
    public void testEditUser(@InitialPage LoginPage login){
        login.loginSpecific("username3", "password");
        
        loadPage("#/user/3/edit");
        
        ePage.editUser("someuser", "email@email33.com", "new description");

        assertEquals("Not on edited user page","someuser's Profile",uPage.getUsername());
        assertEquals("Description is missing","new description",uPage.getDescription()); 
    }
    
    @Test
    public void testEditUserInputMessages(@InitialPage LoginPage login){
        login.loginSpecific("username3", "password");
        
        loadPage("#/user/3/edit");        
        
        ePage.editUserInput("", "email", "description");
        
        assertFalse("Username Error is missing", ePage.getUsernameError().getText().isEmpty());
        assertFalse("Email Error is missing",ePage.getEmailError().getText().isEmpty());
    }
    
    @Test
    public void testEditUserAccessDenied(@InitialPage LoginPage login){
        login.loginSpecific("username2", "password");
        
        loadPage("#/user/3/edit");
        
        ePage.editUser("someuser", "email@email33.com", "new description");
        try{
            Thread.sleep(10000);
        }catch(Exception e){}
        
        assertTrue("Not on error page !",errorPage.assertOnErrorPage());
    }
    
}

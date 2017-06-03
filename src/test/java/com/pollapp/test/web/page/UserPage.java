package com.pollapp.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class UserPage {

    @FindBy(id="username")
    private WebElement username;
    
    @FindBy(id="description")
    private WebElement description;
    
    public String getUsername(){
        Graphene.waitGui().until()
        .element(username)
        .is()
        .visible();        
        return username.getText().trim();
    }
    
    public String getDescription(){
        return description.getText().trim();
    }    
    
}

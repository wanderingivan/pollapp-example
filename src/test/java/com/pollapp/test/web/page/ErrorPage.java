package com.pollapp.test.web.page;

import org.jboss.arquillian.graphene.GrapheneElement;
import org.openqa.selenium.support.FindBy;

public class ErrorPage {

    @FindBy(id="errorMessage")
    GrapheneElement errorMessage;
    
    public boolean assertOnErrorPage(){
        return errorMessage.isPresent();
    }
    
    public String getErrorMessage(){
        return errorMessage.getText().trim();
    }
}

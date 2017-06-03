package com.pollapp.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PollFormPage {
    
    @FindBy(id="title")
    private WebElement title;
    
    @FindBy(id="description")
    private WebElement description;
    
    @FindBy(id="optionInput0")
    private WebElement option1;
    
    @FindBy(id="optionInput1")
    private WebElement option2;
    
    @FindBy(id="pollSubmit")
    private WebElement pollSubmit;
    
    @FindBy(id="titleError")
    private WebElement titleError;
    
    public void submitForm(String pollName,String pollDescription,String option1,String option2){
        fillForm(pollName,pollDescription,option1,option2);
        
        Graphene.guardAjax(pollSubmit)
                .click();
    }
    
    public void submitFormInput(String pollName,String pollDescription,String option1,String option2){
        fillForm(pollName,pollDescription,option1,option2);
        
        Graphene.guardNoRequest(pollSubmit)
                .click();
    }

    public WebElement getTitleError() {
        return titleError;
    }
    
 private void fillForm(String pollName,String pollDescription,String option1,String option2){
 
        Graphene.waitGui().until()
                          .element(title)
                          .is()
                          .visible();
     
        this.title.clear();
        this.title.sendKeys(pollName);
        
        this.description.clear();
        this.description.sendKeys(pollDescription);
        
        this.option1.clear();
        this.option1.sendKeys(option1);
        
        this.option2.clear();
        this.option2.sendKeys(option2);        
    }
    
}

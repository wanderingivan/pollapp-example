package com.pollapp.test.web.page;

import java.util.concurrent.TimeUnit;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CreateUserPage {

    @FindBy(id="username")
    private WebElement usernameInput;

    @FindBy(id="password")
    private WebElement passwordInput;
    
    @FindBy(id="mail")
    private WebElement emailInput;
        
    @FindBy(id="description")
    private WebElement descriptionInput;

    @FindBy( id="createSubmit")
    private WebElement createSubmit;
    
    @FindBy(id="usernameError")
    private WebElement usernameError;
    
    @FindBy(id="passwordError")
    private WebElement passwordError;
    
    @FindBy(id="emailError")
    private WebElement emailError;
    
    @FindBy(id="descriptionError")
    private WebElement descriptionError;
    
    public void createUser(String username,String password,String email,String description){
        fillForm(username,password,email,description);
        
        Graphene.guardAjax(createSubmit)
                .click();
        try{
            TimeUnit.SECONDS.sleep(2);
        }catch(Exception e){}
    }
    
    public void createUserInput(String username,String password,String email,String description){
        fillForm(username,password,email,description);
        
        Graphene.guardNoRequest(createSubmit)
                .click();
    }
    
    public WebElement getUsernameError(){
        return usernameError;
    }
    
    public WebElement getPasswordError() {
        return passwordError;
    }

    public WebElement getEmailError() {
        return emailError;
    }

    public WebElement getDescriptionError() {
        return descriptionError;
    }
    
    private void fillForm(String username,String password,String email,String description){
        Graphene.waitGui().until()
                          .element(usernameInput)
                          .is()
                          .visible();
        
        
        this.usernameInput.clear();
        this.usernameInput.sendKeys(username);

        this.passwordInput.clear();
        this.passwordInput.sendKeys(password);

        this.emailInput.clear();
        this.emailInput.sendKeys(email);

        this.descriptionInput.clear();
        this.descriptionInput.sendKeys(description);        
    }    
    
}

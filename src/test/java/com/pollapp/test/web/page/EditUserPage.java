package com.pollapp.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class EditUserPage {

    @FindBy(id="username")
    private WebElement usernameInput;

    @FindBy(id="mail")
    private WebElement emailInput;
        
    @FindBy(id="description")
    private WebElement descriptionInput;

    @FindBy( id="editSubmit")
    private WebElement editSubmit;
    
    @FindBy(id="usernameError")
    private WebElement usernameError;
    
    @FindBy(id="emailError")
    private WebElement emailError;
    
    @FindBy(id="descriptionError")
    private WebElement descriptionError;
    
    public void editUser(String username,String email,String description){
        
        fillForm(username, email, description);
        
        Graphene.guardAjax(editSubmit)
                .click();
    }
    
    public void editUserInput(String username,String email,String description){
        
        fillForm(username, email, description);
        
        Graphene.guardNoRequest(editSubmit)
                .click();
    }    
    
    public WebElement getUsernameError() {
        return usernameError;
    }

    public WebElement getEmailError() {
        return emailError;
    }

    public WebElement getDescriptionError() {
        return descriptionError;
    }    
    
    private void fillForm(String username,String email,String description){
        Graphene.waitGui().until()
                          .element(usernameInput)
                          .is()
                          .visible();
        
        this.usernameInput.clear();
        this.usernameInput.sendKeys(username);

        this.emailInput.clear();
        this.emailInput.sendKeys(email);

        this.descriptionInput.clear();
        this.descriptionInput.sendKeys(description);        
    }
}

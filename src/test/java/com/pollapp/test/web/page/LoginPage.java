package com.pollapp.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.jboss.arquillian.graphene.GrapheneElement;
import org.jboss.arquillian.graphene.page.Location;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

@Location("#/login")
public class LoginPage {

    @FindBy(id="username")
    private WebElement usernameInput;
    
    @FindBy(id="password")
    private WebElement passwordInput;
    
    @FindBy(id="loginSubmit")
    private WebElement loginSubmit;
    
    @FindBy(id="userMenu")
    private GrapheneElement userDropdown;
    
    @FindBy(id="logoutSubmit")
    private GrapheneElement logout;
    
    @FindBy(id="loginLink")
    private GrapheneElement loginLink;
    
    public void login(String username, String password){
        
        this.usernameInput.clear();
        this.passwordInput.clear();
        this.usernameInput.sendKeys(username);
        this.passwordInput.sendKeys(password);
        
        Graphene.guardAjax(loginSubmit)
                .click();
    }
    
    public void loginIfNotAuthenticated(String username, String password){
        if(loginLink.isPresent()){
            login(username,password);
        }
    }
    
    public void logoutIfAuthenticated(){
        if(userDropdown.isDisplayed()){
            this.userDropdown.click();
            Graphene.waitGui()
                    .until()
                    .element(logout)
                    .is()
                    .visible();
            Graphene.guardAjax(logout).click();
        }
        
    }
    
    public void loginSpecific(String username, String password){
        logoutIfAuthenticated();
        this.loginLink.click();
        Graphene.waitGui()
                .until()
                .element(usernameInput)
                .is()
                .visible();
        login(username,password);
    }
    
}

package com.pollapp.test.web.page;

import org.jboss.arquillian.graphene.Graphene;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PollPage {
    
    @FindBy(id="pollname")
    private WebElement pollname;
    
    @FindBy(id="description")
    private WebElement description;
    
    @FindBy(id="author")
    private WebElement author;
    
    @FindBy(id="opt0")
    private WebElement option1;
    
    @FindBy(id="opt1")
    private WebElement option2;
    
    public String getPollName(){
        Graphene.waitGui().until()
                .element(pollname)
                .is()
                .visible();        
        return pollname.getText().trim(); 
    }
    
    public String getDescription(){
        return description.getText().trim();
    }

    public String getAuthor(){
        return author.getText().trim();
    }
    
    public String getOption1(){
        return option1.getText().trim();
    }
    public String getOption2(){
        return option2.getText().trim();
    }

}

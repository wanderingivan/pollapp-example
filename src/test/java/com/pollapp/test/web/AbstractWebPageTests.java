package com.pollapp.test.web;

import java.net.URL;

import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.drone.api.annotation.Drone;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.junit.runner.RunWith;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;

/**
 * Abstract class defining common 
 * dependencies and methods for loading
 * pages
 */
@RunAsClient
@RunWith(Arquillian.class)
public class AbstractWebPageTests {

    @Drone
    protected WebDriver driver;
    
    @ArquillianResource
    protected URL deploymentUrl;

    /**
     * Appends the specified url to the deploymentUrl 
     * and sets the window to 1920x1080 before attempting to load
     * @param url the url to load
     */   
    protected void loadPage(String url){
        driver.manage().window().setSize(new Dimension(1920,1080));
        driver.get(deploymentUrl.toString()
                                .trim()
                                .concat(url));
    }
}

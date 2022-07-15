package com.project.pageObjects;

import com.project.stepDefinitions.Hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class BasePage {
    protected WebDriver driver;
    public BasePage(WebDriver driver) {
        this.driver = Hooks.getDriver();
        PageFactory.initElements(driver,this);
    }
}

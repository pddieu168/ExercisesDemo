package utils.selenium;

import global.Const;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import utils.common.CommonUtil;
import utils.common.LogUtil;

import static utils.TestBase.driver;

public class ElementAction {
    public static void click(WebElement ele, String... eleName) {
        try {
            CommonUtil.waitForElement(ele, Const.EXCEPTION_CONDITIONS.CLICKABLE);
            ele.click();
        }catch(Exception exp){
            CommonUtil.waitForElement(ele, Const.EXCEPTION_CONDITIONS.CLICKABLE);
            JavascriptExecutor executor = (JavascriptExecutor) driver;
            executor.executeScript("arguments[0].click();", ele);
        }
        if(eleName.length>0){
            if (!eleName[0].isEmpty()) {
                LogUtil.info(String.format("Click %s", eleName[0].toString()));
            }
        }

    }

    public static void typeElement(WebElement ele, String value, String... eleName) {
        CommonUtil.waitForElement(ele,Const.EXCEPTION_CONDITIONS.VISIBILITIES);
        ele.click();
             //ele.clear();
             JavascriptExecutor js = (JavascriptExecutor)driver;
             js.executeScript("arguments[0].value = '';", ele);        
             ele.sendKeys(value);
        String log = String.format("Type '%s'", value);
        if (!eleName[0].isEmpty()) {
            log += String.format(" into edit box '%s'", eleName[0]);
        }
        LogUtil.info(log);
    }

    public static void clickElementById(String id, String eleName) {
        WebElement ele = ElementFinder.findElementById(id);
        click(ele, eleName);
    }

    public static void clickElementByClassName(String className, String eleName) {
        WebElement ele = ElementFinder.findElementByClassName(className);
        click(ele, eleName);
    }

    public static void clickElementByXpath(String xpath, String eleName) {
        WebElement ele = ElementFinder.findElementByXpath(xpath);
        CommonUtil.waitForElement(ele,Const.EXCEPTION_CONDITIONS.CLICKABLE);
        click(ele, eleName);
    }

    public static void typeElementById(String id, String value, String... eleName) {
        WebElement element = ElementFinder.findElementById(id);
        typeElement(element, value, eleName);
    }

    public static void typeElementByClassName(String className, String value, String eleName) {
        WebElement element = ElementFinder.findElementByClassName(className);
        typeElement(element, value, eleName);
    }

    public static void typeElementByXpath(String xpath, String value, String eleName) {
        WebElement element = ElementFinder.findElementByXpath(xpath);
        typeElement(element, value, eleName);
    }

    public static String getTextElementById(String id){
        WebElement element = ElementFinder.findElementById(id);
        return element.getText();
    }

    public static String getTextElementByClassName(String className){
        WebElement element = ElementFinder.findElementByClassName(className);
        return element.getText();
    }

    public static String getTextElementByXpath(String xpath){
        WebElement element = ElementFinder.findElementByXpath(xpath);
        return element.getText();
    }
    public static void clearWebField(WebElement element){
        while(!element.getAttribute("value").equals("")){
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public static void scrollToTopOfPageByJS() {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("window.scrollTo(0, -document.body.scrollHeight)");
    }
    public static void scrollToElementByJS(WebElement element) {
        JavascriptExecutor executor = (JavascriptExecutor) driver;
        executor.executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public static void typeElementCustomClear(WebElement ele, String value, String... eleName) {
        ele.sendKeys(Keys.CONTROL,"a");
        CommonUtil.think(1);
        ele.sendKeys(Keys.BACK_SPACE);
        CommonUtil.think(1);
        typeElement(ele,value,eleName);
    }
}

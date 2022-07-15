package utils.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utils.TestBase;

import java.util.List;

public class ElementFinder extends TestBase {

    public static WebElement findElementById(String id) {
        return driver.findElement(By.id(id));
    }

    public static WebElement findElementByName(String name) {
        return driver.findElement(By.name(name));
    }

    public static WebElement findElementByClassName(String className) {
        return driver.findElement(By.className(className));
    }

    public static WebElement findElementByCss(String cssSelector) {
        return driver.findElement(By.cssSelector(cssSelector));
    }

    public static WebElement findElementByXpath(String xpath) {
        return driver.findElement(By.xpath(xpath));
    }

    public static List<WebElement> findElementsById(String id) {
        return driver.findElements(By.id(id));
    }

    public static List<WebElement> findElementsByName(String name) {
        return driver.findElements(By.name(name));
    }

    public static List<WebElement> findElementsByClassName(String className) {
        return driver.findElements(By.className(className));
    }

    public static List<WebElement> findElementsByCss(String cssSelector) {
        return driver.findElements(By.cssSelector(cssSelector));
    }

    public static List<WebElement> findElementsByXpath(String xpath) {
        return driver.findElements(By.xpath(xpath));
    }

    public static boolean isDisplayedByFind(String xpath) {
        return driver.findElements(By.xpath(xpath)).size() > 0;
    }
}

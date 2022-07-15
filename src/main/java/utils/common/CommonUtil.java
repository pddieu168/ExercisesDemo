package utils.common;

import com.project.stepDefinitions.Hooks;
import global.Const;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.selenium.ElementAction;
import utils.selenium.ElementFinder;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import static com.project.stepDefinitions.Hooks.getDriver;

public class CommonUtil {

    public static void navigateToURL(String url) {
        getDriver().navigate().to(url);
    }

    public static void think(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static String getCurrentDateTime(String format) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    public static String getFutureDateTime(int numDay, String format) {
        ZonedDateTime todayWithDefaultTimeZone = ZonedDateTime.now().plusDays(numDay);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern(format);
        return todayWithDefaultTimeZone.format(dtf);
    }

    public static void scrollToElementbyText(String text) {
        WebElement webElement = getDriver().findElement(By.xpath("//*[text()='" + text + "']"));
        scrollToElementByPosition(webElement, true);
    }

    /*
    Example (auto, start ,nearest) : element as top
    (auto, center ,nearest) : element scroll as vertical center
    (auto, center ,end) : element scroll as vertical center, horizontal end
    */
    public static void scrollToElementbyText(String text, String transition, String vertical, String horizontal) {
        WebElement webElement = getDriver().findElement(By.xpath("//*[text()='" + text + "']"));
        scrollToElementByPosition(webElement, transition, vertical, horizontal);
    }

    /*
    alignToTop - a Boolean value:
    if true, the top of the element will be aligned to the top of the visible area of the scrollable ancestor. Corresponds to scrollIntoViewOptions: {block: "start", inline: "nearest"}. This is the default value.
    if false, the bottom of the element will be aligned to the bottom of the visible area of the scrollable ancestor. Corresponds to scrollIntoViewOptions: {block: "end", inline: "nearest"}.
    */
    public static void scrollToElementByPosition(WebElement element, boolean position) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format("arguments[0].scrollIntoView(%s);", position), element);
    }

    /*
    Author:Phu Dieu
    behavior - Defines the transition animation - Value: auto,smooth. Defaults to auto.
    block - Defines vertical alignment - Value: start, center, end, or nearest. Defaults to start.
    inline - Defines horizontal alignment - Value: start, center, end, or nearest. Defaults to nearest.
     */
    public static void scrollToElementByPosition(WebElement element, String transition, String vertical, String horizontal) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript(String.format("arguments[0].scrollIntoView({behavior: \"%s\", block: \"%s\", inline: \"%s\"});", transition, vertical, horizontal), element);
    }

    public static void scrollByElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) getDriver();
        js.executeScript("arguments[0].scrollIntoView();", element);
    }

    /* Author: Phu Dieu
    Wait until element <Condition> before throwing an exception using fluent wait
     */
    public static void waitForElement(WebElement element, Const.EXCEPTION_CONDITIONS type, int timeout, int frequency) {
        Function func = null;
        switch (type) {
            case VISIBILITIES:
                func = ExpectedConditions.visibilityOf(element);
                break;
            case CLICKABLE:
                func = ExpectedConditions.elementToBeClickable(element);
                break;
            case INVISIBILITIES:
                func = ExpectedConditions.invisibilityOf(element);
                break;
            default:
                break;
        }
        waitForElementWithCondition(timeout, frequency, func);
    }

    public static void waitForElement(WebElement element, Const.EXCEPTION_CONDITIONS type) {
        Function func = null;
        switch (type) {
            case CLICKABLE:
                func = ExpectedConditions.visibilityOf(element);
                break;
            case VISIBILITIES:
                func = ExpectedConditions.elementToBeClickable(element);
                break;
            case INVISIBILITIES:
                func = ExpectedConditions.invisibilityOf(element);
                break;
            default:
                break;
        }
        waitForElementWithCondition(Const.TIMEOUT, Const.POLLING_EVERY, func);
    }

    public static void waitForElement(String xpath, Const.EXCEPTION_CONDITIONS type) {
        Function func = null;
        switch (type) {
            case VISIBILITIES:
                func = ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath));
                break;
            case CLICKABLE:
                func = ExpectedConditions.elementToBeClickable(By.xpath(xpath));
                break;
            case INVISIBILITIES:
                func = ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath));
                break;
            default:
                break;
        }
        waitForElementWithCondition(Const.TIMEOUT, Const.POLLING_EVERY, func);
    }

    public static void waitForElementWithCondition(int timeout, int frequency, Function func) {
        FluentWait wait = new FluentWait(getDriver());
        wait.withTimeout(Duration.ofSeconds(timeout));
        wait.pollingEvery(Duration.ofSeconds(frequency));
        wait.ignoring(NoSuchElementException.class);
        wait.until(func);
    }

    public static void waitForPageLoad() {
        ExpectedCondition<Boolean> expectation = new
                ExpectedCondition<Boolean>() {
                    public Boolean apply(WebDriver driver) {
                        return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
                    }
                };
        try {
            waitForElementWithCondition(Const.TIMEOUT, Const.POLLING_EVERY, expectation);
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for Page Load Request to complete.");
        }
    }

    private static Logger logger = LogManager.getLogger();
    static boolean item = false;

    public static void selectElementByValue(String xpath) {
        try {
            waitForElement(xpath, Const.EXCEPTION_CONDITIONS.CLICKABLE);
            WebElement element = ElementFinder.findElementByXpath(xpath);
            scrollToElementByPosition(element, "auto", "center", "nearest");
            think(3);
            logger.info("Found element: " + xpath);
            item = true;
            element.click();
        } catch (Exception ex) {
            item = false;
            logger.info("Couldn't find this element");
        }
    }

    public static void switchTONewestWindow() {
        for (String handle : getDriver().getWindowHandles()) {
            getDriver().switchTo().window(handle);
        }
    }

    public static boolean isElementPresentByXpath(String path) {
        try {
            getDriver().findElement(By.xpath(path));
            return true;
        } catch (org.openqa.selenium.NoSuchElementException e) {
            return false;
        }
    }

    public static void RefreshPage() {
        getDriver().navigate().refresh();
    }

    public static void waitForElementToAppear(String xpath) {
        WebDriverWait wait = new WebDriverWait(getDriver(), 40);
        wait.pollingEvery(Duration.ofMillis(100));
        System.out.println("Waiting for by to Appear : " + xpath);
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
    }

    public static boolean waitForElementToAppearAndGetStatus(String xpath) {
        boolean status;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 40);
            wait.pollingEvery(Duration.ofMillis(100));
            System.out.println("Waiting for by to Appear : " + xpath);
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
            status = element.isDisplayed();
        } catch (Exception ex) {
            status = false;
            ex.printStackTrace();
        }
        return status;
    }

    public static boolean waitForElementToDisappearAndGetStatus(String xpath) {
        boolean status;
        try {
            WebDriverWait wait = new WebDriverWait(getDriver(), 40);
            wait.pollingEvery(Duration.ofMillis(100));
            System.out.println("Waiting for by to Disappear : " + xpath);
            status = wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath(xpath)));
        } catch (Exception ex) {
            status = false;
            ex.printStackTrace();
        }
        return status;
    }

    public static String getDerivedPath(String path) {
        return System.getProperty("user.home") + File.separator + path;
    }

    public static boolean waitForFileDownload(String path, int timeout) {
        int count = 0;
        boolean found = false;
        do {
            if (count > timeout) {
                break;
            }
            think(1);
            found = (new File(path)).exists();
            count++;

        } while (!found);

        return found;
    }

    public static void enterFilePathInWindowDialog(String path) {
        StringSelection s = new StringSelection(path);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);
        Robot robot = null;
        //making init for default os as windows
        int EnterKey = KeyEvent.VK_ENTER;
        int ControlKey = KeyEvent.VK_CONTROL;
        int Vkey = KeyEvent.VK_V;

        if (Const.OS.equalsIgnoreCase("ios")) {
            ControlKey = KeyEvent.VK_META;
        } else if (!Const.OS.equalsIgnoreCase("windows")) {
            Assert.fail("Invalid OS name is given as input in Const class: " + Const.OS);
        }
        try {
            robot = new Robot();
            CommonUtil.think(2);
            robot.keyPress(EnterKey);
            robot.keyPress(EnterKey);
            robot.keyRelease(EnterKey);
            CommonUtil.think(1);
            robot.keyPress(ControlKey);
            robot.keyPress(Vkey);
            robot.keyRelease(ControlKey);
            CommonUtil.think(3);
            robot.keyPress(EnterKey);
            CommonUtil.think(3);
        } catch (AWTException e) {
            e.printStackTrace();
        }
        Hooks.getDriver().switchTo().activeElement();
    }

    public static void setImplicitWait(int seconds) {
        Hooks.getDriver().manage().timeouts().implicitlyWait(seconds, TimeUnit.SECONDS);
    }

    public static void resetImplicitWait() {
        Hooks.getDriver().manage().timeouts().implicitlyWait(Const.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
    }

    public static void clearWebField(WebElement element) {
        Actions action = new Actions(getDriver());
        action.doubleClick(element).perform();
        while (!element.getAttribute("value").equals("")) {
            element.sendKeys(Keys.BACK_SPACE);
        }
    }

    public static String getNewWindowHandle(String parent) {
        String child = "";
        Set<String> handles = Hooks.getDriver().getWindowHandles();
        for (String handle :
                handles) {
            if (!parent.contentEquals(handle)) {
                child = handle;
            }
        }
        return child;
    }
}

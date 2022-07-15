package utils.selenium;

import global.Const;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.github.bonigarcia.wdm.config.DriverManagerType;
import io.github.bonigarcia.wdm.config.WebDriverManagerException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;
import org.openqa.selenium.safari.SafariOptions;

public class DriverFactory {
    private static WebDriver currentDriver = null;

    //Get current web driver running
    public static WebDriver getCurrentDriver(String browserName) {
        if (currentDriver == null) {
            switch (browserName) {
                case Const.BROWSER_CHROME:
                    WebDriverManager.getInstance(DriverManagerType.CHROME).setup();
                    currentDriver = new ChromeDriver();
                    break;
                case Const.BROWSER_FIREFOX:
                    WebDriverManager.getInstance(DriverManagerType.FIREFOX).setup();
                    currentDriver = new FirefoxDriver();
                    break;
                case Const.BROWSER_IE:
                    WebDriverManager.getInstance(DriverManagerType.IEXPLORER).setup();
                    currentDriver = new InternetExplorerDriver();
                    break;
                case Const.BROWSER_SAFARI:
                    WebDriverManager.getInstance(DriverManagerType.SAFARI).setup();
                    DesiredCapabilities desiredCapabilities = DesiredCapabilities.safari();
                    desiredCapabilities = DesiredCapabilities.safari();
                    SafariOptions safariOptions = new SafariOptions();
                    safariOptions.setUseTechnologyPreview(true);
                    desiredCapabilities.setCapability("safariOptions", safariOptions);
                    desiredCapabilities.setCapability("safari.cleanSession", true);
                    desiredCapabilities.acceptInsecureCerts();
                    currentDriver = new SafariDriver(desiredCapabilities);
                    break;
                case Const.BROWSER_EDGE:
                    WebDriverManager.getInstance(DriverManagerType.EDGE).setup();
                    currentDriver = new EdgeDriver();
                    break;
                default:
                    throw new WebDriverManagerException("Invalid browser: " + browserName);
            }
        }
        return currentDriver;
    }

    // Close current browser
    public static void closeCurrentBrowser() {
        if (currentDriver != null) {
            currentDriver.quit();
        }
        currentDriver = null;
    }
}
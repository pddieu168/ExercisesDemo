package com.project.stepDefinitions;

import com.cucumber.listener.Reporter;
import global.Const;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import utils.common.CommonUtil;
import utils.common.ConfigReader;
import utils.common.LogUtil;
import utils.selenium.DriverFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Hooks {
    private static WebDriver driver;

    private static String CONFIG_FILE = System.getProperty("user.dir") + "/config/config.properties";
    private static String CONFIG_FILE_WINDOWS = System.getProperty("user.dir") + "\\config\\config.properties";
    private static String TESTNG_FILE = System.getProperty("user.dir") + "/src/test/resources/testng.xml";
    private static String TESTNG_FILE_WINDOWS = System.getProperty("user.dir") + "\\src\\test\\resources\\testng.xml";

    public static WebDriver getDriver() {
        return driver;
    }

    public static ConfigReader getConfigFile() {
        ConfigReader configReader = new ConfigReader();
        if (Const.OS.equals("windows")) {
            configReader.loadFile(CONFIG_FILE_WINDOWS);
        } else {
            configReader.loadFile(CONFIG_FILE);
        }
        return configReader;
    }

    @Before(order = 1)
    public void BeforeScenario() throws ParserConfigurationException, IOException, SAXException {
        String browserType = "";
        if (Const.TESTNG) {
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(new File(TESTNG_FILE));
            NodeList nodeList = document.getElementsByTagName("parameter");
            LogUtil.info(nodeList.item(0).getAttributes().getNamedItem("value").getNodeValue());
            browserType = nodeList.item(0).getAttributes().getNamedItem("value").getNodeValue();
        } else {
            browserType = getConfigFile().get("browser");
        }
        try {
            if (Const.UI) {
                LogUtil.info("Driver: " + browserType);
                driver = DriverFactory.getCurrentDriver(browserType);
                driver.manage().window().maximize();
                driver.manage().timeouts().implicitlyWait(Const.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS);
            }
        } catch (Exception ex) {
            LogUtil.error(ex.getMessage());
        }
    }

    @After(order = 1)
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            String screenshotName = scenario.getName().replaceAll(" ", "_");
            String date = CommonUtil.getCurrentDateTime("yyyyMMdd");
            String dateTime = CommonUtil.getCurrentDateTime("yyyyMMdd_HHmmss");
            try {
                File sourcePath = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
                File destinationPath = new File(System.getProperty("user.dir") + "/target/cucumber-reports/screenshots/" + date + "/" + dateTime + "_" + screenshotName + ".png");
                FileUtils.copyFile(sourcePath, destinationPath);
                Reporter.addScreenCaptureFromPath(destinationPath.toString());
            } catch (IOException e) {
            }
        }
    }

    @After(order = 0)
    public void AfterSteps(Scenario scenario) {
        DriverFactory.closeCurrentBrowser();
    }
}

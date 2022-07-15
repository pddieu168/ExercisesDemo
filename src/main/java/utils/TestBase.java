package utils;

import org.openqa.selenium.WebDriver;
import com.project.stepDefinitions.Hooks;

public class TestBase {
    public static WebDriver driver;
    public TestBase() {
        this.driver = Hooks.getDriver();
    }
}

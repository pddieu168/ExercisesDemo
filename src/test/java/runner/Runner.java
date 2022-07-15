package runner;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

@CucumberOptions(
        features = "src/test/java/feature/",

        tags = {"@Test"},
        glue = {"com.project.stepDefinitions"},
        monochrome = true,
        plugin = {"pretty", "json:target/cucumber-reports/Cucumber.json",
                "junit:target/cucumber-reports/Cucumber.xml",
                "html:target/cucumber-reports"}
)

public class Runner extends AbstractTestNGCucumberTests {
    @BeforeSuite()
    public void beforeSuite() {
    }

    @AfterSuite()
    public void AfterSuite() {
    }
}


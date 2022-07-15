package com.project.stepDefinitions;

import com.project.pageObjects.RegionHeaderPage;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import utils.TestBase;
import utils.common.ConfigReader;

public class RegionHeaderSteps extends TestBase {
    RegionHeaderPage regionHeaderPage = new RegionHeaderPage(driver);

    @When("^User visit Weather page$")
    public void userVisitWeatherPage() {
        driver.get(ConfigReader.get("base_url"));
    }

    @Then("^Verify that the search input field is displayed$")
    public void assertSearchFieldIsDisplayedOrNot() {
        Assert.assertTrue(regionHeaderPage.isLocationSearchInputDisplayed());
    }

    @When("^Select (.*) Option From Result$")
    public void selectSingaporeOption(String option) {
        regionHeaderPage.clickSearchOption(option);
    }

    @When("^Input value (.*) in search field$")
    public void inputValueToSearchField(String value) {
        regionHeaderPage.inputSearch(value);
    }

    @When("^Click Local Suite Header (.*)$")
    public void clickLocalSuiteHeader(String header) {
        regionHeaderPage.clickLocalSuiteHeader(header);
    }

    @When("^Retrieve (.*) days weather for Day and Night$")
    public void retrieve10DaysWeather(int days) {
        regionHeaderPage.getTemperatureAndHumidityOf10DaysWeather(days);
    }

}

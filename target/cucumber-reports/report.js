$(document).ready(function() {var formatter = new CucumberHTML.DOMFormatter($('.cucumber-report'));formatter.uri("file:src/test/java/feature/Dashboard.feature");
formatter.feature({
  "name": "",
  "description": "",
  "keyword": "Feature",
  "tags": [
    {
      "name": "@Test"
    }
  ]
});
formatter.scenario({
  "name": "Retrieve 10 days weather for Day and Night and save to csv file",
  "description": "",
  "keyword": "Scenario",
  "tags": [
    {
      "name": "@Test"
    }
  ]
});
formatter.before({
  "status": "passed"
});
formatter.step({
  "name": "User visit Weather page",
  "keyword": "When "
});
formatter.match({
  "location": "com.project.stepDefinitions.RegionHeaderSteps.userVisitWeatherPage()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Verify that the search input field is displayed",
  "keyword": "Then "
});
formatter.match({
  "location": "com.project.stepDefinitions.RegionHeaderSteps.assertSearchFieldIsDisplayedOrNot()"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Input value Singapore in search field",
  "keyword": "When "
});
formatter.match({
  "location": "com.project.stepDefinitions.RegionHeaderSteps.inputValueToSearchField(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Select Singapore, North, Singapore Option From Result",
  "keyword": "Then "
});
formatter.match({
  "location": "com.project.stepDefinitions.RegionHeaderSteps.selectSingaporeOption(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Click Local Suite Header 10 Day",
  "keyword": "And "
});
formatter.match({
  "location": "com.project.stepDefinitions.RegionHeaderSteps.clickLocalSuiteHeader(java.lang.String)"
});
formatter.result({
  "status": "passed"
});
formatter.step({
  "name": "Retrieve 10 days weather for Day and Night",
  "keyword": "When "
});
formatter.match({
  "location": "com.project.stepDefinitions.RegionHeaderSteps.retrieve10DaysWeather(int)"
});
formatter.result({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
formatter.after({
  "status": "passed"
});
});
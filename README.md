## Framework Creator: Phu Dieu

# Precondition:
- Access src/main/java/global/Const: Change value OS to "windows" or "ios" for macOS
- Access config/config.properties: Change value browser=chrome/safari/edge,...
- Access src/main/java/global/Const: Change value UI to false (Run API script without launch webdriver 
or true (run UI) 

# How to create script in cucumber
## 1. Create an Object class in /Automation/src/main/java/com/project/pageObjects
    - Manage elements , actions of the page
    - UI Syntax: <PageName>Page (Ex: LoginPage,...)
    - API Syntax: EP_<PageName> (Ex: EP_Login,...)
    - Inheritance <new page> with BasePage (Ex: public class LoginPage extends BasePage)
    - Declare element xpath, id , css locator in the top
    - Using annotation @FindBy to declare Webelement
    - Finally create action methods of that page
    NOTE:
    - Using find-element in ./src/main/java/utils/selenium/ElementFinder.java to find element (Update if any)
    - Using actions in ./src/main/java/utils/selenium/ElementAction.java to get Action for Element (Update if any)
    - Using some common methods and helper in this package ./src/main/java/utils/common (Update if any)
    - Add new and using methods in ./src/main/java/com/project/pageObjects/CommonPage.java for UI Object page
    - Add new and using methods in ./src/main/java/com/project/API/endpoints/EP_Common.java for API endpoint
## 2. Create an Step Definition class in ./src/main/java/com/project/stepDefinitions 
    - Manage steps which will use in Feature file:
    - UI Syntax: <PageName>Steps (Ex: LoginSteps,...)
    - API Syntax: EP_<PageName>Steps (Ex: EP_LoginSteps,...)
    - Inheritance <new page> with TestBase (Ex: public class LoginSteps extends TestBase ) 
    - Declare Steps with plain text start with Given, When, Then, And
    NOTE:
    - Add and using steps in /src/main/java/com/project/stepDefinitions/CommonSteps.java for common steps use in many features for UI
    - Add and using steps in /src/main/java/com/project/stepDefinitions/api/EP_CommonSteps.java for common steps use in many features for API
## 3. Create an feature file in /Automation/src/test/java/feature
    - Manage all scenarios of feature
    - File extension (.feature)
    - UI Syntax: <PageName>.feature (Ex: Login.feature,...)
    - API Syntax: EP_<PageName>.feature (Ex: EP_Login.feature,...)
    - Feature file must have Feature key work at the top (EX:Feature: Login function)
    - Scenarios must start with keywork Scenario or Scenario Outline + Example
    - Create tag at the top of feature file @UI @Login @smoketest
    - Create tag at the beginning of Scenario or Scenario Outline
    
# How to execute 
## 1. Use Runner
    - tags = "@Test"
    - tags = {"@Test", "not @Ignore"} => not + @<Ignore> in order to not run @<Ignore> 
    - Click Run
## 2. Run with gradle commandline
    - Commandline to run test : gradle test -Dcucumber.options="--tags @tagName"
## 3. Run with testng.xml
    - Access src/main/java/global/Const: Change value TESTNG = true
    - Navigate to /src/test/resources/testng.xml
    - Update parameter browser value
    - Update class (path of runner)
    - Click Run

# Commit Rule:
- Commit with message format: [QA][<QAName>]: <Action Changed>
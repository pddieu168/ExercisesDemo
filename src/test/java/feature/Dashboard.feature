@Test
Feature:
  Scenario: Retrieve 10 days weather for Day and Night and save to csv file
    When User visit Weather page
    Then Verify that the search input field is displayed
    When Input value Singapore in search field
    Then Select Singapore, North, Singapore Option From Result
    And Click Local Suite Header 10 Day
    When Retrieve 10 days weather for Day and Night
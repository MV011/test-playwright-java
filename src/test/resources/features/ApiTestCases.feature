@API @TestManagement
Feature: Test Management API
  As a user
  I want to manage test cases through an API
  So that I can track and organize my testing efforts

  Background:
    Given I have request options set for login with the following data:
      | email          | username | password     |
      | test@email.com | testuser | testpass123! |
    When I send a POST request to "/login"
    Then the response status code should be 200
    And the response should contain a token
    And I update my headers with the authorization token
    Then I send a GET request to "/users/me"
    And I save the logged in user's data
    And I remove any leftover request data


  Scenario: Create Test Case
    Given I have a payload set for CreateTestCase with the following data:
      | title         | description        | status  |
      | Login Feature | Test login process | PENDING |
    When I send a POST request to "/test-cases/"
    Then the response status code should be 200
    And the response should contain the created test case

  Scenario: Retrieve a test case owned by another user
    Given I send a GET request to "/test-cases/2"
    Then the response status code should be 404
    And the response should contain the error "Test case not found"

  Scenario: Access Non-existent Test Case
    Given I send a GET request to "/test-cases/999"
    Then the response status code should be 404
    And the response should contain the error "Test case not found"

  Scenario: Retrieve Test Cases
    When I send a GET request to "/test-cases/"
    Then the response status code should be 200
    And the response should contain a list of test cases that belong to the current user

  Scenario: Update Test Case
    Given I have a payload set for CreateTestCase with the following data:
      | title         | description         | status    |
      | Updated Login | Updated description | COMPLETED |
    When I send a PUT request to "/test-cases/1"
    Then the response status code should be 200
    And the response should contain the updated test case

  Scenario: Delete Test Case
    When I send a DELETE request to "/test-cases/1"
    Then the response status code should be 200
    When I send a GET request to "/test-cases/1"
    Then the response status code should be 404


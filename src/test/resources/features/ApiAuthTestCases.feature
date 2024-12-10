@API @Auth
Feature: Test Management API Authentication
  As a user I want to be able to create accounts and authenticate

  Scenario: User Registration
    Given I have a payload set for CreateUserRequest with the following data:
      | email           | username  | password     |
      | test2@email.com | testuser2 | testpass123! |
    When I send a POST request to "/signup"
    Then the response status code should be 200
    And the response should contain user information

  Scenario: User Registration with Existing account
    Given I have a payload set for CreateUserRequest with the following data:
      | email          | username | password     |
      | test@email.com | testuser | testpass123! |
    When I send a POST request to "/signup"
    Then the response status code should be 400
    And the response should contain the error "Username already registered"

  Scenario: User Authentication with valid data
    Given I have request options set for login with the following data:
      | username | password     |
      | testuser | testpass123! |
    When I send a POST request to "/login"
    Then the response status code should be 200
    And the response should contain a token

  Scenario: User Authentication with incorrect username
    Given I have request options set for login with the following data:
      | username    | password     |
      | invaliduser | testpass123! |
    When I send a POST request to "/login"
    Then the response status code should be 401
    And the response should contain the error "Incorrect username or password"

  Scenario: Unauthorized Access
    When I send a GET request to "/test-cases/"
    Then the response status code should be 401
    And the response should contain the error "Not authenticated"

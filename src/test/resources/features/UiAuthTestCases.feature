@UI

Feature: Authentication
  As a user
  I want to be able to sign up and login
  So that I can access the test management system

  @Ignore
  Scenario: Successful user registration
    Given I am on the signup page
    When I enter "test@example.com" as email
    And I enter "testuser" as username
    And I enter "password123" as password
    And I enter "password123" as confirm password
    And I click the "Sign Up" button
    Then I should be redirected to the dashboard
    And I should see "Welcome, testuser" in the header
    
  @Ignore
  Scenario: Failed registration with mismatched passwords
    Given I am on the signup page
    When I enter "test@example.com" as email
    And I enter "testuser" as username
    And I enter "password123" as password
    And I enter "password456" as confirm password
    And I click the "Sign Up" button
    Then I should see an error message "Passwords do not match"

  Scenario: Successful login and logout
    Given I am on the login page
    When I enter "testuser" as username
    And I enter "testpass123!" as password
    And I click the Login button
    Then I should be redirected to the dashboard
    And I should see "Welcome, testuser" in the header
    When I click the Logout button
    Then I am on the login page

  Scenario: Failed login with incorrect credentials
    Given I am on the login page
    When I enter "testuser" as username
    And I enter "wrongpassword" as password
    And I click the Login button
    Then I should see an error message "Login failed. Please check your credentials."
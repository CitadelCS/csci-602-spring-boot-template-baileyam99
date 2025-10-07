Feature: Account API endpoints

  Scenario: client creates a new account
    When the client posts to "/account" with username "testuser" and email "test@example.com"
    Then the client receives status code of 201
    And the client receives an account with username "testuser" and email "test@example.com"

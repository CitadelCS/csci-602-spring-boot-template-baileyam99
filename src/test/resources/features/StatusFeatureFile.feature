Feature: the API endpoints can be retrieved

  Scenario: client makes call to GET /health
    When the client calls /health
    Then the client receives status code of 200
    And the client receives server status string ok

  Scenario: client makes call to GET /info
    When the client calls /info
    Then the client receives status code of 200
    And the client receives app name "csci-602"
    And the client receives app version "1.0.0"
    And the client receives app description "Template Spring Boot API for use of CSCI 602"

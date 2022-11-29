@GetProfile
Feature: Get Profile
  I want to use this template for my feature file

  @InvalidURL
  Scenario: Get Profile Using Invalid URL
    Given I have logged in the system
     When I send the request with invalid URL
     Then I validate the outcomes

  @InvalidMethod
  Scenario: Get Profile
    Given I want to view my profile information
     When I send the request with invalid method
     Then I validate the status code and the response message

  @InvalidToken
  Scenario: Get Profile Using Invalid Token
    Given I have logged in the system and is provided with a token
     When I send the request to view my profile with invalid token
     Then I check for the status code and response message

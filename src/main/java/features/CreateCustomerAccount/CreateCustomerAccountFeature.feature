@CreateCustommerAccount
Feature: Create Customer Account
  I want to use this template for my feature file

  @InvalidMethod
  Scenario: Validate sending request using invalid method
    Given I am a user and want to create an account
     When I send the request using the GET method
     Then I check the result

  @InvalidURL
  Scenario: Create Customer Account Using Invalid URL
    Given I want to create a customer account
     When I send the request using invalid URL
     Then I should not be able to create the account

  @InvalidToken
  Scenario: Check Invalid Token
    Given I want to create an account
     When I make the request with invalid token
     Then There should be a warning message

  @FieldsValidation
  Scenario Outline: Validate Single Field
    Given I want to create an user account
     When I enter value "<Value>" in the field "<Field>"
     Then The response code should be "<statusCode>" and the return message should be "<expectedMessage>"

    Examples: 
      | Field       | Value         | statusCode | expectedMessage                                        |
      | phoneNumber | null          |        400 | Invalid phone number (ex: 0123456789 or +840123456789) |
      | phoneNumber | missing       |        500 | No message available                                   |
      | phoneNumber | 0123456uw!    |        400 | Invalid phone number (ex: 0123456789 or +840123456789) |
      | phoneNumber |    0398961679 |        400 | phone number has been used                             |
      | password    | null          |        400 | password is not strong enough.                         |
      | password    | missing       |        500 | rawPassword cannot be null                             |
      | password    | Nguyentran123 |        400 | password is not strong enough.                         |

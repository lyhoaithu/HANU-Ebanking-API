@GetUserByID
Feature: Get User By ID
  I want to use this template for my feature file

  @InvalidURLAndMethod
  Scenario Outline: Validate Get User Information By ID Using Invalid URL/Method
    Given I want to get user by user ID
     When I send the request with the url "<url>" and method "<method>"
     Then I validate the outcome response "<statusCode>" and "<message>"

    Examples: 
      | url                                       | method | statusCode | message            |
      | http://localhost:8080/api/v1/user/uieyeiw | GET    |        400 | Bad Request        |
      | http://localhost:8080/api/v1/user/5       | POST   |        405 | Method Not Allowed |

  @InvalidToken
  Scenario: Validate Get User Information By ID Using Invalid Token
    Given I want to get an user information by user ID
     When I send the request with invalid an token
     Then The response code should be 401 and the message is 'Expired or invalid JWT token'

  @InvalidAccountID
  Scenario Outline: Validate Get User Information By ID Using Invalid Account ID
    Given I want to get an user profile by user ID
     When I send the request with invalid account ID "<accountID>"
     Then I validate the response code "<statusCode>" and warning message "<responseMessage>" of the "<accountID>"

    Examples: 
      | accountID | statusCode | responseMessage |
      |        30 |        400 |                 |
      | shf       |        400 | Bad Request     |

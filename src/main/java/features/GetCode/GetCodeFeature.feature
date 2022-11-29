@GetCode
Feature: Get Code
  I want to use this template for my feature file

  @CheckInvalidURLAndMethod
  Scenario Outline: Check Invalid URL and Method
    Given I want to get the code
     When I send the request using the url "<url>" and method "<method>"
     Then The response should have status code "<statusCode>" and message "<message>"

    Examples: 
      | url                                       | method | statusCode | message            |
      | http://localhost:8080/api/v1/auth/codea78 | GET    |        404 | Not Found          |
      | http://localhost:8080/api/v1/auth/code    | DELETE |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario Outline: Check Response Using Invalid Token
    Given I want to get the authentication code
     When I send the request using the invalid token
     Then The response should return the status code and message correctly

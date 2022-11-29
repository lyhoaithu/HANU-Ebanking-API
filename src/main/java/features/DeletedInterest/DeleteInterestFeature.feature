@DeleteInterest
Feature: DeleteInterest
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Validate Deleting Interest Using Invalid URL/Method
    Given I want to delete an interest
     When I delete using url "<url>" and method "<method>"
     Then I verify the results. Expected: "<statusCode>" + "<message>"

    Examples: 
      | url                                       | method | statusCode | message            |
      | http://localhost:8080/api/v1/interested/4 | DELETE |        404 | Not Found          |
      | http://localhost:8080/api/v1/interest/4   | GET    |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario: Validate Deleting Interest Using Invalid Token
    Given I want to delete an interest.
     When I delete interest with invalid token
     Then The actual response must be '401' and 'Expired or invalid JWT token'

  @ValidateFields
  Scenario Outline: Valididate Deleting Interest ID
    Given I want to delete a particular interest
     When I delete interest using: key "<key>" with value "<value>"
     Then I compare the actual results. Expected: code "<statusCode>" and message "<message>"

    Examples: 
      | key | value   | statusCode | message                                                                                                                                                                |
      | id  |      13 |        400 | interest not found                                                                                                                                                     |
      | id  |         |        405 | Request method 'DELETE' not supported                                                                                                                                  |
      | id  | missing |        405 | Request method 'DELETE' not supported                                                                                                                                  |
      | id  | abc     |        400 | Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \\"abc\\" |
      | id  |       1 |        400 | There is at least one saving or loan still using this interest                                                                                                         |

@ReturnLoan
Feature: Return Loan
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Validate Return Loan Using Invalid URL/Method
    Given I want to return the loan
     When I send the request using: "<url>" and "<method>"
     Then I should get status "<statusCode>" and message "<message>"

    Examples: 
      | url                                                       | method | statusCode | message            |
      | http://localhost:8080/api/v1/transaction/loan/returned/22 | GET    |        404 | Not Found          |
      | http://localhost:8080/api/v1/transaction/loan/return/22   | POST   |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario: Validate Return Loan Using Invalid Token
    Given I want to return my loan
     When I return my loan using invalid token
     Then I verify the result. Expected:  '401' and 'Expired or invalid JWT token'.

  @ValidateFields
  Scenario Outline: Validate Return Loan With IncorrectID
    Given I want to return my loan.
     When I return my loan using "<id>" value "<value>"
     Then I verify the outcome. Expected "<statusCode>" and "<message>"

    Examples: 
      | key | value   | statusCode | message                                                                                                                                                                |
      | id  |      15 |        400 | Not the loan owner                                                                                                                                                     |
      | id  |      40 |        400 | Invalid loan id                                                                                                                                                        |
      | id  |      23 |        400 | Loan has already been returned                                                                                                                                         |
      | id  | missing |        404 | Not Found                                                                                                                                                              |
      | id  |         |        404 | Not Found                                                                                                                                                              |
      | id  | abc     |        400 | Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"abc\" |

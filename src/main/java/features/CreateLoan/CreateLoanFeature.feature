@CreateLoan
Feature: Create Loan
  I want to use this template for my feature file

  @CheckInvalidURLAndMethod
  Scenario Outline: Validate Creating Loan Using Invalid URL/Method
    Given I want to create loan
     When I create loan using url "<url>" and method "<method>"
     Then I verify the result. Expected message: "<statusCode>" and "<message>"

    Examples: 
      | url                                             | method | statusCode | message            |
      | http://localhost:8080/api/v1/transaction/loan33 | POST   |        404 | Not Found          |
      | http://localhost:8080/api/v1/transaction/loan   | GET    |        405 | Method Not Allowed |

  @CheckInvalid/ExpiredToken
  Scenario: Validate Creating Loan Using Invalid Token
    Given I want to create a loan
     When I send out the request
     Then I verify the result. Expected result: '401' and 'Expired or invalid JWT token'

  @ValidateFields
  Scenario Outline: Validate Fields In Creating Loan
    Given I want to create a loan.
     When I create loan with information: "<key>": "<value>"
     Then I verify the result. Expected: "<statusCode>" and "<message>"

    Examples: 
      | key        | value   | statusCode | message                                                                                                                |
      | amount     | -100000 |        400 | the loan amount must be greater than 0                                                                                 |
      | amount     |       0 |        400 | the loan amount must be greater than 0                                                                                 |
      | amount     | abc     |        400 | Bad Request                                                                                                            |
      | amount     |         |        400 | the loan amount must be greater than 0                                                                                 |
      | amount     | missing |        400 | the loan amount must be greater than 0                                                                                 |
      | interestId |      13 |        400 | interest is not found                                                                                                  |
      | interestId | 13a     |        400 | Bad Request                                                                                                            |
      | interestId | missing |        500 | The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null! |
      | interestId |         |        500 | The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null! |

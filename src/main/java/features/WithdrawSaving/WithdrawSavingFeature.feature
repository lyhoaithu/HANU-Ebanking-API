@WithdrawSaving
Feature: Withdraw Saving
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Validate Withdrawing Saving Using Invalid URL/Method
    Given I want to withdraw the saving
     When I withdraw using the url "<url>" and "<method>"
     Then I verify the result: "<statusCode>" and "<message>"

    Examples: 
      | url                                                           | method | statusCode | message            |
      | http://localhost:8080/api/v1/transaction/saving/withdrawed/60 | GET    |        404 | Not Found          |
      | http://localhost:8080/api/v1/transaction/saving/withdraw/60   | POST   |        405 | Method Not Allowed |

  @CheckingInvalidToken
  Scenario: Validate Withdrawing Saving Using Invalid Token
    Given I want to withdraw my saving
     When I send out the request.
     Then I verify the result. Expected result: '401' and 'Expired or invalid JWT token'.

  @ValidateFields
  Scenario Outline: Validate Fields In Creating Loan
    Given I want to withdraw my saving.
     When I withdraw with information: "<key>": "<value>"
     Then I verify the result. Expected: "<statusCode>" and "<message>".

    Examples: 
      | key | value   | statusCode | message                                                                                                                                                                |
      | id  |      80 |        400 | Invalid saving id                                                                                                                                                      |
      | id  |      70 |        400 | Saving has already been deposited                                                                                                                                     |
      | id  |         |        404 | Not Found                                                                    |
      | id  | missing |        404 | Not Found                                                                                                                                                              |
      | id  |      10 |        400 | Not the saving owner                                                                                                                                                   |
      | id  | abc     |        400 | Failed to convert value of type 'java.lang.String' to required type 'java.lang.Long'; nested exception is java.lang.NumberFormatException: For input string: \"abc\" |

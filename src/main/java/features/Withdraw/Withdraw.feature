@Withdraw
Feature: Title of your feature
  I want to use this template for my feature file

  @CheckInvalidURLAndMethod
  Scenario Outline: Validate Withdraw Money
    Given I want to withdraw money
     When I make the request with the url "<url>" and method "<method>"
     Then I validate the result. Expected "<statusCode>" and "<message>"

    Examples: 
      | url                                                  | method | statusCode | message                              |
      | http://localhost:8080/api/v1/transaction/withdraw123 | POST   |        404 |                                      |
      | http://localhost:8080/api/v1/transaction/withdraw    | PATCH  |        405 | Request method 'PATCH' not supported |

  @ValidateBody
  Scenario Outline: Validate Withdraw Money Fields
    Given I want to withdraw an amount of money
     When I send the request with "<key>" equals "<value>"
     Then I verify the result. Expected "<statusCode>" and "<message>"

    Examples: 
      | key    | value       | statusCode | message                                                    |
      | amount |    -1000000 |        400 | the withdraw amount must be greater than 0                 |
      | amount |           0 |        400 | the withdraw amount must be greater than 0                 |
      | amount | abc123      |        500 | For input string: \\"abc123\\"                             |
      | amount |             |        400 | Required request part 'amount' is not present              |
      | amount | 10000000000 |        400 | the withdraw amount must be lower than the current balance |

  @CheckWithdrawUsingInvalidToken
  Scenario: Validating Withdraw Money Using Invalid Token
    Given I want to withdraw the money
     When I withdraw using invalid token
     Then I check the output. Expected '401' and 'Expired or invalid JWT token'

@SetBalance
Feature: Validate Setting Balance
  I want to use this template for my feature file

  @ValidateURLAndMethod
  Scenario Outline: Validate sending request using invalid url/method
    Given I want to set the balance for my account
     When I send the request with the url "<url>", method "<method>".
     Then The expected status is supposed to be "<statusCode>" with the message "<message>"

    Examples: 
      | url                                            | method | statusCode | message                             |
      | http://localhost:8080/api/v1/userabc/1/balance | PATCH  |        404 |                                     |
      | http://localhost:8080/api/v1/user/25/balance   | PATCH  |        400 | Account not found                   |
      | http://localhost:8080/api/v1/user/1/balance    | POST   |        405 | Request method 'POST' not supported |

  @ValidateInvalidToken
  Scenario: Validate Setting Balance Using Invalid Token
    Given I want to set the account balance
     When I send the request with expired or invalid token
     Then The status code must be '401' with the message 'Expired or invalid JWT token'

  @ValidateBody
  Scenario Outline: Validate body keys
    Given I want to set the balance
     When I send the request with the body key "<key>", value "<value>"
     Then The expected status is "<statusCode>" with the message "<message>"

    Examples: 
      | key     | value    | statusCode | message                                        |
      | balance | -1000000 |        400 | Balance must be greater than 0                 |
      | balance |        0 |        200 | Update balance success. Current balance is 0.0 |
      | balance | abc123   |        500 | For input string: \\"abc123\\"                 |
      | balance | null     |        400 | Required request part 'balance' is not present |

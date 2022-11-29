@Transfer
Feature: Validate Transfer
  I want to use this template for my feature file

  @CheckInvalidURLAndMethod
  Scenario Outline: Validate Transfer Using Invalid URL/Method
    Given I want to transfer the money
     When I send the request: url "<url>" with method "<method>"
     Then I validate the outcomes. Expected: statusCode "<statusCode>" and message "<message>"

    Examples: 
      | url                                                  | method | statusCode | message                            |
      | http://localhost:8080/api/v1/transaction/transfer123 | POST   |        404 | No message available               |
      | http://localhost:8080/api/v1/transaction/transfer    | GET    |        405 | Request method 'GET' not supported |

  @CheckInvalidToken
  Scenario: Validate Transfer Using Invalid Token
    Given I want to make a transfer
     When I send the request using wrong token value
     Then The status code is expected to be '401' with the message 'Expired or invalid JWT token'

  @ValidateBody
  Scenario Outline: Validate Transfer Body
    Given I want to make a transaction
     When I send the request with: key "<key>" and value "<value>"
     Then I validate the outcomes. Expected: statusCode "<statusCode>", message "<message>"

    Examples: 
      | key       | value       | statusCode | message                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
      | amount    |    -1000000 |        400 | the transfer amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | amount    |           0 |        400 | the transfer amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | amount    | abc         |        400 |JSON parse error: Unrecognized token 'abc': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false'); nested exception is com.fasterxml.jackson.core.JsonParseException: Unrecognized token 'abc': was expecting (JSON String, Number, Array, Object or token 'null', 'true' or 'false')\n at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 2, column: 19]|
      | amount    |             |        400 | the transfer amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      | amount    | 10000000000 |        400 | the transfer amount must be lower than the current balance                                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      #| amount    | missing     |        400 | the transfer amount must be greater than 0                                                                                                                                                                                                                                                                                                                       |
      #| toAccount |          22 |        200 | success                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
      | toAccount |          25 |        500 | Unable to find com.capt.ebankingbackend2022.entity.AccountEntity with id 25                                                                                                                                                                                                                                                                                                                                                                                                                                               |
      | toAccount |             |        500 | Unable to find com.capt.ebankingbackend2022.entity.AccountEntity with id 0                                                                                                                                                                                                                                                                                                                                                                                                                                                |
      #| toAccount | missing     |        500 | Unable to find com.capt.ebankingbackend2022.entity.AccountEntity with id 0                                                                                                                                                                                                                                                                                       |

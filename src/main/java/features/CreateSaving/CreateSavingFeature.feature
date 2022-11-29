@CreatingSaving
Feature: Create Saving
  I want to use this template for my feature file

  @CheckInvalidURLAndMethod
  Scenario Outline: Validate Creating Saving Using Invalid URL/Method
    Given I want to create a saving
     When I send the request with: url: "<url>" and method "<method>"
     Then I verify the status "<statusCode>" and response message "<message>"

    Examples: 
      | url                                                | method | statusCode | message            |
      | http://localhost:8080/api/v1/transaction/saving123 | POST   |        404 | Not Found          |
      | http://localhost:8080/api/v1/transaction/saving    | GET    |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario: Validate Creating Saving Using Invalid Token
    Given I want to create saving
     When I send the request but using invalid or expried token
     Then I should get the status code '401' and message 'Expired or invalid JWT token'

  @FieldsValidation
  Scenario Outline: Validate Creating Saving Entering Invalid Information in fields
    Given I want to create a saving.
     When I send the request with: key "<key1>": "<value1>" and key "<key2>": "<value2>"
     Then I expect to recieve status code: "<statusCode>" and response message: "<message>"

    Examples: 
      | key1        | value1        | key2               | value2 | statusCode | message                                                                                                                                                                                                                                                                                                                                                                                                                |
      | amount      |       -100000 | null               | null   |        400 | the saving amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                               |
      | amount      |             0 | null               | null   |        400 | the saving amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                               |
      | amount      | abc           | null               | null   |        400 | Bad Request|
      | amount      | 1000000000000 | null               | null   |        400 | the saving amount must be lower than the current balance                                                                                                                                                                                                                                                                                                                                                               |
      | amount      |               | null               | null   |        400 | the saving amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                               |
      | amount      | missing       | null               | null   |        400 | the saving amount must be greater than 0                                                                                                                                                                                                                                                                                                                                                                               |
      | interestId  |            13 | null               | null   |        400 | interest is not found                                                                                                                                                                                                                                                                                                                                                                                                  |
      | interestId  | 13a           | null               | null   |        400 | Bad Request|
      | interestId  | missing       | null               | null   |        500 | The given id must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!                                                                                                                                                                                                                                                                                                 |
      | hasMaturity | true          | maturityWithProfit | false  |        200 | create saving successfully, 100000.0                                                                                                                                                                                                                                                                                                                                                                                   |
      | hasMaturity | false         | maturityWithProfit | true  |        200 | create saving successfully, 100000.0                                                                                                                                                                                                                                                                                                                                                                                   |
      | hasMaturity | false         | maturityWithProfit | false  |        200 | create saving successfully, 100000.0                                                                                                                                                                                                                                                                                                                                                                                   |

@CreateInterest
Feature: Create Interest
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Validate Creating Interest Using Invalid URL/Method
    Given I want to create interest
     When I create interest using url: "<url>" and method "<method>"
     Then I verify the actual results. Expected: code "<statusCode>", message "<message>"

    Examples: 
      | url                                            | method | statusCode | message            |
      | http://localhost:8080/api/v1/interested/create | POST   |        404 | Not Found          |
      | http://localhost:8080/api/v1/interest/create   | GET    |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario: Validate Creating Interest Using Invalid Token
    Given I want to create an interest
     When I create interest with invalid token
     Then The actual response should be '401' and 'Expired or invalid JWT token'

  @ValidateFields
  Scenario Outline: Validate Create Interest Fields
    Given I want to create an interest.
     When I create interest using: key "<key>" with value "<value>"
     Then I verify the actual results. Expected: code "<statusCode>" and message "<message>"

    Examples: 
      | key         | value    | statusCode | message                                                                                                                         |
      | rate        |       -6 |        400 | Interest rate must be greater than 0 and lower than 100                                                                         |
      | rate        |        0 |        400 | Interest rate must be greater than 0 and lower than 100                                                                         |
      | rate        |      101 |        400 | Interest rate must be greater than 0 and lower than 100                                                                         |
      | rate        | abc      |        400 | Bad Request                                                                                                                     |
      | rate        |          |        400 | The given rate must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null!        |
      | rate        | missing  |        500 | Required request part 'rate' is not present                                                                                     |
      | instantRate |     -0.1 |        400 | Instant interest rate must be greater than 0 and lower than 100                                                                 |
      | instantRate |        0 |        400 | Instant interest rate must be greater than 0 and lower than 100                                                                 |
      | instantRate |      101 |        400 | Instant interest rate must be greater than 0 and lower than 100                                                                 |
      | instantRate | abc      |        400 | Bad Request                                                                                                                     |
      | instantRate |          |        400 | The given instantRate must not be null!; nested exception is java.lang.IllegalArgumentException: The given id must not be null! |
      | instantRate | missing  |        500 | Required request part 'instantRate' is not present                                                                              |
      | duration    |       -2 |        400 | Duration must be greater 1 day                                                                                                  |
      | duration    |        0 |        400 | Duration must be greater 1 day                                                                                                  |
      | duration    | abc      |        400 | Bad Request                                                                                                                     |
      | duration    |          |        400 | Duration must be greater 1 day                                                                                                  |
      | duration    | missing  |        500 | Duration must be greater 1 day                                                                                                  |
      | type        | withdraw |        400 | Interest type must be saving or loan                                                                                            |
      | type        |          |        400 | Interest type must be saving or loan                                                                                            |
      | type        | missing  |        500 | Interest type must be saving or loan                                                                                            |

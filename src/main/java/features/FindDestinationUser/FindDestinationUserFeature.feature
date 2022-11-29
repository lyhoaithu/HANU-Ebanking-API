@FindDestinationUser
Feature: Find Destination User
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Validate finding using invalid URL/Method
    Given I want to find an user based on their information
     When I send request using url "<url>" with method "<method>"
     Then The expected status code should be "<statusCode>" and return message should be"<error>"

    Examples: 
      | url                                                                      | method | statusCode | error              |
      | http://localhost:8080/api/v1ss/user/findUser?type=phone&value=0962370612 | GET    |        404 |                    |
      | http://localhost:8080/api/v1/user/findUser?type=phone&value=0962370612   | POST   |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario: Validate finding user using invalid Token
    Given I want to find an user using his or her information
     When I perform the request
     Then I should get the error message 'Expired or invalid JWT token'

  @ValidateParams
  Scenario Outline: Title of your scenario outline
    Given I want to find the user using their information
     When I send the request with params "<param1>" and "<param2>" with value "<value1>" and "<value2>"
     Then I verify the status "<statusCode>" and message "<message>"

    Examples: 
      | param1 | value1    | param2 | value2     | statusCode | message                                                                            |
      | type   | phone     | value  | 09623huch^ |        400 | User not found                                                                     |
      | type   | phone     | value  |            |        400 | User not found                                                                     |
      | type   | missing   | value  | 0962370612 |        400 | Required request parameter 'type' for method parameter type String is not present  |
      | type   | phone     | value  | missing    |        400 | Required request parameter 'value' for method parameter type String is not present |
      | type   |           | value  | 0962370612 |        400 | User not found                                                                     |
      | type   | firstName | value  | Lester     |        400 | User not found                                                                     |

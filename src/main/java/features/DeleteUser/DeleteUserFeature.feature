@DeleteUser
Feature: Delete User

  @CheckInvalidURLAndMethod
  Scenario Outline: Check Invalid URL/Method
    Given I want to delete an user
     When I send the request using URL "<url>" and Method "<method>"
     Then I validate the result of the response "<statusCode>" "<message>"

    Examples: 
      | url                                    | method | statusCode | message            |
      | http://localhost:8080/api/v1/userss/10 | DELETE |        404 | Not Found          |
      | http://localhost:8080/api/v1/user/10   | POST   |        405 | Method Not Allowed |

  @CheckInvalidToken
  Scenario: Check Invalid Token
    Given I want to delete an user.
     When I send the request using an invalid token.
     Then The response code should be 401 and the response message should be 'Expired or invalid JWT token'.

  @CheckInvalidURLAndMethod
  Scenario Outline: Validate Params
    Given I want to delete a particular user
     When I send the request with user id "<id>"
     Then I check the result of the response "<statusCode>" "<message>"

    Examples: 
      | id  | statusCode | message           |
      | abc |        401 | Bad Request       |
      |  30 |        401 | account not found |

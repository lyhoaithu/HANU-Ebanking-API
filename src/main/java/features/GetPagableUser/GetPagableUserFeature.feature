@GetPageableUser
Feature: Get Number Of User In A Page
  I want to use this template for my feature file

  @InvalidURLAndMethod
  Scenario Outline: Check Invalid URL Or Method
    Given I want to get pageable user
     When I send the request by using the url "<url>" and method "<method>"
     Then The response status should be "<expectedStatus>"

    Examples: 
      | url                                                           | method | expectedStatus |
      | http://localhost:8080/api/v1ss/user?page=0&size=5&sort=id,asc | GET    |            404 |
      | http://localhost:8080/api/v1/user/me                          | POST   |            405 |

  @CheckInvalidToken
  Scenario: Validate Get Pageable User Function Using Invalid Token
    Given I have an invalid token
     When I send the request to get pageable user
     Then The status code should be 401 and there should be an error message

  @ValidateParams
  Scenario Outline: Check Get Pageable User Using Invalid Parameters
    Given I want to get the number of user in one page
     When I send the request with url and param "<Param>" with value "<Value>"
     Then I validate the response outcomes. Expected results: "<StatusCode>", "<Message>", "<numberOfElements>", "<IDList>"

    Examples: 
      | Param | Value    | StatusCode | Message              | numberOfElements | IDList          |
      | page  |       30 |        200 | success              |                0 |                 |
      | page  | null     |        200 | success              |                5 | [1, 2, 3, 4, 5] |
      | page  | missing  |        200 | success              |                5 | [1, 2, 3, 4, 5] |
      | size  | !        |        200 | success              |               20 |                 |
      | size  | null     |        200 | success              |               20 |                 |
      | size  | missing  |        200 | success              |               20 |                 |
      | sort  | id, desc |        400 | sort param not found |                0 |                 |
      | sort  | null     |        200 | success              |                5 | [1, 2, 3, 4, 5] |
      | sort  | missing  |        200 | success              |                5 | [1, 2, 3, 4, 5] |

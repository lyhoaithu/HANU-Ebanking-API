@ChangePassword
Feature: Validate Changing Password
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Check Sending Request Using Invalid URL Or Method
    Given I want to change the password
     When I send out the request with "<url>" by method "<method>"
     Then The return result should be "<error>"

    Examples: 
      | url                                                  | method | error              |
      | http://localhost:8080/api/v1/user/password/changewrw | PATCH  | Not Found          |
      | http://localhost:8080/api/v1/user/password/change    | GET    | Method Not Allowed |

  @CheckInvalidToken
  Scenario Outline: Check Sending Request Using Invalid Token
    Given I want to change the password of my account
     When I send out the request but using invalid token
     Then The returned result should be '401' and message should be 'Expired or invalid JWT token'

  @ValidateParams
  Scenario Outline: Check Sending Request Using Invalid Param Value
    Given I want to change the password of my user account
     When I send out the request with param "<param1>" value "<value1>" and param "<param2>" value "<value2>"
     Then The returned result should be "<statusCode>" and "<message>".

    Examples: 
      | param1        | value1       | param2       | value2       | statusCode | message                                              |
      | previous_pass | Nguyen@123   | new_password | nguyentran08 |        400 | password is not strong enough.                       |
      | previous_pass | nguyentran08 | new_password | Nguyen@123   |        400 | password is not true                                 |
      | previous_pass |              | new_password | Nguyen@123   |        400 | Required request part 'previous_pass' is not present |
      | previous_pass | Nguyen@123   | new_password |              |        400 | Required request part 'new_password' is not present  |

@LogIn
Feature: User log in the system

  @CheckInvalidMethod
  Scenario Outline: Check status code and response message when send request with invalid method
    Given User wants to log in the system
     When User sends request using the url "<url>" with invalid method "<method>"
     Then The response status code should be "<statusCode>"

    Examples: 
      | url                                     | method | statusCode |
      | http://localhost:8080/api/v1/auth/login | GET    |        405 |

  @CheckInvalidURL
  Scenario Outline: Check status code and response message when send request with invalid URL
    Given User wants to login the system
     When User sends request using the invalid url "<url>" with valid method "<method>"
     Then The status code of the response should be "<statusCode>"

    Examples: 
      | url                                    | method | statusCode |
      | http://localhost:8080/api/v1/auth/sadd | POST   |        404 |

  @FieldsValidation
  Scenario Outline: Validate Fields
    Given I want to log in the system
     When I fill the field "<field>" with value "<value>" and send the request
     Then The status code should be "<statusCode>" and the message should be "<expectedMessage>"

    Examples: 
      | field       | value      | statusCode | expectedMessage                   |
      | phoneNumber | 0123456789 |        200 | login success                     |
      | phoneNumber | null       |        401 | phone number or password not true |
      | phoneNumber | 0123456uw! |        401 | phone number or password not true |
      | password    | Nguyen@123 |        200 | login success                     |
      | password    | null       |        401 | phone number or password not true |
      | password    | ac12!      |        401 | phone number or password not true |

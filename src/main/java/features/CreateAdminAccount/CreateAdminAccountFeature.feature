@CreateAdminAccount
Feature: Create Admin Account
  I want to use this template for my feature file

  @InvalidURL
  Scenario Outline: Validate Create Admin Account By Accessing The Invalid URL
    Given I want to create an admin account
     When I send the request using the invalid url "<url>"
     Then The status code should be "<expectedStatusCode>" and there should be a warning message saying "<expectedWarningMessage>"

    Examples: 
      | url                                         | expectedStatusCode | expectedWarningMessage |
      | http://localhost:8080/api/v1/auth/createsad |                404 | Not Found              |

  @InvalidMethod
  Scenario Outline: Validate Create Admin Account By Using Invalid Method
    Given I want to create an account for admin
     When I send the request using the invalid method
     Then The status code should be "<expectedStatusCode>" and the warning message should be "<expectedWarningMessage>"

    Examples: 
      | expectedStatusCode | expectedWarningMessage |
      |                405 | Method Not Allowed     |

  @ValidateFields
  Scenario Outline: Validate Fields
    Given I want to create the account for admin
     When I enters value in the field "<fieldName>" with value "<value>" and send the request
     Then The status code should be "<expectedStatusCode>" and message should be "<expectedMessage>"

    Examples: 
      | fieldName   | value                                | expectedStatusCode | expectedMessage                                        |
      | phoneNumber | null                                 |                400 | Invalid phone number (ex: 0123456789 or +840123456789) |
      | phoneNumber | missing                              |                500 | Internal Server Error                                  |
      | phoneNumber | 01234hiug!@                          |                400 | Invalid phone number (ex: 0123456789 or +840123456789) |
      | phoneNumber |                           0398961679 |                400 | phone number has been used                             |
      | password    | null                                 |                400 | password is not strong enough.                         |
      | password    | missing                              |                500 | Internal Server Error                                  |
      | password    | nguyen123                            |                400 | password is not strong enough.                         |
      | code        | missing                              |                401 | Don't have permission                                  |
      | code        | NGUYENDZAI02                         |                400 | Admin code not existed                                 |
      | code        | 0db761de-e0a6-497e-a6c2-ce54c973d16f |                400 | Admin code is inactive                                 |

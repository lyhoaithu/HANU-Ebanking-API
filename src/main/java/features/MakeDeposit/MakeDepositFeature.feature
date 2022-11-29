@MakeDeposit
Feature: Validate Making Deposit
  I want to use this template for my feature file

  @CheckInvalidURL/Method
  Scenario Outline: Validate Making Deposit Using Invalid URL/Method
    Given I want to make a deposit
     When I send the request using the url "<url>", method "<method>"
     Then I verify the result "<statusCode>" and "<errorMessage>"

    Examples: 
      | url                                                | method | statusCode | errorMessage       |
      | http://localhost:8080/api/v1/transactionss/deposit | POST   |        404 |                    |
      | http://localhost:8080/api/v1/transaction/deposit   | PATCH  |        405 | Method Not Allowed |

  @CheckInvalidURL/Method
  Scenario Outline: Validate Making Deposit Using Invalid Params
    Given User wants to make a deposit
     When He sends the request with key "<key>" and value "<value>"
     Then He should get the code "<statusCode>" and error message "<errorMessage>"

    Examples: 
      | key    | value  | statusCode | errorMessage                                  |
      | amount |    -10 |        400 | the deposit amount must be greater than 0     |
      | amount |      0 |        400 | the deposit amount must be greater than 0     |
      | amount | abc123 |        500 | For input string: \\"abc123\\"                |
      | amount | null   |        400 | Required request part 'amount' is not present |

  #| amount | missing |        500 | Failed to parse multipart servlet request; nested exception is javax.servlet.ServletException: org.apache.tomcat.util.http.fileupload.impl.InvalidContentTypeException: the request doesn't contain a multipart/form-data or multipart/mixed stream, content type header is null |
  @CheckInvalidToken
  Scenario Outline: Validate Making Deposit Using Invalid Token
    Given I want to create a deposit
     When I send the request using the wrong token
     Then The expected status code should be '401' and the error message displayed 'Expired or invalid JWT token'

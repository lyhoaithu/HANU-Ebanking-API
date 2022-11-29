package stepsdefinition.ReturnLoan;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidURLAndMethodStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	
  @Given("I want to return the loan")
  public void givenIWantToReturnTheLoan() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon();
  }

  @When("I send the request using: {string} and {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equals("POST")) {
		  String requestBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateLoan\\CreateLoanData.json");
		  response= apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
	  }
	  else {
		  response=apiUtils.sendGETRequestWithToken(url, token);
	  }
  }

  @Then("I should get status {string} and message {string}")
  public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "error");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

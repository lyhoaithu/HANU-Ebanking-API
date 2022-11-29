package stepsdefinition.CreateInterest;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidTokenStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	String url="http://localhost:8080/api/v1/interest/create";
	
  @Given("I want to create an interest")
  public void givenIWantToCreateAnInterest() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon()+"123";
  }

  @When("I create interest with invalid token")
  public void whenISendOutTheRequest() throws Throwable {
	  String requestBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateInterest\\CreateInterestData.json");
	  response= apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
  }

  @Then("The actual response should be '401' and 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

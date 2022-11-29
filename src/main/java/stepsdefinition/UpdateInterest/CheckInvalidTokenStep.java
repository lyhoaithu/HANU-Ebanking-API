package stepsdefinition.UpdateInterest;

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
	String url="http://localhost:8080/api/v1/interest/12";
	
  @Given("I want to update an interest")
  public void givenIWantToUpdateAnInterest() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon()+"123";
  }

  @When("I update interest with invalid token")
  public void whenISendOutTheRequest() throws Throwable {
	  String requestBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\UpdateInterest\\UpdateInterestData.json");
	  response= apiUtils.sendPUTWithToken(url, requestBody, token);
  }

  @Then("The actual response must be: '401' and 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

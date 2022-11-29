package stepsdefinition.DeleteInterest;

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
	String url="http://localhost:8080/api/v1/interest/18";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	
  @Given("I want to delete an interest.")
  public void givenIWantToDeleteAnInterest() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon()+"123";
  }

  @When("I delete interest with invalid token")
  public void whenISendTheRequest() throws Throwable {
	  response=apiUtils.sendDELETEWithToken(url, token);
  }

  @Then("The actual response must be '401' and 'Expired or invalid JWT token'")
  public void thenIValidateTheOutcome() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

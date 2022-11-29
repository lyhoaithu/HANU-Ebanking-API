package stepsdefinition.WithdrawSaving;

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
	String url="http://localhost:8080/api/v1/transaction/saving/withdraw/60";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
  @Given("I want to withdraw my saving")
  public void givenIWantToWithdrawSaving() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon()+"123";
  }

  @When("I send out the request.")
  public void whenISendTheRequest() throws Throwable {
	  response= apiUtils.sendGETRequestWithToken(url, token);
  }

  @Then("I verify the result. Expected result: '401' and 'Expired or invalid JWT token'.")
  public void thenVerifyTheResult() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

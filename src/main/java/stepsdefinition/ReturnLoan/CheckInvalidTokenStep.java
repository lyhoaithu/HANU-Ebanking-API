package stepsdefinition.ReturnLoan;

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
	String url="http://localhost:8080/api/v1/transaction/loan/return/22";
  @Given("I want to return my loan")
  public void givenIWantToReturnMyLoan() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon()+"123";
  }

  @When("I return my loan using invalid token")
  public void whenISendTheRequest() throws Throwable {
	  response=apiUtils.sendGETRequestWithToken(url, token);
  }

  @Then("I verify the result. Expected:  '401' and 'Expired or invalid JWT token'.")
  public void thenIVerifyTheResult() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

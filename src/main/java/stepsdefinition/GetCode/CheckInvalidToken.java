package stepsdefinition.GetCode;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidToken {
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	HttpResponse<String> response01;
	String token;
	String url="http://localhost:8080/api/v1/auth/code";
  @Given("I want to get the authentication code")
  public void givenIWantToGetTheAuthenticationCode() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request using the invalid token")
  public void whenISendTheRequestUsingInvalidToken() throws Throwable {
	  String invalidToken= token+"124";
	  response01=apiUtils.sendGETRequestWithToken(url, invalidToken);
  }

  @Then("The response should return the status code and message correctly")
  public void thenIValidateTheResult() throws Throwable {
	  String actualMessage=jsonUtils.getDataByKey(response01.body(), "message");
	  String actualStatusCode= Integer.toString(response01.statusCode());
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
	  Assert.assertEquals("401", actualStatusCode);
  }

}

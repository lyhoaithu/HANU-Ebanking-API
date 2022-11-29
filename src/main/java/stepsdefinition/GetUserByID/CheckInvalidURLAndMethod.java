package stepsdefinition.GetUserByID;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidURLAndMethod {
	HttpResponse<String> response;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String token;
  @Given("I want to get user by user ID")
  public void givenIWantToGetUserByUserID() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with the url {string} and method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equalsIgnoreCase("GET")) {
		  response=apiUtils.sendGETRequest(url);
	  }
	  else if(method.equalsIgnoreCase("POST")) {
		  response=apiUtils.sendPOSTRequest(url, method);
	  }
  }

  @Then("I validate the outcome response {string} and {string}")
  public void thenIValidateTheOutcome(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode=Integer.toString(response.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "error");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

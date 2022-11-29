package stepsdefinition.FindDestinationUser;

import java.net.http.HttpResponse;
import java.util.HashMap;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidURLAndMethod {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to find an user based on their information")
  public void givenIWantToFindAnUser() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send request using url {string} with method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equals("GET")) {
	  response= apiUtils.sendGETRequestWithToken(url, token);
  }
	  else {
		  response= apiUtils.sendPOSTRequestWithToken(url, "", token);
	  }
  }

  @Then("The expected status code should be {string} and return message should be{string}")
  public void thenIValidateTheOutCome(String expectedStatusCode, String expectedError) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  if(actualStatusCode.equals("405")) {
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "error");
	  Assert.assertEquals(expectedError, actualMessage);
  }
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
  }

}

package stepsdefinition.DeleteInterest;

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
	
  @Given("I want to delete an interest")
  public void givenIWantToDeleteAnInterest() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon();
  }

  @When("I delete using url {string} and method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equals("DELETE")) {
		  response= apiUtils.sendDELETEWithToken(url, token);
	  }
	  else {
		  response=apiUtils.sendGETRequestWithToken(url, token);
	  }
  }

  @Then("I verify the results. Expected: {string} + {string}")
  public void thenIVerifyTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "error");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

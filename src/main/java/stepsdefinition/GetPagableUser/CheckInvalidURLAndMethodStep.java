package stepsdefinition.GetPagableUser;

import java.net.http.HttpResponse;

import org.apiguardian.api.API;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidURLAndMethodStep {
	HttpResponse<String> response;
	LogInPreCondition preCon= new LogInPreCondition();
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to get pageable user")
  public void givenIWantToGetPageableUser() throws Throwable {
	  token=preCon.PreCon();
  }

  @When("I send the request by using the url {string} and method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equalsIgnoreCase("GET")) {
		  response=apiUtils.sendGETRequestWithToken(url, token);
	  }
	  else if (method.equalsIgnoreCase("POST")) {
		  response=apiUtils.sendPOSTRequestWithToken(url, null, token);
	  }
  }

  @Then("The response status should be {string}")
  public void thenIValidateTheStatusCode(String expectedStatusCode ) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
  }

}

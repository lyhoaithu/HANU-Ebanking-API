package stepsdefinition.GetPagableUser;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidTokenStep {
	HttpResponse<String> response;
	String token;
	String url="http://localhost:8080/api/v1/user/me";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I have an invalid token")
  public void givenIHaveAnInvalidToken() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon()+"4982749";
  }

  @When("I send the request to get pageable user")
  public void whenISendTheRequest() throws Throwable {
	  response= apiUtils.sendGETRequestWithToken(url, token);
  }

  @Then("The status code should be 401 and there should be an error message")
  public void thenIValidateTheStatusCode() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

package stepsdefinition.FindDestinationUser;

import java.net.http.HttpResponse;
import java.util.HashMap;

import common.APIUtils;
import common.JSONUtils;
import common.URLUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class ValidateParamsStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	URLUtils urlUtils= new URLUtils();
	String url="http://localhost:8080/api/v1/user/findUser?type=phone&value=0962370612";
  @Given("I want to find the user using their information")
  public void givenIWantToFindAnUser() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with params {string} and {string} with value {string} and {string}")
  public void whenISendTheRequest(String param1, String param2, String value1, String value2) throws Throwable {
	  url= urlUtils.changeURL(url, param1, value1);
	  url=urlUtils.changeURL(url, param2, value2);
	  response=apiUtils.sendGETRequestWithToken(url, token);
  }
  

  @Then("I verify the status {string} and message {string}")
  public void thenIVerifyTheResult(String expectedStatusCode, String expectedErrorMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualErrorMessage= jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
  }

}

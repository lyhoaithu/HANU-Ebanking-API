package stepsdefinition.FindDestinationUser;

import java.net.http.HttpResponse;
import java.util.HashMap;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidToken {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String url="http://localhost:8080/api/v1/user/findUser?type=phone&value=0962370612";
  @Given("I want to find an user using his or her information")
  public void givenIWantToFindTheUser() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon()+"dhkhj";
  }

  @When("I perform the request")
  public void whenIPerformTheRequest() throws Throwable {
	  response= apiUtils.sendGETRequestWithToken(url, token);
  }

  @Then("I should get the error message 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401",actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

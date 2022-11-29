package stepsdefinition.GetUserByID;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidToken {
	HttpResponse<String> response;
	String token;
	String url="http://localhost:8080/api/v1/user/5";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to get an user information by user ID")
  public void givenIWantToGetTheUserInf() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with invalid an token")
  public void whenISendTheRequestUsingInvalidToken() throws Throwable {
	  String invalidToken= token +"20489";
	  response= apiUtils.sendGETRequestWithToken(url, invalidToken);
  }

  @Then("The response code should be 401 and the message is 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  String actualStatusCode=Integer.toString(response.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
	  
  }

}

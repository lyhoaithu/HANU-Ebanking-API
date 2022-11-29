package stepsdefinition.GetProfile;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.messages.JSON;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidTokenStep{
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	HttpResponse<String> response01;
	String token;
	String url="http://localhost:8080/api/v1/user/me";
  @Given("I have logged in the system and is provided with a token")
  public void givenIHaveLoggedInTheSystemAndIsProvidedWithAToken() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request to view my profile with invalid token")
  public void whenISendTheRequestWithInvalidToken() throws Throwable {
	  String invalidToken= token+"123";
	  response01= apiUtils.sendGETRequestWithToken(url, invalidToken );
  }

  @Then("I check for the status code and response message")
  public void thenIValidateTheStatusCodeAndMessage() throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response01.body(),"message");
	  String actualStatusCode=Integer.toString(response01.statusCode());
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
	  Assert.assertEquals("401", actualStatusCode);
	  
  }

}

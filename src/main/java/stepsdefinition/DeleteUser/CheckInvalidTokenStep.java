package stepsdefinition.DeleteUser;

import java.net.http.HttpResponse;

import org.junit.Assert;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidTokenStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String url="http://localhost:8080/api/v1/user/10";
  @Given("I want to delete an user.")
  public void givenIWantToDeleteAnUSer() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon()+"8267";
  }

  @When("I send the request using an invalid token.")
  public void whenISendTheRequest() throws Throwable {
	  response= apiUtils.sendDELETEWithToken(url, token);
	  
  }

  @Then("The response code should be 401 and the response message should be 'Expired or invalid JWT token'.")
  public void thenIValidateTheResult() throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "message");
	  String actualStatusCode= Integer.toString(response.statusCode());
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

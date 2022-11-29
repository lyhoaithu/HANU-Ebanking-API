package stepsdefinition.DeleteUser;

import java.net.http.HttpResponse;

import org.junit.Assert;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.sl.In;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidURLAndMethodStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to delete an user")
  public void givenIWantToDeleteAnUser() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request using URL {string} and Method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equals("DELETE")) {
		  response=apiUtils.sendDELETEWithToken(url, token);
	  }
	  else if(method.equals("POST")) {
		  response=apiUtils.sendPOSTRequestWithToken(url, "", token);
	  }
  }

  @Then("I validate the result of the response {string} {string}")
  public void thenIValidateTheResult(String statusCode, String message) throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "error");
	  String actualStatusCode= Integer.toString(response.statusCode());
	  Assert.assertEquals(statusCode, actualStatusCode);
	  Assert.assertEquals(message, actualMessage);
  }

}

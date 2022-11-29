package stepsdefinition.DeleteInterest;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class ValidateFieldsStep {
	HttpResponse<String> response;
	String token;
	String url="http://localhost:8080/api/v1/interest";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	
  @Given("I want to delete a particular interest")
  public void givenIWantToDeleteAnInterest() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token=logIn.PreCon();
  }

  @When("I delete interest using: key {string} with value {string}")
  public void whenISendTheRequest(String key, String value) throws Throwable {
	  if(!value.equals("missing")) {
	  url=url+"/"+value;
  }
	  response=apiUtils.sendDELETEWithToken(url, token);
  }

  @Then("I compare the actual results. Expected: code {string} and message {string}")
  public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  if(actualMessage.contains("JSON")) {
		  actualMessage= jsonUtils.getDataByKey(response.body(), "error");
	  }
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

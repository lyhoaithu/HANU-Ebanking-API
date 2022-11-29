package stepsdefinition.ReturnLoan;

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
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	String url="http://localhost:8080/api/v1/transaction/loan/return/";
  @Given("I want to return my loan.")
  public void givenIWantToReturnMyLoan() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon();
  }

  @When("I return my loan using {string} value {string}")
  public void whenISendTheRequest(String key, String value) throws Throwable {
	  if(!value.equals("missing")) {
		  url=url+value;
	  }
	  response=apiUtils.sendGETRequestWithToken(url, token);
  }

  @Then("I verify the outcome. Expected {string} and {string}")
  public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	 String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  if(actualStatusCode.equals("404")) {
		  actualMessage= jsonUtils.getDataByKey(response.body(), "error");
	  }
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

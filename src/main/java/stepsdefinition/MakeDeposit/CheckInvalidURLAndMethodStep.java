package stepsdefinition.MakeDeposit;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidURLAndMethodStep {
	String []response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to make a deposit")
  public void givenIWantToMakeADeposit() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request using the url {string}, method {string}")
  public void when(String url, String method) throws Throwable {
	  String [][]keysAndValues= new String [1][2];
	  keysAndValues[0][0]="amount";
		keysAndValues[0][1]="10000";
	  if(method.equalsIgnoreCase("POST")) {
		  response= apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
	  }
	  else {
		  response=apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);
	  }
  }

  @Then("I verify the result {string} and {string}")
  public void thenIVerifyTheResult(String expectedStatusCode, String expectedErrorMessage) throws Throwable {
	  Assert.assertEquals(response[0], expectedStatusCode);
	  if(response[0].equals("405")) {
		  String actualErrorMessage= jsonUtils.getDataByKey(response[1], "error");
		  Assert.assertEquals(expectedErrorMessage, actualErrorMessage);
	  }
  }

}

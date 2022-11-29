package stepsdefinition.SetBalance;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
public class CheckInvalidURLAndMethodStep {
	String[] response;
	String token;
	String url="http://localhost:8080/api/v1/user/1/balance";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to set the balance for my account")
  public void givenIWantToSetTheBalance() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with the url {string}, method {string}.")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  String [][]keysAndValues= new String [1][2];
	  keysAndValues[0][0]="balance";
	  keysAndValues[0][1]="100000";
	  if(method.equals("PATCH")) {
		  response=apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);
	  }
	  else {
		  response=apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
	  }
  }

  @Then("The expected status is supposed to be {string} with the message {string}")
  public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  Assert.assertEquals(expectedStatusCode, response[0]);
	  if(!response[0].equals("404")) {
		  String actualMessage= jsonUtils.getDataByKey(response[1], "message");
		  Assert.assertEquals(expectedMessage, actualMessage);
	  }
	
  }

}

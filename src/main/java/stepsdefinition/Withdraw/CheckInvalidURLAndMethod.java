package stepsdefinition.Withdraw;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidURLAndMethod {
	String[] response;
	String token;
	String url="http://localhost:8080/api/v1/transaction/withdraw";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to withdraw money")
  public void givenIWantToWithdraw() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I make the request with the url {string} and method {string}")
  public void whenIMakeTheRequest(String url, String method) throws Throwable {
	  String[][]keysAndValues= new String[1][2];
	  keysAndValues[0][0]="amount";
	  keysAndValues[0][1]="100000";
	  if(method.equals("POST")) {
		  response=apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
	  }
	  else {
		  response=apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);
	  }
  }

  @Then("I validate the result. Expected {string} and {string}")
  public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  Assert.assertEquals(expectedStatusCode, response[0]);
	  if(response[0].equals("405")){
		  Assert.assertEquals(expectedMessage,jsonUtils.getDataByKey(response[1], "message") ); 
	  }
	
  }

}

package stepsdefinition.MakeDeposit;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidTokenStep {
	String []response;
	String token;
	String url="http://localhost:8080/api/v1/transaction/deposit";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to create a deposit")
  public void givenIWantToCreateADeposit() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon()+"123";
  }

  @When("I send the request using the wrong token")
  public void whenISendTheRequest() throws Throwable {
	  String [][]keysAndValues= new String[1][2];
	  keysAndValues[0][0]="amount";
	  keysAndValues[0][1]="100000";
	  response= apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
  }

  @Then("The expected status code should be '401' and the error message displayed 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  Assert.assertEquals(response[0], "401");
	String actualMessage= jsonUtils.getDataByKey(response[1], "message");
	Assert.assertEquals("Expired or invalid JWT token", actualMessage);
	  
  }

}

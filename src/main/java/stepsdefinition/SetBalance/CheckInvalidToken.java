package stepsdefinition.SetBalance;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
public class CheckInvalidToken {
	String[] response;
	String token;
	String url="http://localhost:8080/api/v1/user/1/balance";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to set the account balance")
  public void givenIWantToSetTheAccountBalance() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon()+"123";
  }

  @When("I send the request with expired or invalid token")
  public void whenISendTheRequest() throws Throwable {
	  String [][] keysAndValues= new String [1][2];
	  keysAndValues[0][0]="balance";
	  keysAndValues[0][1]="100000";
	  response= apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);  
}

  @Then("The status code must be '401' with the message 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response[1], "message");
	  Assert.assertEquals("401", response[0]);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

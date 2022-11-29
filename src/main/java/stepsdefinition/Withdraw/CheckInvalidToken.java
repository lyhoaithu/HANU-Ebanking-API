package stepsdefinition.Withdraw;

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
	String url="http://localhost:8080/api/v1/transaction/withdraw";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to withdraw the money")
  public void givenIWantToWithdrawMoney() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon()+"123";
  }

  @When("I withdraw using invalid token")
  public void whenIWithdraw() throws Throwable {
	  String[][]keysAndValues= new String[1][2];
	  keysAndValues[0][0]="amount";
	  keysAndValues[0][1]="100000";
	  response= apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
  }

  @Then("I check the output. Expected '401' and 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  Assert.assertEquals(response[0], "401");
	  Assert.assertEquals(jsonUtils.getDataByKey(response[1], "message"), "Expired or invalid JWT token");
  }

}

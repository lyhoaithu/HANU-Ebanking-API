package stepsdefinition.Withdraw;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class ValidateBody {
	String[] response;
	String token;
	String url="http://localhost:8080/api/v1/transaction/withdraw";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to withdraw an amount of money")
  public void givenIWantToWithdrawTheMoney() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with {string} equals {string}")
  public void whenISendTheRequest(String key, String value) throws Throwable {
	  String[][]keysAndValues= new String [1][2];
	  keysAndValues[0][0]=key;
	  keysAndValues[0][1]=value;
	  response= apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
	  }

  @Then("I verify the result. Expected {string} and {string}")
  public void thenIVerifyTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response[1], "message");
	  Assert.assertEquals(expectedStatusCode, response[0]);
	  Assert.assertEquals(expectedMessage, actualMessage);

  }
  

}

package stepsdefinition.SetBalance;

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
	String url="http://localhost:8080/api/v1/user/1/balance";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	
  @Given("I want to set the balance")
  public void givenIWantToSetTheBalance() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with the body key {string}, value {string}")
  public void whenISendTheRequest(String key, String value) throws Throwable {
	  String [][] keysAndValues= new String [1][2];
	  keysAndValues[0][0]=key;
	  if(value.equals("null")) {
		  keysAndValues[0][0]=key;
		  keysAndValues[0][1]="";
	  }
	  else {
		  keysAndValues[0][0]=key;
		  keysAndValues[0][1]=value;
	  }
	  response= apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);
  }

  @Then("The expected status is {string} with the message {string}")
  public void thenIValidateTheResponse(String expectedStatusCode, String expectedMessage) throws Throwable {
	  Assert.assertEquals(expectedStatusCode, response[0]);
	  String actualMessage= jsonUtils.getDataByKey(response[1],"message");
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

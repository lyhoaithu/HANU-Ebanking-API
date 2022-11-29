package stepsdefinition.ChangePassword;

import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

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
  @Given("I want to change the password")
  public void givenIWantToChangeThePassword() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send out the request with {string} by method {string}")
  public void whenISendOutTheRequest(String url, String method) throws Throwable {
	  String [][] keysAndValues= new String[2][2];
		keysAndValues[0][0]="previous_pass";
		keysAndValues[0][1]="Nguyen@123";
		keysAndValues[1][0]="new_password";
		keysAndValues[1][1]="nguyen@08";
	  if(method.equalsIgnoreCase("patch")) {
		
			  response=apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);
	  }
	  else {
		  response=apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
	  }
  }

  @Then("The return result should be {string}")
  public void thenIValidateTheResult(String errorMessage) throws Throwable {
	  JSONParser parser= new JSONParser();
		 JSONObject responseJSON= (JSONObject) parser.parse(response[1]);
	  String actualMessage= responseJSON.get("error").toString();
	  Assert.assertEquals(errorMessage, actualMessage);
  }

}

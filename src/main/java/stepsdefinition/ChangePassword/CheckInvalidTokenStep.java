package stepsdefinition.ChangePassword;

import java.net.http.HttpResponse;
import java.util.HashMap;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.messages.internal.com.fasterxml.jackson.databind.ObjectMapper;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidTokenStep {
	String[] response;
	String token;
	String url="http://localhost:8080/api/v1/user/password/change";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to change the password of my account")
  public void given() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon()+"28398";
  }

  @When("I send out the request but using invalid token")
  public void whenISendOutTheRequest() throws Throwable {
	String [][] keysAndValues= new String[2][2];
	keysAndValues[0][0]="previous_pass";
	keysAndValues[0][1]="Nguyen@123";
	keysAndValues[1][0]="new_password";
	keysAndValues[1][1]="nguyen@08";
	 response=apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues); 
	  
  }

  @Then("The returned result should be '401' and message should be 'Expired or invalid JWT token'")
  public void thenIValidateTheResult() throws Throwable {
	  JSONParser parser= new JSONParser();
		 JSONObject responseJSON= (JSONObject) parser.parse(response[1]);
	  String actualMessage= responseJSON.get("message").toString();
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

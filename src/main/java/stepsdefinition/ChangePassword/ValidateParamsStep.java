package stepsdefinition.ChangePassword;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
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

public class ValidateParamsStep {
	String[] response;
	String token;
	String url="http://localhost:8080/api/v1/user/password/change";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	
  @Given("I want to change the password of my user account")
  public void givenIWantToChangeTheUserPassword() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send out the request with param {string} value {string} and param {string} value {string}")
  public void whenISendTheRequest(String param1, String value1, String param2, String value2) throws Throwable {
	  String[][] keysAndValues= new String [2][2];
	  keysAndValues[0][0]=param1;
		keysAndValues[0][1]=value1;
		keysAndValues[1][0]=param2;
		keysAndValues[1][1]=value2;

	response=apiUtils.sendPATCHWithTokenAndDataForm(url, token, keysAndValues);
	
  }

  @Then("The returned result should be {string} and {string}.")
  public void thenIValidateTheResult(String statusCode, String message) throws Throwable {
	  JSONParser parser= new JSONParser();
		 JSONObject responseJSON= (JSONObject) parser.parse(response[1]);
	  String actualMessage= responseJSON.get("message").toString();
	  Assert.assertEquals(message, actualMessage);
	  Assert.assertEquals(response[0], statusCode);
  }

}

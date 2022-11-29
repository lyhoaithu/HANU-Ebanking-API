package stepsdefinition.MakeDeposit;

import java.net.http.HttpResponse;
import java.util.ArrayList;
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
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;


public class ValidateBodyStep {
	String []response;
	String token;
	String url="http://localhost:8080/api/v1/transaction/deposit";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("User wants to make a deposit")
  public void givenUserWantsToMakeADeposit() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("He sends the request with key {string} and value {string}")
  public void whenHeSendsTheRequest(String key, String value) throws Throwable {
String [][] keysAndValues= new String [1][2];

if(value.equals("null")) {
	keysAndValues[0][0]=key;
	keysAndValues[0][1]="";
	response= apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
	
}

else {
	keysAndValues[0][0]=key;
	keysAndValues[0][1]=value;
	response= apiUtils.sendPOSTWithTokenAndDataForm(url, token, keysAndValues);
}

  }

  @Then("He should get the code {string} and error message {string}")
  public void thenHeShouldGetTheError(String expectedStatusCode, String expectedErrorMessage) throws Throwable {
	  JSONParser parser= new JSONParser();
		 JSONObject responseJSON= (JSONObject) parser.parse(response[1]);
	  String actualMessage= responseJSON.get("message").toString();
	  Assert.assertEquals(expectedErrorMessage, actualMessage);
	  Assert.assertEquals(expectedStatusCode, response[0]);
  }

}

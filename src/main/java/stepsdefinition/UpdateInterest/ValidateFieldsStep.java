package stepsdefinition.UpdateInterest;

import java.io.File;
import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class ValidateFieldsStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	String url="http://localhost:8080/api/v1/interest";
	
  @Given("I want to update an interest.")
  public void givenIWantToCreateAnInterest() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon();
  }

  @When("I update interest using: key {string} with value {string}")
  public void whenISendTheRequest(String key, String value) throws Throwable {
	  File original= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateInterest\\UpdateInterestData.json");
	  File destination= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateInterest\\UpdateInterestDataCopy.json");
	  jsonUtils.copyJSONFile(original, destination);
	  String requestBody=jsonUtils.readJsonFile(destination.getAbsolutePath());
	 requestBody=jsonUtils.changeValueByFieldName(destination, key, value);
	 response=apiUtils.sendPUTWithToken(url, requestBody, token);
  }

  @Then("I verify the actual results: Expected: code {string} and message {string}")
  public void thenIVerifyTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

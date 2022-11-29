package stepsdefinition.CreateSaving;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
public class CheckInvalidURLAndMethod {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	
  @Given("I want to create a saving")
  public void givenIWantToCreateASaving() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with: url: {string} and method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  String requestBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateSaving\\CreateSavingData.json");
	  if(method.equals("POST")) {
		  response=apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
	  }
	  else {
		  response=apiUtils.sendGETRequestWithToken(url, token);
	  }
  }

  @Then("I verify the status {string} and response message {string}")
  public void thenIVerifyTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "error");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

package stepsdefinition.CreateSaving;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidToken {
	HttpResponse<String> response;
	String token;
	String url = "http://localhost:8080/api/v1/transaction/saving";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
  @Given("I want to create saving")
  public void givenIWantToCreateSaving() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon()+"123";
  }

  @When("I send the request but using invalid or expried token")
  public void whenISendTheRequest() throws Throwable {
	  String requestBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateSaving\\CreateSavingData.json");
	  response= apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
  }

  @Then("I should get the status code '401' and message 'Expired or invalid JWT token'")
  public void thenIVerifyTheResult() throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals("401", actualStatusCode);
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
  }

}

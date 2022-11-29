package stepsdefinition.CreateCustommerAccount;

import java.net.http.HttpResponse;

import org.junit.Assert;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;


public class CheckInvalidTokenStep {
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	HttpResponse<String> response01;
	String url= "http://localhost:8080/api/v1/auth/create-customer-account";
	String token;
  @Given("I want to create an account")
  public void givenIWantToCreateAnUserAccount() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I make the request with invalid token")
  public void whenIMakeTheRequestWithInvalidToken() throws Throwable {
	  String jsonBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateCustomerAccount\\CreateCustomerAccountData.json");
	 String token01=token+"839!k";
	  response01=apiUtils.sendPOSTRequestWithToken(url, jsonBody, token01);

  }

  @Then("There should be a warning message")
  public void thenThereShouldBeAWarningMessage() throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response01.body(), "message");
	  String actualStatusCode=Integer.toString(response01.statusCode());
	  Assert.assertEquals("Expired or invalid JWT token", actualMessage);
	  Assert.assertEquals("401", actualStatusCode);

  }

}

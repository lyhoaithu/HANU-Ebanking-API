package stepsdefinition.CreateCustommerAccount;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidURLStep{
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	HttpResponse<String> response01;
	String url="http://localhost:8080/api/v1/auth/create-customer-accountsad";
	String token;
  @Given("I want to create a customer account")
  public void givenIWantToCreateACustomerAccount() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request using invalid URL")
  public void whenISendTheRequestUsingInvalidURL() throws Throwable {
	  String jsonBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateCustomerAccount\\CreateCustomerAccountData.json");
	  response01=apiUtils.sendPOSTRequestWithToken(url, jsonBody, token);
	  
  }

  @Then("I should not be able to create the account")
  public void thenIShouldNotBeAbleToCreateTheAccount() throws Throwable {
	  String actualMessage= jsonUtils.getDataByKey(response01.body(), "error");
	  String actualStatusCode= Integer.toString(response01.statusCode());
	  Assert.assertEquals("404", actualStatusCode);
	  Assert.assertEquals("Not Found", actualMessage);
  }

}

package stepsdefinition.CreateCustommerAccount;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;
public class CheckInvalidMethodStep{
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	HttpResponse<String> response01;
	String url="http://localhost:8080/api/v1/auth/create-customer-account";
	String token;
  @Given("I am a user and want to create an account")
  public void givenIWantToCreateACustomerAccount() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request using the GET method")
  public void whenISendTheRequestWithGETMethod() throws Throwable {
	  String responseBody=jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateCustomerAccount\\CreateCustomerAccountData.json");
	  response01=apiUtils.sendGETRequestWithToken(url,token);
  }

  @Then("I check the result")
  public void thenICheckTheResult() throws Throwable {
	  String actualMessage=jsonUtils.getDataByKey(response01.body(), "error");
	  String actualStatusCode= Integer.toString(response01.statusCode());
	  Assert.assertEquals("Method Not Allowed", actualMessage);
	  Assert.assertEquals("405", actualStatusCode);
  }

}

package stepsdefinition.CreateCustommerAccount;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

import java.io.File;
import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.And;

public class FieldsValidationsStep{
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	HttpResponse<String> response01=null;
	String token;
	String url= "http://localhost:8080/api/v1/auth/create-customer-account";
  @Given("I want to create an user account")
  public void givenIWantToCreateAnUserAccount() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I enter value {string} in the field {string}")
  public void whenIEnterValueInTheField(String value, String field) throws Throwable {
	  File rootFile= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateCustomerAccount\\CreateCustomerAccountData.json");
	  File copyFile= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateCustomerAccount\\CreateCustomerAccountDataCopy.json");
	  jsonUtils.copyJSONFile(rootFile, copyFile);
	  String jsonBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateCustomerAccount\\CreateCustomerAccountDataCopy.json");
	 String jsonBodyChanged= jsonUtils.changeValueByFieldName(copyFile, field, value);
	  response01=apiUtils.sendPOSTRequestWithToken(url, jsonBodyChanged, token);
  }


  @Then("The response code should be {string} and the return message should be {string}")
  public void thenTheResponseCodeAndTheReturnMessageShouldBeCorrect(String statusCode, String expectedMessage) throws Throwable {
	  
	  String actualMessage= jsonUtils.getDataByKey(response01.body(), "message");
	  String actualStatusCode=Integer.toString(response01.statusCode());
	  Assert.assertEquals(expectedMessage, actualMessage);
	  Assert.assertEquals(statusCode, actualStatusCode);
	  
  }


}

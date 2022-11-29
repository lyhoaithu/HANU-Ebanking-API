package stepsdefinition.CreateLoan;

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
	String url="http://localhost:8080/api/v1/transaction/loan";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	
  @Given("I want to create a loan.")
  public void givenIWantToCreateALoan() throws Throwable {
	  LogInPreCondition logIn= new LogInPreCondition();
	  token= logIn.PreCon();
  }

  @When("I create loan with information: {string}: {string}")
  public void whenISendTheRequest(String key, String value) throws Throwable {
	  File original= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateLoan\\CreateLoanData.json");
	  File destination= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateLoan\\CreateLoanDataCopy.json");
	  jsonUtils.copyJSONFile(original, destination);
	  String requestBody=jsonUtils.readJsonFile(destination.getAbsolutePath());
	 requestBody=jsonUtils.changeValueByFieldName(destination, key, value);
	 response=apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
  }

  @Then("I verify the result. Expected: {string} and {string}")
  public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  if(actualMessage.contains("JSON")) {
		  actualMessage=jsonUtils.getDataByKey(response.body(), "error");
	  }
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
	  Assert.assertEquals(expectedMessage, actualMessage);
  }

}

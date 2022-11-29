package stepsdefinition.CreateAdminAccount;

import java.io.File;
import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import io.cucumber.java.en.Then;

public class FieldsValidation {
	HttpResponse<String> response;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String url="http://localhost:8080/api/v1/auth/create";
  @Given("I want to create the account for admin")
  public void givenIWantToCreateTheAccountForAdmin() throws Throwable {
  }

  @When("I enters value in the field {string} with value {string} and send the request")
  public void whenIEnterValuesAndSendTheRequest(String fieldName, String value) throws Throwable {
	  File rootFile= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateAdminAccount\\CreateAdminAccountData.json");
	  File copyFile= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateAdminAccount\\CreateAdminAccountDataCopy.json");
	  jsonUtils.copyJSONFile(rootFile, copyFile);
	  String jsonBodyChange= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateAdminAccount\\CreateAdminAccountDataCopy.json");
	  String jsonBody= jsonUtils.changeValueByFieldName(copyFile, fieldName, value);
	  response=apiUtils.sendPOSTRequest(url, jsonBody);
	  System.out.println(jsonBody);
	  
  }

  @Then("The status code should be {string} and message should be {string}")
  public void thenTheStatusCodeAndMessageShouldBeAsExpected(String expectedCode, String expectedMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualWarningMessage;
	  if (actualStatusCode.equals("500")) {
		  actualWarningMessage= jsonUtils.getDataByKey(response.body(), "error");
	  }
	  else {
	  actualWarningMessage= jsonUtils.getDataByKey(response.body(), "message");
	  }
	  Assert.assertEquals(expectedCode, actualStatusCode);
		 Assert.assertEquals(expectedMessage, actualWarningMessage);
  }

}

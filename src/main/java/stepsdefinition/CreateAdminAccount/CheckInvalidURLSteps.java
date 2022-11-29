package stepsdefinition.CreateAdminAccount;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import io.cucumber.java.en.Then;

public class CheckInvalidURLSteps {
	HttpResponse<String> response;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to create an admin account")
  public void givenIWantToCreateAnAdminAccount() throws Throwable {
  }

  @When("I send the request using the invalid url {string}")
  public void whenISendTheRequestUsingTheInvalidURL(String url) throws Throwable {
	  String jsonBody= jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateAdminAccount\\CreateAdminAccountData.json");
	  response=apiUtils.sendPOSTRequest(url, jsonBody);
  }

  @Then("The status code should be {string} and there should be a warning message saying {string}")
  public void thenIValidateTheStatusCodeAndResponseMessage(String expectedStatusCode, String expectedWarningMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "error");
	 Assert.assertEquals(expectedStatusCode, actualStatusCode);
	 Assert.assertEquals(expectedWarningMessage, actualMessage);
  }

}

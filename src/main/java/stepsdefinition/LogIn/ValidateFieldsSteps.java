package stepsdefinition.LogIn;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import io.cucumber.java.en.Then;

import java.io.File;
import java.net.http.HttpResponse;

import org.junit.Assert;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.And;
public class ValidateFieldsSteps {
	String url="http://localhost:8080/api/v1/auth/login";
	HttpResponse<String> response =null;
	APIUtils apiUtils= new APIUtils();
	  JSONUtils jsonUtils= new JSONUtils();
  @Given("I want to log in the system")
  public void givenIWantToLogInTheSystem() throws Throwable {
  }

  @When("I fill the field {string} with value {string} and send the request")
  public void whenIEnterValuesInFields(String field, String value) throws Throwable {
	  APIUtils apiUtils= new APIUtils();
	  JSONUtils jsonUtils= new JSONUtils();
	  File rootFile= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\LogIn\\LogInData.json");
	  File copyFile= new File("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\LogIn\\LogInDataCopy.json");
	  jsonUtils.copyJSONFile(rootFile, copyFile);
	  String jsonBody= jsonUtils.readJsonFile(copyFile.getAbsolutePath());
	  String resultFile=jsonUtils.changeValueByFieldName(copyFile, field, value);
	 response= apiUtils.sendPOSTRequest(url, resultFile);
  }

  @Then("The status code should be {string} and the message should be {string}")
  public void thenTheStatusCodeAndMessageShouldBe(String statusCode, String expectedMessage) throws Throwable {
	  APIUtils apiUtils= new APIUtils();
	  JSONUtils jsonUtils= new JSONUtils();
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response.body(), "message");
	  Assert.assertEquals(actualStatusCode, statusCode);
	  Assert.assertEquals(actualMessage, expectedMessage);
  }

}

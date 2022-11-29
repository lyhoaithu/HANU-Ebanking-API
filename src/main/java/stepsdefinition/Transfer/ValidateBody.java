package stepsdefinition.Transfer;

import java.io.File;
import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class ValidateBody {
	HttpResponse<String> response;
	String token;
	String url = "http://localhost:8080/api/v1/transaction/transfer";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();

	@Given("I want to make a transaction")
	public void givenIWantToMakeATransaction() throws Throwable {
		LogInPreCondition preCon = new LogInPreCondition();
		token = preCon.PreCon();
	}

	@When("I send the request with: key {string} and value {string}")
	public void whenISendTheRequest(String key, String value) throws Throwable {
		File original = new File(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\Transfer\\TransferData.json");
		File destination = new File(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\Transfer\\TransferDataCopy.json");
		jsonUtils.copyJSONFile(original, destination);
		String requestBody = jsonUtils.readJsonFile(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\Transfer\\TransferDataCopy.json");
		requestBody= jsonUtils.changeValueByFieldName(destination, key, value);
		response = apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
	}

	@Then("I validate the outcomes. Expected: statusCode {string}, message {string}")
	public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
		String actualStatusCode = Integer.toString(response.statusCode());
		String actualMessage = jsonUtils.getDataByKey(response.body(), "message");
		Assert.assertEquals(expectedStatusCode, actualStatusCode);
		Assert.assertEquals(expectedMessage, actualMessage);
	}

}

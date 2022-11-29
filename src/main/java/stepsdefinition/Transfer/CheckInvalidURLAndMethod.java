package stepsdefinition.Transfer;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidURLAndMethod {
	HttpResponse<String> response;
	String token;
	String url = "http://localhost:8080/api/v1/transaction/transfer";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();

	@Given("I want to transfer the money")
	public void givenIWantToTransferTheMoney() throws Throwable {
		LogInPreCondition preCon = new LogInPreCondition();
		token = preCon.PreCon();
	}

	@When("I send the request: url {string} with method {string}")
	public void whenISendTheRequest(String url, String method) throws Throwable {
		String responseBody = jsonUtils.readJsonFile(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\Transfer\\TransferData.json");
		if (method.equals("POST")) {
			response = apiUtils.sendPOSTRequestWithToken(url, responseBody, token);
		} else {
			response = apiUtils.sendGETRequestWithToken(url, token);
		}

	}

	@Then("I validate the outcomes. Expected: statusCode {string} and message {string}")
	public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
		String actualStatusCode = Integer.toString(response.statusCode());
		String actualMessage = jsonUtils.getDataByKey(response.body(), "message");
		Assert.assertEquals(expectedStatusCode, actualStatusCode);
		Assert.assertEquals(expectedMessage, actualMessage);
	}

}

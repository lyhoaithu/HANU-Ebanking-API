package stepsdefinition.Transfer;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidToken {
	HttpResponse<String> response;
	String token;
	String url = "http://localhost:8080/api/v1/transaction/transfer";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();

	@Given("I want to make a transfer")
	public void givenIWantToMakeATransfer() throws Throwable {
		LogInPreCondition preCon = new LogInPreCondition();
		token = preCon.PreCon() + "123";
	}

	@When("I send the request using wrong token value")
	public void whenISendTheRequest() throws Throwable {
		String requestBody = jsonUtils.readJsonFile(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\Transfer\\TransferData.json");
		response = apiUtils.sendPOSTRequestWithToken(url, token, requestBody);
	}

	@Then("The status code is expected to be '401' with the message 'Expired or invalid JWT token'")
	public void thenIValidateTheResult() throws Throwable {
		String actualStatusCode = Integer.toString(response.statusCode());
		String actualMessage = jsonUtils.getDataByKey(response.body(), "message");
		Assert.assertEquals("401", actualStatusCode);
		Assert.assertEquals("Expired or invalid JWT token", actualMessage);
	}

}

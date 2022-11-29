package stepsdefinition.CreateSaving;

import java.io.File;
import java.net.http.HttpResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class FieldsValidation {
	HttpResponse<String> response;
	String token;
	String url = "http://localhost:8080/api/v1/transaction/saving";
	APIUtils apiUtils = new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();

	@Given("I want to create a saving.")
	public void givenIWantToCreateASaving() throws Throwable {
		LogInPreCondition preCon = new LogInPreCondition();
		token = preCon.PreCon();
	}

	@When("I send the request with: key {string}: {string} and key {string}: {string}")
	public void whenISendTheRequest(String key1, String value1, String key2, String value2) throws Throwable {
		File original = new File(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateSaving\\CreateSavingData.json");
		File destination = new File(
				"D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateSaving\\CreateSavingDataCopy.json");
		jsonUtils.copyJSONFile(original, destination);
		// String requestRead=
		// jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\CreateSaving\\CreateSavingDataCopy.json");
		String requestBody;
		if (key2.equals("null")) {
			requestBody = jsonUtils.changeValueByFieldName(destination, key1, value1);

		} else {
			requestBody = jsonUtils.changeValueByFieldName(destination, key1, value1);
			requestBody = jsonUtils.changeValueByFieldName(destination, key2, value2);
		}
		response = apiUtils.sendPOSTRequestWithToken(url, requestBody, token);
	}

	@Then("I expect to recieve status code: {string} and response message: {string}")
	public void thenIValidateTheResult(String expectedStatusCode, String expectedMessage) throws Throwable {
		String actualStatusCode = Integer.toString(response.statusCode());
		Assert.assertEquals(expectedStatusCode, actualStatusCode);
		String actualMessage = null;
		if (!actualStatusCode.equals("200")) {
			actualMessage = jsonUtils.getDataByKey(response.body(), "message");
			if(actualMessage.contains("JSON")) {
				actualMessage=jsonUtils.getDataByKey(response.body(), "error");
			}
		}
		else {
			String message;
			message = jsonUtils.getDataByKey(response.body(), "message");
			JSONParser parser = new JSONParser();
			JSONObject responseJsonObj = (JSONObject) parser.parse(response.body());
			JSONObject data = (JSONObject) responseJsonObj.get("data");
			String amount = data.get("amount").toString();
			actualMessage = message +", "+ amount;
			String balanceBefore = data.get("balanceBefore").toString();
			String balanceAfter = data.get("balanceAfter").toString();
			double amountDouble = Double.parseDouble(amount);
			double balanceBeforeDouble = Double.parseDouble(balanceBefore);
			double balanceAfterDouble = Double.parseDouble(balanceAfter);
			boolean calculate = false;
			if (amountDouble == balanceBeforeDouble - balanceAfterDouble) {
				calculate = true;
			}
			Assert.assertEquals(calculate, true);
		}
//		System.out.println(actualMessage);
Assert.assertEquals(expectedMessage, actualMessage);
	}
}

package stepsdefinition.GetProfile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import org.json.simple.parser.ParseException;
import org.junit.Assert;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidMethodStep{
	
	//LogIn to get Token
	String url="http://localhost:8080/api/v1/user/me";
	public   HttpResponse<String> response01;
	String token;
	
	
  @Given("I want to view my profile information")
  public void givenIWantToViewMyProfileInformation() throws IOException, ParseException, URISyntaxException, InterruptedException {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request with invalid method")
  public void whenISendTheRequestWithInvalidMethod() throws Throwable {
		APIUtils apiUtils= new APIUtils();
		JSONUtils jsonUtils = new JSONUtils();
		String jsonBody  = jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\LogIn\\LogInData.json");
		response01= apiUtils.sendPOSTRequestWithToken(url, jsonBody, token);
  }

  @Then("I validate the status code and the response message")
  public void thenIValidateTheStatusCodeAndTheResponseMessage() throws Throwable {
	  JSONUtils jsonUtils = new JSONUtils();
	  String actualStatusCode= Integer.toString(response01.statusCode());
	  String actualMessage= jsonUtils.getDataByKey(response01.body(), "error");
	 Assert.assertEquals("Method Not Allowed", actualMessage);
	 Assert.assertEquals("405", actualStatusCode);
  }

}

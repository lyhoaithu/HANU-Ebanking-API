package stepsdefinition.GetUserByID;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class CheckInvalidAccountID {
	HttpResponse<String> response;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String token;
	String url="http://localhost:8080/api/v1/user/";
  @Given("I want to get an user profile by user ID")
  public void givenIWantToGetAnUserProfile() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();
  }

  @When("I send the request with invalid account ID {string}")
  public void whenISendTheRequestWithInvalidAccountID(String accountID) throws Throwable {
	  String urlWithID= url+ accountID;
	  response= apiUtils.sendGETRequestWithToken(urlWithID, token);
  }

  @Then("I validate the response code {string} and warning message {string} of the {string}")
  public void thenIValidateTheResult(String statusCode, String responseMessage, String accountID) throws Throwable {
	  String actualMessage=null;
	 if(accountID.equals("30")) {
		 actualMessage=jsonUtils.getDataByKey(response.body(), "message");
		
	 }
	 else {
		 actualMessage=jsonUtils.getDataByKey(response.body(), "error");
	 }
	
	  String actualStatusCode= Integer.toString(response.statusCode());
	  Assert.assertEquals(statusCode, actualStatusCode);
	  Assert.assertEquals(responseMessage,actualMessage );
	
  }

}

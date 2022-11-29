package stepsdefinition.CreateAdminAccount;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import io.cucumber.java.en.Then;

public class CheckInvalidMethodSteps {
	HttpResponse<String> response;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String url="http://localhost:8080/api/v1/auth/create";
  @Given("I want to create an account for admin")
  public void givenIWantToCreateAnAccountForAdmin() throws Throwable {
  }

  @When("I send the request using the invalid method")
  public void whenISendTheRequestUsingTheInvalidMethod() throws Throwable {
	  response= apiUtils.sendGETRequest(url);
  }

  @Then("The status code should be {string} and the warning message should be {string}")
  public void thenIValidateTheStatusCodeAndResponseMessage(String expectedStatusCode, String expectedWarningMessage) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  String actualWarningMessage= jsonUtils.getDataByKey(response.body(), "error");
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
		 Assert.assertEquals(expectedWarningMessage, actualWarningMessage);
  }

}

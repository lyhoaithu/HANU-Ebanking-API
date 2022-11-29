package stepsdefinition.GetProfile;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import org.json.simple.parser.ParseException;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;


public class CheckInvalidURLStep{
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils = new JSONUtils();
	HttpResponse<String> response01;
	String url= "http://localhost:8080/api/v1/user/meqq";
	String token;
  @Given("I have logged in the system")
  public void givenIHaveLoggedInTheSystem() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request with invalid URL")
  public void whenISendTheRequestWithInvalidURL() throws Throwable {
	 response01= apiUtils.sendGETRequestWithToken(url, token);
	 
	
  }

  @Then("I validate the outcomes")
  public void thenIValidateTheOutcomes() throws Throwable {
  String actualMessage= jsonUtils.getDataByKey(response01.body(), "error");
  String actualStatusCode= Integer.toString(response01.statusCode());
  Assert.assertEquals("Bad Request", actualMessage);
  Assert.assertEquals("400", actualStatusCode);
  }
  

}

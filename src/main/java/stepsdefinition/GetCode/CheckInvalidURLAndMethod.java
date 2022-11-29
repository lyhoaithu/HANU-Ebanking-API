package stepsdefinition.GetCode;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;

public class CheckInvalidURLAndMethod {
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	HttpResponse<String> response01;
	String token;
  @Given("I want to get the code")
  public void givenIWantToGetTheCode() throws Throwable {
	  LogInPreCondition preCon=new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request using the url {string} and method {string}")
  public void whenISendTheRequest(String url, String method) throws Throwable {
	  if(method.equalsIgnoreCase("GET")) {
		  response01=apiUtils.sendGETRequest(url);
	  }
	  else if(method.equalsIgnoreCase("DELETE")) {
		  response01=apiUtils.sendDELETERequest(url);
	  }
  }

  @Then("The response should have status code {string} and message {string}")
  public void thenICheckTheResult(String expectedStatusCode, String expectedResponseMessage) throws Throwable {
	  String actualMessage=jsonUtils.getDataByKey(response01.body(), "error");
	  String actualStatusCode= Integer.toString(response01.statusCode());
	  Assert.assertEquals(expectedResponseMessage, actualMessage);
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
  }

}

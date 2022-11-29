package stepsdefinition.LogIn;

import java.net.HttpURLConnection;
import java.net.URL;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import io.cucumber.java.en.Then;

public class CheckInvalidMethodOrURLSteps {
	
	//Check Invalid Method
	
	HttpResponse<String> response=null;
  @Given("User wants to log in the system")
  public void givenUserWantsToLogInTheSystem(){ 
  }

  @When("User sends request using the url {string} with invalid method {string}")
  public void whenUserSendsRequest(String url, String method) throws Throwable {
	  APIUtils apiUtils= new APIUtils();
	  JSONUtils jsonUtils= new JSONUtils();
	 response= apiUtils.sendGETRequest(url);
	  
  }

  @Then("The response status code should be {string}")
  public void thenCheckTheStatusCode(String statusCode){
	  int actualStatusCode= response.statusCode();
	  int expectedStatusCode= Integer.parseInt(statusCode);
	  Assert.assertEquals(expectedStatusCode, actualStatusCode);
  }
  

  //Check Invalid URL
  @Given("User wants to login the system")
  public void givenUserWantsToLogIn(){
	
  }

  @When("User sends request using the invalid url {string} with valid method {string}")
  public void whenUserSendsTheRequest(String url, String method) throws Throwable {
	  APIUtils apiUtils= new APIUtils();
	  JSONUtils jsonUtils= new JSONUtils();
	  String requestBody=jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\LogIn\\LogInData.json");
	  response = apiUtils.sendGETRequest(url);
	 
  }

  @Then("The status code of the response should be {string}")
  public void thenVerifyTheStatusCode(String statusCode){
	  int actualStatusCode= response.statusCode();
		 int expectedStatusCode= Integer.parseInt(statusCode);
		 Assert.assertEquals(expectedStatusCode, actualStatusCode);
  }
		  
  }



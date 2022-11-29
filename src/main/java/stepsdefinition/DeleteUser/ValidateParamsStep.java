package stepsdefinition.DeleteUser;

import java.net.http.HttpResponse;

import common.APIUtils;
import common.JSONUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class ValidateParamsStep {
	HttpResponse<String> response;
	String token;
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	String url="http://localhost:8080/api/v1/user/";
  @Given("I want to delete a particular user")
  public void givenIWantToDeleteAParticularUser() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token=preCon.PreCon();
  }

  @When("I send the request with user id {string}")
  public void when(String id) throws Throwable {
	  response= apiUtils.sendDELETEWithToken(url+id, token);
	  
  }

  @Then("I check the result of the response {string} {string}")
  public void then() throws Throwable {
  }

}

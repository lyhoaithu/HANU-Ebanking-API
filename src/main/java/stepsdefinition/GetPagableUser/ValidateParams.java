package stepsdefinition.GetPagableUser;

import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import common.APIUtils;
import common.JSONUtils;
import common.URLUtils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.When;
import junit.framework.Assert;
import stepsdefinition.LogInPreCondition;
import io.cucumber.java.en.Then;

public class ValidateParams {
	HttpResponse<String> response;
	String token;
	String url="http://localhost:8080/api/v1/user?page=0&size=5&sort=id,asc";
	APIUtils apiUtils= new APIUtils();
	JSONUtils jsonUtils= new JSONUtils();
	URLUtils urlUtils= new URLUtils();
	String statusCode; String message; String numberOfElements; String IDList;
  @Given("I want to get the number of user in one page")
  public void givenIWantToGetPageableUser() throws Throwable {
	  LogInPreCondition preCon= new LogInPreCondition();
	  token= preCon.PreCon();

//	  for(Map<String,String> columnData:dataTable) {
//		  statusCode=columnData.get("StatusCode");
//		  message=columnData.get("message");
//		  numberOfElements=columnData.get("numberOfElements");
//		  IDList=columnData.get("IDList");
//	  }
	  
  }

  @When("I send the request with url and param {string} with value {string}")
  public void whenISendTheRequest(String param, String value) throws Throwable {
	String URL= urlUtils.changeURL(url, param, value);
	System.out.println(URL);
	response=apiUtils.sendGETRequestWithToken(URL, token);
  }

  @Then("I validate the response outcomes. Expected results: {string}, {string}, {string}, {string}")
  public void thenIValidateTheResult(String statusCode, String message, String numberOfElements, String IDList) throws Throwable {
	  String actualStatusCode= Integer.toString(response.statusCode());
	  
	  String actualMessage=jsonUtils.getDataByKey(response.body(), "message");
	  if(message.equalsIgnoreCase("success")) {
	  //Get nested value
	  JSONParser parser= new JSONParser();
	  JSONObject responseJsonObj= (JSONObject) parser.parse(response.body());
	  
	  //Lấy được data r mới lấy được number of elements vì nó là tập con của data
	 
	  JSONObject data= (JSONObject) responseJsonObj.get("data");
	String actualNumberOfElements= data.get("numberOfElements").toString();
	List<String> ID= new ArrayList<>();
	if(actualNumberOfElements.equals("5")) {
		
		//Lấy Jsonarray tên là content
		JSONArray content= (JSONArray) data.get("content");
		
		//Extract array
		for(int i=0; i<content.size();i++) {
			 JSONObject contentObj = (JSONObject) content.get(i);
			 
			 //Lấy giá trị ID
			    String id = contentObj.get("id").toString();
			 ID.add(id);
		}
		String IDStr= ID.toString();
		Assert.assertEquals(IDStr, IDList);
	}
	Assert.assertEquals(actualNumberOfElements,numberOfElements);
	  }
	Assert.assertEquals(statusCode, actualStatusCode);
	Assert.assertEquals(message, actualMessage);
	
//	  System.out.println(actualStatusCode+" "+ statusCode);
//	  System.out.println(actualMessage+" "+ message);
//	  System.out.println(actualNumberOfElements);
  }

}

package stepsdefinition;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.http.HttpResponse;

import org.json.simple.parser.ParseException;

import common.APIUtils;
import common.JSONUtils;

public class LogInPreCondition {
	public static String url="http://localhost:8080/api/v1/auth/login";
	public static String token;
	public static HttpResponse<String> response;
	public String PreCon() throws IOException, ParseException, URISyntaxException, InterruptedException {
		APIUtils apiUtils= new APIUtils();
		JSONUtils jsonUtils = new JSONUtils();
		String jsonBody  = jsonUtils.readJsonFile("D:\\AutomationTest\\02Projects\\HANUBankingAPIAutomation\\src\\main\\resources\\LogIn\\LogInData.json");
	 response= apiUtils.sendPOSTRequest(url, jsonBody);
	 token = jsonUtils.getDataByKey(response.body(), "data");	
	 return token;
	}
}

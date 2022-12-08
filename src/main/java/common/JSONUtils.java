package common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import io.cucumber.messages.JSON;

public class JSONUtils {
public String getDataByKey(String responseBody, String keyword) throws ParseException {
	JSONParser parser= new JSONParser();
	JSONObject responseBodyJsonObj= (JSONObject) parser.parse(responseBody);
	String data=responseBodyJsonObj.get(keyword).toString();
	return data;
}

public String readJsonFile(String filePath) throws IOException, ParseException {
	File file= new File(filePath);
	FileReader reader = new FileReader(file);
	JSONParser parser= new JSONParser();
	JSONObject responseBodyJsonObj= (JSONObject) parser.parse(reader);
	String jsonBody= responseBodyJsonObj.toJSONString();
	reader.close();
	return jsonBody;
}

public void copyJSONFile(File rootFile, File copyFile) throws IOException {
	 if(copyFile.exists()) {
		 copyFile.delete();
	  }
	  try {
	  Files.copy(rootFile.toPath(), copyFile.toPath());
	  System.out.println("Copy successfully");
	  } catch(Exception e) {
		  System.out.println("Json request body is not found");
	  }
}

public String changeValueByFieldName(File file, String fieldName, String value) throws IOException, ParseException {
	FileReader reader= new FileReader(file);
	JSONParser parser= new JSONParser();
	JSONObject jsonObj= (JSONObject) parser.parse(reader);
	if(value.equals("null")) {
		jsonObj.put(fieldName, "");
	}
	else if(value.equals("missing")) {
		jsonObj.remove(fieldName);
	}
	else
		jsonObj.put(fieldName, value);

String resultFile= jsonObj.toJSONString();
return resultFile;
}

public String checkErrorMessage(String responseBody) {
	String responseBodyCheck="";
	  for (int i =0; i<responseBody.length();i++) {
		  char character=responseBody.charAt(i);
		  if(character!='"') {
			  responseBodyCheck+=character;
		  }
	  }
	  return responseBodyCheck;	
}

}

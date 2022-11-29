package common;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Scanner;

import org.apache.http.client.utils.URIBuilder;



public class URLUtils {

public String changeURL(String originalURL, String param, String value) throws URISyntaxException {
	URIBuilder urlBuilder = new URIBuilder(originalURL);
	URI url=null;
	String URL;
	if(value.equals("null")) {
		urlBuilder.setParameter(param, "");
		url= urlBuilder.build();
		URL=url.toString();
	}
	
	//delete parameter
	else if(value.equals("missing")) {
		urlBuilder.setParameter(param, "");
		url= urlBuilder.build();
	URL=url.toString().replace("&"+param+"=", "");
	}
	
	//Chèn giá trị khác
	else {
		urlBuilder.setParameter(param, value);
		url= urlBuilder.build();
		URL=url.toString();
	}
	return URL;
}

}


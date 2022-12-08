package common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;


public class APIUtils {
	public HttpResponse<String> sendPOSTRequest(String url, String jsonRequestBody)
			throws URISyntaxException, IOException, InterruptedException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url)).header("Content-Type", "application/json")
				.POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody)).build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;
	}

	public HttpResponse<String> sendGETRequest(String url)
			throws IOException, InterruptedException, URISyntaxException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url)).header("Content-Type", "application/json")
				.GET().build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;

	}

	public HttpResponse<String> sendDELETERequest(String url) throws IOException, InterruptedException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url)).header("Content-Type", "application/json")
				.DELETE().build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;

	}

	public HttpResponse<String> sendPUTWithToken(String url, String jsonRequestBody, String token)
			throws IOException, InterruptedException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder()
				.uri(URI.create(url)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token)
				.PUT(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
				.build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;
	}

	public HttpResponse<String> sendGETRequestWithToken(String url, String token)
			throws IOException, InterruptedException, URISyntaxException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token).GET().build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;
	}

	public HttpResponse<String> sendPOSTRequestWithToken(String url, String jsonRequestBody, String token)
			throws URISyntaxException, IOException, InterruptedException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token).POST(HttpRequest.BodyPublishers.ofString(jsonRequestBody))
				.build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;
	}

	public HttpResponse<String> sendDELETEWithToken(String url, String token) throws IOException, InterruptedException {
		HttpResponse<String> response = null;
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url))
//			.header("Content-Type", "application/json")
				.header("Authorization", "Bearer " + token).DELETE().build();
		response = HttpClient.newHttpClient().send(request, BodyHandlers.ofString());
		return response;
	}

	public String[] sendPOSTWithTokenAndDataForm(String url, String token, String[][] keysAndValues)
			throws IOException, InterruptedException {
		// Set up client
		CloseableHttpClient client = HttpClients.createDefault();

		// Set up response
		CloseableHttpResponse response;

		// Set up request
		HttpPost post = new HttpPost(url);
		post.setHeader("Authorization", "Bearer " + token);

		// Set params
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (int i = 0; i < keysAndValues.length; i++) {
			builder.addTextBody(keysAndValues[i][0], keysAndValues[i][1], ContentType.MULTIPART_FORM_DATA); // Không cần
																											// set
																											// header vì
																											// ở đây đã
																											// có sẵn
																											// contentType

		}
		// Không cần set header vì ở đây đã có sẵn contentType
		HttpEntity multipart = builder.build();
		post.setEntity(multipart);
		

		response = client.execute(post);

		// Đọc response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder stringBuilder = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			stringBuilder.append(line);
		}
		String[] result = { response.getStatusLine().toString().substring(9, 12), stringBuilder.toString() };
		return result;
	}

	public String[] sendPATCHWithTokenAndDataForm(String url, String token, String[][] keysAndValues)
			throws IOException, InterruptedException {
		// Set up client
		CloseableHttpClient client = HttpClients.createDefault();

		// Set up response
		CloseableHttpResponse response;

		// Set up request
		HttpPatch patch = new HttpPatch(url);
		patch.setHeader("Authorization", "Bearer " + token);

		// Set params
		MultipartEntityBuilder builder = MultipartEntityBuilder.create();
		for (int i = 0; i < keysAndValues.length; i++) {
			builder.addTextBody(keysAndValues[i][0], keysAndValues[i][1], ContentType.MULTIPART_FORM_DATA); // Không cần
																											// set
																											// header vì
																											// ở đây đã
																											// có sẵn
																											// contentType

		}
		// Không cần set header vì ở đây đã có sẵn contentType
		HttpEntity multipart = builder.build();
		patch.setEntity(multipart);

		response = client.execute(patch);
		// Đọc response
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
		StringBuilder stringBuilder = new StringBuilder();
		String line = "";
		while ((line = rd.readLine()) != null) {
			stringBuilder.append(line);
		}
		String[] result = { response.getStatusLine().toString().substring(9, 12), stringBuilder.toString() };
		return result;
	}

}

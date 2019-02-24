package grupo.uno.quickiz.command;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class HttpRequest {

	private String server = "https://run.glot.io/languages/python/latest";
	private RestTemplate rest;
	private HttpHeaders headers;
	private HttpStatus status;
	
	public HttpRequest() {
		this.rest = new RestTemplate();
	    this.headers = new HttpHeaders();
	    headers.add("Content-Type", "application/json");
	    headers.add("Authorization", "Token 8892fadb-8c0d-4c36-a5b0-194caa1f4666");
	}
	
	public String post(String json) {   
	    HttpEntity<String> requestEntity = new HttpEntity<String>(json, headers);
	    ResponseEntity<String> responseEntity = rest.exchange(server, HttpMethod.POST, requestEntity, String.class);
	    this.setStatus(responseEntity.getStatusCode());
	    return responseEntity.getBody();
	}
	
	public HttpStatus getStatus() {
	    return status;
	}

	public void setStatus(HttpStatus status) {
	    this.status = status;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public RestTemplate getRest() {
		return rest;
	}

	public void setRest(RestTemplate rest) {
		this.rest = rest;
	}

	public HttpHeaders getHeaders() {
		return headers;
	}

	public void setHeaders(HttpHeaders headers) {
		this.headers = headers;
	} 
	
	
}
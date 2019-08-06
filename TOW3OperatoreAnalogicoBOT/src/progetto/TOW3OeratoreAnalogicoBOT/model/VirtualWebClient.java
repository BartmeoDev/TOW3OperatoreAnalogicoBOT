package progetto.TOW3OeratoreAnalogicoBOT.model;

import java.net.URL;

import org.apache.http.Header;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.HttpHeader;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.api.client.http.GenericUrl;
import com.google.api.client.http.HttpHeaders;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestFactory;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonObjectParser;
import com.google.api.client.json.gson.GsonFactory;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;


public class VirtualWebClient {
	
	static WebClient webClient;
	
	private static final GsonFactory gsonFactory = new GsonFactory();

	
	
	public VirtualWebClient() {
		
		try {
			webClient = new WebClient();
			webClient.getOptions().setThrowExceptionOnFailingStatusCode(false);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.getOptions().setJavaScriptEnabled(false);
			
		}	catch(Exception e) {
				System.out.println("aaaaaaaa non riesco a aprirti il webclient222");
				e.printStackTrace();
			}
		
		//webClient.getOptions().setThrowExceptionOnScriptError(false);
	}
	
	
	public static HtmlPage setPage(String url) {
		try {

			//WebRequest wr = new WebRequest (new URL (url));
			return webClient.getPage(url);
			
		}catch(Exception e) {
			System.out.print(e);
			return null;
		}	
	}
	
	public static String setHttpRequest(String url) {
		try {
			
			HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
			HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
			request.setParser(gsonFactory.createJsonObjectParser());
			HttpResponse response = request.execute();
			
			String json_string = response.parseAsString();
			response.disconnect();
			
			return json_string;
			
			
		}catch(Exception e) {
			System.out.println(url);
			System.out.println("SONO QUI, ERROREEEEEEE");
			System.out.print(e);
			return null;
		}
	}
	
	public static String setSimpletHttpRequest (String url) {
		try {
			
			HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
			HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
			HttpResponse response = request.execute();
			
			String html_string = response.parseAsString();
			response.disconnect();
			
			return html_string;
			
			
		}catch(Exception e) {
			System.out.println(url);
			System.out.println("SONO QUI, ERROREEEEEEE");
			System.out.print(e);
			return null;
		}	
		
	}
	
	
	public static String setPatentHttpRequest (String url, String auth) {
		try {
			
			HttpRequestFactory requestFactory = new NetHttpTransport().createRequestFactory();
			HttpRequest request = requestFactory.buildGetRequest(new GenericUrl(url));
			HttpHeaders headers = new HttpHeaders();
			headers.setAuthorization(auth);
			request.setHeaders(headers);
			
			
			HttpResponse response = request.execute();
			
			String html_string = response.parseAsString();
			response.disconnect();
			
			return html_string;
			
			
		}catch(Exception e) {
			System.out.println(url);
			System.out.println("SONO QUI, ERROREEEEEEE");
			System.out.print(e);
			return null;
		}	
		
	}
	

}

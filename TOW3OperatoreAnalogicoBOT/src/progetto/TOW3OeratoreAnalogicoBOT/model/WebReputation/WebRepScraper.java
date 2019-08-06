package progetto.TOW3OeratoreAnalogicoBOT.model.WebReputation;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import progetto.TOW3OeratoreAnalogicoBOT.model.VirtualWebClient;



public class WebRepScraper {
	
	private HtmlPage htmlPage;
	
	
	public WebRepScraper () {
				
	}	
	
	
	
	// PRENDE IN INPUT UNA RICERCA GOOGLE DI BASE, AGGIUNGE/RIMUOVE VINCOLI E ANALIZZA TUTTE LE PAGINE DELLA RICERCA
	public List<String> searchFor (String url) {
		
		List<String> links = new ArrayList<String>();
		final int limit = 20;
		String numero_risultati = "";
		//int n_res;

		
		links.addAll(this.getSinglePageLinks(url+"&filter=0"));
		
		DomNode result_node = (DomNode)htmlPage.getByXPath("//div[@id='resultStats']").get(0);
		numero_risultati = result_node.getTextContent();
		System.out.println(numero_risultati);
//		
//		StringTokenizer tokens = new StringTokenizer(numero_risultati);
//		String st = tokens.nextToken();
//		if (!st.startsWith("C")) n_res = Integer.parseInt(st);
//		else n_res = Integer.parseInt(tokens.nextToken());
//		
//		

		
		
		for (int i=10; i<limit; i+=10) {
			System.out.print(i);
			url += "&start=" + i + "&filter=0";
			links.addAll(this.getSinglePageLinks(url));			
		}
		
		for (String s : links) System.out.println(s);
		return links;
		
	}
	
	
	
	// PRENDE IN INPUT UN URL DI UNA RICERCA GOOGLE E RESTITUISCE LISTA DI DOMNODES TROVATI
	private List<String> getSinglePageLinks (String url) {
		
		htmlPage = VirtualWebClient.setPage(url);
		
		List<String> list_to_append = new ArrayList<String>();
		for (DomElement dom : htmlPage.getElementsByTagName("a")) {

			String link = dom.getAttribute("href");
			
			if (!link.contains("google.com") && 
					!link.contains("googleusercontent") && 
					!link.contains("google.it") &&  
					!link.contains("youtube.com") &&  
					!link.contains("blogger.com") && 
					(link.startsWith("https://") || link.startsWith("http://"))) {
				list_to_append.add(link);
			}			
			
			//System.out.println(dom.getAttribute("href"));
		}

		
		return list_to_append;
	}

}

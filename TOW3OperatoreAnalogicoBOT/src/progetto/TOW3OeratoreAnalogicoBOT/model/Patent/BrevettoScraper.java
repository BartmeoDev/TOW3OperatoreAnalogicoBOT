package progetto.TOW3OeratoreAnalogicoBOT.model.Patent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.commons.io.*;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebRequest;
import com.gargoylesoftware.htmlunit.WebWindow;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import progetto.TOW3OeratoreAnalogicoBOT.model.VirtualWebClient;

//import org.openqa.selenium.WebDriver;
//import org.openqa.selenium.chrome.ChromeDriver;


public class BrevettoScraper {
//	
//	static WebClient webClient;
//	private String pageURL;
	private HtmlPage htmlPage;
	
	public BrevettoScraper () {
		
		
	}
//	
//	
//	public BrevettoScraper() {
//		try {
//		webClient = new WebClient();
//		}catch(Exception e) {
//			System.out.println("aaaaaaaa non riesco a aprirti il webclient");
//			e.printStackTrace();
//		}
//	}
//		
		
	
//		---------------
//		System.setProperty("webdriver.chrome.driver", "/Users/scorl/Desktop/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.get("https://didattica.polito.it/portal/page/portal/home/Studente");
//		--------------------
		


	public void setPage(String url) {
		try {
			//this.pageURL = url;
			this.htmlPage = VirtualWebClient.setPage(url);
		}catch(Exception e) {
			e.printStackTrace();
		}	
	}
	
	/*
	public String extractTitle() {
		DomElement title = page.getElementByName("DC.title");	
		//		String title = page.getTitleText();
//				getHtmlElementById("title").asText();
		System.out.println(title.getAttribute("content"));
		return title.getAttribute("content");
	}
	*/
	
	public String getCurrentAssignee() {
		String assignee =null;

		try {
			DomNode assignee_node = (DomNode) htmlPage.getByXPath("//dd[@itemprop='assigneeCurrent']").get(0);
			assignee = assignee_node.getTextContent();
		}catch(Exception e) {
			}
		
		return assignee;
	}
	
		
	public Brevetto extractBrevetto(String partita_iva) {

		String title_text = htmlPage.getTitleText();
		StringTokenizer token_title = new StringTokenizer(title_text);
		String app_num = token_title.nextToken();

		String title = htmlPage.getElementByName("DC.title").getAttribute("content");

		String abstr = htmlPage.getElementByName("DC.description").getAttribute("content");
		
		String assignee =null;
		try {
			DomNode assignee_node = (DomNode) htmlPage.getByXPath("//dd[@itemprop='assigneeCurrent']").get(0);
			assignee = assignee_node.getTextContent();
		}	catch(Exception e) {
			}
		
		List<DomNode> inventor_nodes = new LinkedList<DomNode>();
		for (Object n: htmlPage.getByXPath("//dd[@itemprop='inventor']")) {
			inventor_nodes.add((DomNode)n);
		}
		String inventors ="";
		for (DomNode n: inventor_nodes) {
			inventors += n.getTextContent() +", ";
		}
		
		String data = null;
		try {
		DomNode data_node = (DomNode) htmlPage.getByXPath("//time[@itemprop='publicationDate']").get(0);
		data = data_node.getTextContent();
		}catch(Exception e) {
			System.out.println("dataaaaaa");
		}
		
		List<DomNode> key_nodes = new LinkedList<DomNode>();
		for (Object n: htmlPage.getByXPath("//dd[@itemprop='priorArtKeywords']")) {
			key_nodes.add((DomNode)n);
		}
		String keywords ="";
		for (DomNode n: key_nodes) {
			keywords += n.getTextContent() +", ";
		}
	
		
		Map<DomNode,DomNode> nations_map = new HashMap<DomNode,DomNode>();
		int i=0;
		for (Object n: htmlPage.getByXPath("//li[@itemprop='application']/span[@itemprop='countryCode']")) {
			
			DomNode nat = (DomNode)n;
			Object s = htmlPage.getByXPath("//li[@itemprop='application']/span[@itemprop='legalStatusCat']").get(i); 
			DomNode status = (DomNode)s;

			nations_map.put(nat, status);

			i++;
		}		
		String nations_active ="";	
		for (DomNode dom: nations_map.keySet()) {
			if (nations_map.get(dom).getTextContent().equals("active")){
				nations_active+=dom.getTextContent() + ", ";
			}
		}
		
         
        List<DomNode> codclass_nodes = new LinkedList<DomNode>();
 		for (Object n: htmlPage.getByXPath("//li[@itemprop='cpcs']/span[@itemprop='Code']")) {
 			codclass_nodes.add((DomNode)n);
 		}
 		
 		String codclass ="";
		String valore="";
		int counter=0;
		
		for (DomNode n: codclass_nodes) {
			codclass=n.getTextContent() + ", ";
		}
		
 		for (DomNode n: codclass_nodes) {
 			String testo= n.getTextContent();
 			if (counter<=testo.length()) {
 				valore=testo;
 				counter=testo.length();
 			}	
 			else {
 				codclass +=  valore + ", ";
 				counter=testo.length();
 			}
 		
 		}
        		
		
		Brevetto brevetto = new Brevetto(partita_iva, app_num, title, assignee, inventors, data, nations_active, abstr, codclass, keywords); 
		
		System.out.println(brevetto.toString());
		return brevetto;
					
	}
	
	//final File folder = new File("C:\\Users\\scorl\\Desktop\\Magistrale\\Secondo anno\\GISP ICT");
	
	public void modifyFileName(final File folder, String partita_iva, String CSV_folder) {
	    for (final File fileEntry : folder.listFiles()) {
	        String filename = fileEntry.getName();
	        String[] tokens = filename.split("-");
	        if (tokens[0].compareTo("gp")==0 && tokens[1].compareTo("search")==0) 
	        	{
	        	File fuffaFile = new File (CSV_folder+partita_iva+".csv");
	        	fileEntry.renameTo(fuffaFile);
	        	}
	            //System.out.println(fileEntry.getName());
	        
	    }
	}
	
	public ArrayList<String> getPatentsURLs (String partita_iva, String CSV_folder) {
		
		ArrayList<String> url_brevetti = new ArrayList<String> ();
		String line;
		int i = 0;
	
		try {
			FileReader fr = new FileReader(CSV_folder+partita_iva+".csv");
			BufferedReader br = new BufferedReader(fr);
		
			while ((line = br.readLine())!=null) {
				i++;
				if (i<3) continue;
		
				String[] brevetto = line.split(",");
				url_brevetti.add("https://patents.google.com/patent/" + brevetto[0].replace("-", ""));
			}
			
			br.close();
	
		} catch (Exception e){}
	
		System.out.println(url_brevetti.toString());
		System.out.println(url_brevetti.size());
		return url_brevetti;
	
	}



	public void downloadCSV(String dw_link, String partita_iva, String CSV_folder) {
		
		
		URL download = null;
		
		try {
			
			download = new URL(dw_link);		
			
			ReadableByteChannel rbc = Channels.newChannel(download.openStream());
			FileOutputStream fos = new FileOutputStream (CSV_folder+partita_iva+".csv");
			fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
			fos.close();
			
			
			/*
			URLConnection urlcon = new URL (dw_link).openConnection();
			HttpURLConnection httpcon = (HttpURLConnection)download.openConnection();
			httpcon.setRequestMethod("GET");
			urlcon.connect();
			
			urlcon.getInputStream();
			
			
			//WebWindow ww = webClient.getCurrentWindow();
			//String ww_name = ww.getName();
			
			WebRequest wr = new WebRequest(download);
			webClient.addRequestHeader("boh", "mah");
			webClient.getPage(wr);
					
			//webClient.getWebConnection().getResponse(wr);
			
			
*/			
			
		
		} catch (Exception e) {
				System.out.println(e);
			}
		
	}

	
	
	
	
	
	
	
}

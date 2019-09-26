package progetto.TOW3OeratoreAnalogicoBOT.model.Patent;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.kennycason.kumo.WordFrequency;

import progetto.TOW3OeratoreAnalogicoBOT.model.VirtualWebClient;
import progetto.TOW3OeratoreAnalogicoBOT.model.WebReputation.WordCloudGenerator;

public class EPOWebService {
	
	private String authentication = "";
	private String range = "1-100";
	private String type = "publication";
	private String format = "docdb";
	
	public EPOWebService (String auth) {
		this.authentication = auth;
		
	}
	
	public ArrayList<Brevetto> getPatents (String nome_azienda, String rag_soc) {
		
		ArrayList<Brevetto> patents = new ArrayList<Brevetto>();
		
		String xml_page = VirtualWebClient.setPatentHttpRequest(
				this.generateURLbyAzienda(nome_azienda, rag_soc), this.authentication
				);
		
		if (xml_page == null) return null;
		
		Document XMLdoc = this.stringToXML(xml_page);
		
		patents = this.getPatentsFromXMLdoc(XMLdoc);
		
		return patents;
	}
	
	
	
	
	public Brevetto getPatentInfo (Brevetto patent) {
		
		String xml_page = VirtualWebClient.setPatentHttpRequest(
				this.generateURLbyBrevetto(patent.getFullDocNumber()), this.authentication
				);
		
		if (xml_page == null) return null;
		
		Document XMLdoc = this.stringToXML(xml_page);

		
		patent.setData(this.getDate(XMLdoc));	// SET PATENT DATE
		
		patent.setCodclass(this.getClassificationNumbers(XMLdoc));	// SET PATENT CODCLASSES
		
		patent.setAssegnatario(this.getApplicant(XMLdoc));	// SET PATENT APPLICANT
		
		patent.setInventori(this.getInventors(XMLdoc));	// SET PATENT INVENTORS
		
		patent.setTitolo(this.getTitle(XMLdoc));	// SET PATENT TITLE
		
		String abstract_text = this.getAbstract(XMLdoc);
		patent.setAbstract_text(abstract_text);	// SET PATENT ABSTRACT
		
		patent.setKeywords(this.findKeywords(abstract_text));
		
		return patent;
		
	}
	
	
	
	private String generateURLbyAzienda (String nome_azienda, String rag_soc) {
		String http_request = "http://ops.epo.org/3.2/rest-services/published-data/search?";
		
		http_request += "Range=" + range;
		http_request += "&q=applicant%3A%22" + nome_azienda.replace(" ", "%20") + rag_soc + "%22";
		
		return http_request;
	}
	
	
	
	private String getDate (Document XMLdoc) {
		String date = XMLdoc.getElementsByTagName("date").item(0).getTextContent();
		if (date == null) return "";
		date = new StringBuilder(date).insert(4, "/").insert(7, "/").toString();
		return date;		
	}
	
	
	
	private String[] getClassificationNumbers (Document XMLdoc) {
		
		NodeList nList = XMLdoc.getElementsByTagName("classification-ipcr");
		int ipcrs = nList.getLength();
		String[] classification_numbers = new String [ipcrs];
		
		for (int i=0; i<ipcrs; i++) {
			
			Node node = nList.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) node;
				classification_numbers[i] = element.getElementsByTagName("text").item(0).getTextContent().replace(" ", "");
			}			
			
		}
		
		return classification_numbers;
	}
	
	
	
	private String getApplicant (Document XMLdoc) {
		
		NodeList nList = XMLdoc.getElementsByTagName("applicant");
		
		Node node = nList.item(0);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) node;
				return element.getElementsByTagName("name").item(0).getTextContent();
			}
		
		return "";
		
	}
	
	
	
	private String[] getInventors (Document XMLdoc) {
		
		NodeList nList = XMLdoc.getElementsByTagName("inventor");
		if (nList.getLength() == 0) return null;
		
		int names = nList.getLength();

		ArrayList<String> inventori = new ArrayList<String>();
		
		for (int i=0; i<names; i++) {
			
			Node node = nList.item(i);
			
			if (node.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) node;
				if (element.getAttribute("data-format").compareTo("epodoc") != 0) continue;
				inventori.add(element.getElementsByTagName("name").item(0).getTextContent());
			}			
			
		}
		
		return inventori.toArray(new String[inventori.size()]);
		
	}
	
	
	
	private String getTitle (Document XMLdoc) {		
		
		String title = XMLdoc.getElementsByTagName("invention-title").item(0).getTextContent();
		return title;		
	}
	
	
	private String getAbstract (Document XMLdoc) {		
		NodeList nodelist = XMLdoc.getElementsByTagName("abstract");
		if (nodelist.getLength()==0) return "";
		String abs = nodelist.item(0).getTextContent();
		return abs;		
	}
	
	
	
	private Document stringToXML (String str) {
		
		DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
		
		DocumentBuilder builder = null;
	        try
	        {
	            builder = docFactory.newDocumentBuilder();
	             
	            //Parse the content to Document object
	            Document doc = builder.parse(new InputSource(new StringReader(str)));
	            return doc;
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        	return null;
	        }
		
	}
	
	
	
	private ArrayList<Brevetto> getPatentsFromXMLdoc (Document XMLdoc){
		
		ArrayList<Brevetto> patents = new ArrayList<Brevetto>();
		
		XMLdoc.getDocumentElement().normalize();
		NodeList nList = XMLdoc.getElementsByTagName("ops:publication-reference");
		
		for (int i=0; i<nList.getLength(); i++) {
			
			Node nNode = nList.item(i);
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				
				Element element = (Element) nNode;
				String family = element.getAttribute("family-id");
				String country = element.getElementsByTagName("country").item(0).getTextContent();
				String doc_number = element.getElementsByTagName("doc-number").item(0).getTextContent();
				String kind = element.getElementsByTagName("kind").item(0).getTextContent();
				
				Brevetto patent = new Brevetto (country, doc_number, kind, family);
				
				// escludo i doppioni
				boolean familyAlreadyExists = false;
				for (Brevetto bLista : patents) {
					if (patent.getFamily_id().compareTo(bLista.getFamily_id()) == 0) {
						familyAlreadyExists = true;
						break;
					}
				}
				
				if (!familyAlreadyExists) {
					patents.add(patent);
				}
				
//				System.out.println(patent.getFamily_id());
//				System.out.println(patent.getFullDocNumber());
			}
			
		}
		
		return patents;
		
	}
	
	
	
	private String generateURLbyBrevetto (String fullDocNumber) {
		String http_request = "http://ops.epo.org/3.2/rest-services/published-data/";
		
		http_request += type + "/";
		http_request += format + "/";
		http_request += fullDocNumber + "/biblio";
		
		return http_request;
	}
	
	
	private ArrayList<String> findKeywords (String text){
		
		ArrayList<String> keywords = new ArrayList<String>();
		WordCloudGenerator gen = new WordCloudGenerator (text);
		List<WordFrequency> frequencies = gen.getFrequencies();
		Collections.sort(frequencies);
		
		for (WordFrequency freq : frequencies) {
			keywords.add(freq.getWord());
		}
		
		return keywords;
	}
	

}

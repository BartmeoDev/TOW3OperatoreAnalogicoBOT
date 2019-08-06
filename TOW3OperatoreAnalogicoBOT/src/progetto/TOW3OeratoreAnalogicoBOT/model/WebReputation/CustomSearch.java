package progetto.TOW3OeratoreAnalogicoBOT.model.WebReputation;

import java.util.ArrayList;
import java.util.List;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.google.gson.*;

import progetto.TOW3OeratoreAnalogicoBOT.model.Ricerca;
import progetto.TOW3OeratoreAnalogicoBOT.model.Risultato;
import progetto.TOW3OeratoreAnalogicoBOT.model.VirtualWebClient;

public class CustomSearch {
	
	final private String engineID = "011653134290023894024:347dqaskwcm"; // i ":" diventano "%3A"
	final private String API_key = "AIzaSyB4FrM3zX_b3ILGpcxfSdD2H9xszsoynRM";
	final private String fields = "items(displayLink,link,title),searchInformation";
	final private String sort = "date";


	private void authentication () {
	
	}
	
	
	public Ricerca searchFor (String nome_azienda) {
		
		String url = linkGeneration (nome_azienda);
		
		int start_index = 1;
		System.out.println(url);
		
		JsonObject json = this.searchForSinglePage (url, start_index);
		
		JsonObject stats = (JsonObject)json.get("searchInformation");

		Ricerca ricerca = new Ricerca ();
		int num_res;
		
		while ((num_res = stats.get("totalResults").getAsInt()) != 0) {
			
			JsonArray results = json.getAsJsonArray("items");
			ricerca.setStatistics(num_res, stats.get("searchTime").getAsFloat());
			
			results.forEach(item -> {
				
				JsonObject jobj = (JsonObject) item;
//				System.out.println(jobj.get("title").getAsString());
				Risultato r = new Risultato(jobj.get("title").getAsString(), jobj.get("link").getAsString());
				r.setBacklink(jobj.get("displayLink").getAsString());
				ricerca.addResult(r);
				
			});
			
			start_index += 10;
			if (start_index > 91) break;
			
			json = this.searchForSinglePage(url, start_index);
			stats = (JsonObject)json.get("searchInformation");
			num_res = stats.get("totalResults").getAsInt();
		
		}

		
		return ricerca;
		
	}
	
	
	
	private String linkGeneration (String nome_azienda) {
		
		String link = "https://www.googleapis.com/customsearch/v1?";
		
		link += "q=%22" + nome_azienda.replace(" ", "+") + "%22";
		link += "&cx=" + engineID.replace(":", "%3A");
		link += "&sort=" + sort;
		link += "&fields=" + fields.replace(",", "%2C");
		link += "&key=" + API_key;

		return link;
		
	}
	
	
	private JsonObject searchForSinglePage (String url, int index) {
		
		if (index > 1) url += "&start=" + index;
		
		String json_string = VirtualWebClient.setHttpRequest(url);
		
		JsonParser jsonParser = new JsonParser();
		JsonObject json = (JsonObject)jsonParser.parse(json_string);
		
		return json;
		
	}
}
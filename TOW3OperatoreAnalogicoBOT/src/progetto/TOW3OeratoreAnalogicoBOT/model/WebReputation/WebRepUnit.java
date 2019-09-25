package progetto.TOW3OeratoreAnalogicoBOT.model.WebReputation;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.kennycason.kumo.WordFrequency;

import progetto.TOW3OeratoreAnalogicoBOT.db.DBConnect;
import progetto.TOW3OeratoreAnalogicoBOT.db.OperatoreDAO;
import progetto.TOW3OeratoreAnalogicoBOT.model.Azienda;
import progetto.TOW3OeratoreAnalogicoBOT.model.Ricerca;
import progetto.TOW3OeratoreAnalogicoBOT.model.Risultato;
import progetto.TOW3OeratoreAnalogicoBOT.model.Patent.Brevetto;
import progetto.TOW3OeratoreAnalogicoBOT.model.Patent.BrevettoScraper;

public class WebRepUnit {
	
//	private WebRepScraper wrs;
	private CustomSearch customSearch;
	private ResultScraper resultScraper;
	
	private String[] rassegna_stampa = {"www.ilsole24ore.com",
										"www.repubblica.it",
										"www.lastampa.it",
										"www.corriere.it",
										"www.ilfattoquotidiano.it",
										"www.wired.it",
										"nova.ilsole24ore.com",
										"www.corrierecomunicazioni.it",
										"www.digital4.biz",
										"www.digital360hub.it",
										"www.key4biz.it",
										"inno3.it",
										"www.agendadigitale.eu",
										"www.startupbusiness.it",
										"www.economyup.it",
										"www.datamanager.it",
										"www.industry4business.it",
										"www.internet4things.it",
										"www.blockchain4innovation.it"};

	
	//private String prova = "https://www.google.com/search?q=%22fondazione+torino+wireless%22  +site:lastampa.it+OR+site:ilsole24ore.com";
	//https:                        //www.google.com/search?q=%22fondazione+torino+wireless%22+site%3Ailsole24ore.com
	
	public WebRepUnit () {
		
//		wrs = new WebRepScraper();
		customSearch = new CustomSearch();
		resultScraper = new ResultScraper();
	}
	
	
	// PRENDE IN INPUT IL NOME DELL'AZIENDA, RESTITUISCE UNA VALUTAZIONE (int o object boh)
	public Azienda getReputation (Azienda azienda) {
		
//		String url = linkGeneration(nome_azienda);
//		System.out.println(url);
		
//		wrs.searchFor(url);
		
		// Note: This API returns up to the first 100 results only.
		Ricerca resultSet = customSearch.searchFor(azienda.getNomeAzienda());
		resultSet.setPartitaIVA(azienda.getPartitaIVA());
		
		for (Risultato res : resultSet.getResults()) {
			
			res.setPartitaIVA(azienda.getPartitaIVA());
			String html_text = resultScraper.getBody(res.getLink());

			String article_text = this.extractRealText(html_text, res.getBacklink());			
			res.setText(article_text);
			this.setKeywords(res);
			
			azienda.addArticle(res);
//			System.out.println(article_text);
//			System.out.println("______________________________________________");
		}
		
		int reputation = this.calculateReputation(resultSet); // DA IMPLEMENTARE
		
		azienda.setWebRepIndex(reputation);
		
		return azienda;
//
//		System.out.print(resultSet.getNumResults());
//		System.out.print(resultSet.getEffectiveResults());
//		for (Risultato ris : resultSet.getResults()) {
//			System.out.println(ris.getLink());			
//		}
		
	}
	
	// raw extraction
	private String extractRealText (String html_text, String testata) {
		
		StringCleaner cleaner = new StringCleaner(html_text);
		
		String body_context = cleaner.extractText(testata);
		return body_context;
	
	}
	
	
	private void setKeywords (Risultato res) {
		
		WordCloudGenerator gen = new WordCloudGenerator (res.getText());
		List<WordFrequency> frequencies = gen.getFrequencies();
		
		Collections.sort(frequencies);
		
		ArrayList<String> keys = new ArrayList<String> ();
		
		for (WordFrequency freq : frequencies) {
			keys.add(freq.getWord());
		}
		
		res.setKeywords(keys);
		
	}
	
	private int calculateReputation (Ricerca ric) {
		
		
		return 0;
	}
	
	
	
//	
//	private String linkGeneration (String nome_azienda) {
//		
//		String link = "https://www.google.com/search?q=%22" + nome_azienda.replace(" ", "+") + "%22";
//		
//		for (int i=0; i<rassegna_stampa.length; i++) {
//			
//			String sito = rassegna_stampa[i];
//			
//			if (sito.startsWith("www.")) {
//				sito.replace("www.", "");
//			}
//			
//			link += "+site:" + sito;
//			
//			if (i < rassegna_stampa.length-1) {
//				link += "+OR";
//			}
//			
//		}		
//		
//		return link;
//	}

}

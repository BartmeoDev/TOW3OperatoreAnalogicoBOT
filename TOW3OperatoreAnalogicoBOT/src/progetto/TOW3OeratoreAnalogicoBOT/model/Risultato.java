package progetto.TOW3OeratoreAnalogicoBOT.model;

import java.util.HashMap;

public class Risultato {
	
	private String p_iva;
	private String title;
	private String link;
	private String date;
	private String backlink;
	private String text;
	private String keywords;
	private boolean isGood;
	
	private HashMap <String, Integer> heatmap = new HashMap<String, Integer>();
	
	public Risultato (String t, String l) {
		
		this.title = t;
		this.link = l;
		this.date = "";
		this.backlink = "";
		this.text = "";
		this.keywords = "";
		this.isGood = true;
	}
	
	public String getTitle() {
		return title;
	}

	public String getLink() {
		return link;
	}

	public String getDate() {
		return date;
	}

	public String getBacklink() {
		return backlink;
	}
	
	public void setDate (String d) {
		this.date = d;
	}
	
	public void setBacklink (String bl) {
		this.backlink = bl;
	}
	
	public void setText (String txt) {
		this.text = txt;
	}
	
	public String getText() {
		return this.text;
	}
	
	private void generateHeatmap () {
		
		
	}
	
	private void sortMapByValue () {
		
		
	}
	
	private void findKeywords () {
		
		
	}
	
	public HashMap<String, Integer> heatMap (){
		generateHeatmap ();
		sortMapByValue ();
		findKeywords ();
		return this.heatmap;
	}
	
	public void setGoodness(boolean isgood) {
		this.isGood = isgood;
	}
	
	public boolean isGood () {
		return this.isGood;
	}
	
	public String getKeywords () {
		return this.keywords;
	}
	
	public String getPartitaIVA () {
		return this.p_iva;
	}
	
	public void setPartitaIVA (String iva) {
		this.p_iva = iva;
	}
	

}

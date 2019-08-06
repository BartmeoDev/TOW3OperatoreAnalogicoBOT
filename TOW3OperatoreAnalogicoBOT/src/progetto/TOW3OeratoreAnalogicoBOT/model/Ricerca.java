package progetto.TOW3OeratoreAnalogicoBOT.model;

import java.util.ArrayList;
import java.util.List;



public class Ricerca {
	
	private List<Risultato> results;
	private int total_results = 0;
	private float search_time = 0;
	private String p_iva = "";
	
	
	
	public Ricerca (List<Risultato> r) {
		
		this.results = r;
		//this.google_results = res;		
	}
	
	public Ricerca () {
		this.results = new ArrayList<Risultato>();
	};
	
	public void addResult (Risultato res) {
		this.results.add(res);
	}
	
	
	public List<Risultato> getResults (){
		return results;
	}
	
	public int getNumResults () {
		return total_results;		
	}
	
	public int getEffectiveResults () {
		return results.size();
	}
	
	public void setStatistics (int num, float time) {
		
		this.total_results = num;
		this.search_time = time;
	}
	
	public float searchTime () {
		return this.search_time;
	}
	
	
	public String getPartitaIVA () {
		return this.p_iva;
	}
	
	public void setPartitaIVA (String iva) {
		this.p_iva = iva;
	}
	

}

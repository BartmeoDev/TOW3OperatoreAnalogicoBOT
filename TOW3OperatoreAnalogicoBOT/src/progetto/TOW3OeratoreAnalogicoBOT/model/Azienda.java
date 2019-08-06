package progetto.TOW3OeratoreAnalogicoBOT.model;

import java.util.ArrayList;

import progetto.TOW3OeratoreAnalogicoBOT.model.Patent.Brevetto;

public class Azienda {
	
	private String partitaIVA;
	private String nomeAzienda;
	private String ragioneSociale;
	private int webRepIndex = 0;
	
	private ArrayList<Brevetto> brevetti = new ArrayList<Brevetto>();
	private ArrayList<Risultato> articoli = new ArrayList<Risultato>();
	
	
	public Azienda (String nome, String iva, String ragione) {
		this.partitaIVA = iva;
		this.nomeAzienda = nome;
		this.ragioneSociale = ragione;
	}
	
	

	public String getPartitaIVA() {
		return partitaIVA;
	}

	public String getNomeAzienda() {
		return nomeAzienda;
	}

	public ArrayList<Brevetto> getBrevetti() {
		return brevetti;
	}
	
	// INSERISCE LISTA BREVETTI
	public void insertBrevetti (ArrayList<Brevetto> lista) {

		for (Brevetto b : lista) {
			brevetti.add(b);			
		}
	}
	
	// INSERISCE SINGOLO BREVETTO
	public void insertBrevetto (Brevetto b) {

		brevetti.add(b);
	}
	
	public int countBrevetti () {
		
		return brevetti.size();
	}
	

	public int getWebRepIndex() {
		return webRepIndex;
	}

	public void setWebRepIndex(int webRepIndex) {
		this.webRepIndex = webRepIndex;
	}
	
	public String getRagioneSociale() {
		return ragioneSociale;
	}

	public void setRagioneSociale(String ragioneSociale) {
		this.ragioneSociale = ragioneSociale;
	}
	
	public void setArticoli (ArrayList<Risultato> arts){
		this.articoli = arts;		
	}
	
	public ArrayList<Risultato> getArticoli (){
		return this.articoli;
	}
	
	public void addArticle (Risultato res) {
		articoli.add(res);
	}
	
	
}

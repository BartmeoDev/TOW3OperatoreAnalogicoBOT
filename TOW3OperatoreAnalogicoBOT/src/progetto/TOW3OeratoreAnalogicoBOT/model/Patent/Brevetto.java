package progetto.TOW3OeratoreAnalogicoBOT.model.Patent;

public class Brevetto {

	private String p_iva;
	private String titolo;
	private String assegnatario;
	private String[] inventori;
	private String data;
	private String[] codclass;
	private String luogo;
	private String abstract_text;
	private String keywords;
	
	private String country;
	private String doc_number;
	private String kind;
	private String family_id;
	
	
	public String getCountry() {
		return country;
	}

	public String getDoc_number() {
		return doc_number;
	}

	public String getFamily_id() {
		return family_id;
	}	
	
	public Brevetto (String c, String n, String k, String f) {
		this.country = c;
		this.doc_number = n;
		this.kind = k;
		this.family_id = f;
		this.p_iva = "";
		this.titolo = "";
		this.assegnatario = "";
		this.data = "";
		this.abstract_text = "";
		this.keywords = "";
		
	}
	
	public String getFullDocNumber () {		
		return this.country + "." + this.doc_number + "." + this.kind;
	}	
	
	public String getLuogo() {
		return luogo;
	}

	public void setLuogo(String luogo) {
		this.luogo = luogo;
	}

	public String[] getCodclass() {
		return codclass;
	}
	
	public String getCodclassAsString() {
		String cods = "";
		
		for (String c : this.codclass) {			
			cods += c + "; ";
		}
		return cods;
	}

	public void setCodclass(String[] codclass) {
		this.codclass = codclass;
	}


	public Brevetto(String p_iva, String app_number, String titolo, String assegnatario, String inventori, String data,
			String luogo, String abstract_text,String firstcodclass, String keywords) {
		super();
		this.p_iva = p_iva;
		this.doc_number = app_number;
		this.titolo = titolo;
		this.assegnatario = assegnatario;
		this.inventori[0] = inventori;
		this.data = data;
		this.codclass[0] = firstcodclass;
		this.luogo = luogo;
		this.abstract_text = abstract_text;
		this.keywords = keywords;
	}

	
	public String getKeywords() {
		return keywords;
	}

	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public String[] getInventori() {
		return inventori;
	}

	public void setInventori(String[] inventori) {
		this.inventori = inventori;
	}

	public String getAssegnatario() {
		return assegnatario;
	}

	public void setAssegnatario(String assegnatario) {
		this.assegnatario = assegnatario;
	}

	public String getAbstract_text() {
		return abstract_text;
	}

	public void setAbstract_text(String abstract_text) {
		this.abstract_text = abstract_text;
	}

	public String getP_iva() {
		return p_iva;
	}

	public void setP_iva(String p_iva) {
		this.p_iva = p_iva;
	}

	public String getTitolo() {
		return titolo;
	}

	public void setTitolo(String titolo) {
		this.titolo = titolo;
	}

	@Override
	public String toString() {
		return "Brevetto [p_iva=" + p_iva + ", app_number=" + doc_number + ", titolo=" + titolo + "]";
	}
	
	
	
	public String getInventoriAsString () {
		if (inventori == null) return "";
		
		String inv = "";
		for (String s : this.inventori) {
			inv += s + ", ";
		}
		
		return inv;
	}
	
	
	

}

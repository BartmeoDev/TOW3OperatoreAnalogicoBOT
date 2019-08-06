package progetto.TOW3OeratoreAnalogicoBOT.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import progetto.TOW3OeratoreAnalogicoBOT.model.Risultato;
import progetto.TOW3OeratoreAnalogicoBOT.model.Patent.Brevetto;

public class OperatoreDAO {
	Connection connection = null;
	
	final String sqlBrevettoGoogle = "INSERT INTO `brevetti` (`p_iva`, `app_number`,`titolo`, `assegnatario`, `inventore`, `data_rilascio`, `luogo_validita` , `abstract`, `cod_classificazione`, `keywords`) VALUES (?,?,?,?,?,?,?,?,?,?);";
	final String sqlArticolo = "INSERT INTO `articoli` (`PartitaIVA`, `Title`, `Link`, `Backlink`, `Text`, `Keywords` , `Date`) VALUES (?,?,?,?,?,?,?);";
	final String sqlBrevettoEPO = "INSERT INTO `brevetti` (`P_iva`, `App_number`, `Family_id`, `Titolo`, `Assegnatario`, `Inventori`, `Data`, `Abstract_text`, `Codclass`) VALUES (?,?,?,?,?,?,?,?,?);";
	//	String sqlTitle = "INSERT INTO 'brevetti' ('titolo') VALUES(?);";
//	final String sqlDelete = "TRUNCATE `brevetti`";
	
	public void setConnection(Connection connection) {
		this.connection = connection;
	}
	
	
	public void insertBrevettoFromGpatent(Brevetto b) {
		/*try {
			PreparedStatement stat;
			stat = connection.prepareStatement(sqlDelete);
			stat.execute();

		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}*/
		
		
		try {
			PreparedStatement st;
			st = connection.prepareStatement(sqlBrevettoGoogle);
			st.setString(1, b.getP_iva());
			st.setString(2, b.getDoc_number());
			st.setString(3, b.getTitolo());
			st.setString(4, b.getAssegnatario());
			st.setString(5, b.getInventoriAsString());
			st.setString(6, b.getData());
			st.setString(7, b.getLuogo());
			st.setString(8, b.getAbstract_text());
			st.setString(9, b.getCodclassAsString());
			st.setString(10, b.getKeywords());
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void insertEPOpatent (Brevetto b) {
		
		try {
			PreparedStatement st;
			st = connection.prepareStatement(sqlBrevettoEPO);
			st.setString(1, b.getP_iva());	// stringa, 11 caratteri?
			st.setString(2, b.getFullDocNumber());	// stringa, max 20 caratteri
			st.setString(3, b.getFamily_id());	// stringa, max 15 caratteri
			st.setString(4, b.getTitolo());	// stringa, max 500 caratteri (meglio abbondare)
			st.setString(5, b.getAssegnatario());	// stringa, max 100 caratteri
			st.setString(6, b.getInventoriAsString());	// stringa, max 500 caratteri
			st.setString(7, b.getData());	// stringa, 10 caratteri (formato aaaa/mm/gg)
			st.setString(8, b.getAbstract_text());	// stringa, il massimo (su heidisql "long text" o "text" credo)
			st.setString(9, b.getCodclassAsString());	// stringa, max 200 caratteri (è una lista di codici, non solo più uno come prima)
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	
	public void insertArticle (Risultato res) {
		
		try {
			PreparedStatement st;
			st = connection.prepareStatement(sqlArticolo);
			st.setString(1, res.getPartitaIVA()); // stringa, 11 caratteri?
			st.setString(2, res.getTitle()); // stringa, max 500 caratteri
			st.setString(3, res.getLink()); // stringa, max 500 caratteri
			st.setString(4, res.getBacklink()); // stringa, max 100 caratteri
			st.setString(5, res.getText()); // stringa, il massimo
			st.setString(6, res.getKeywords()); // stringa, max 200 caratteri (non è implementato, ma si potrebbe fare)
			st.setString(7, res.getDate()); // stringa, max 12 caratteri (non implementato)
			
			st.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	public void insertTitolo(String title) {
		try {
			PreparedStatement st = connection.prepareStatement(sqlTitle, Statement.RETURN_GENERATED_KEYS);
			st.setString(1, title);
			ResultSet rs = st.getGeneratedKeys();
			rs.next();
			System.out.println("rs");
		
		
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}
*/
}

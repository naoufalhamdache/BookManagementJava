package appBDD;

import java.sql.*;

public class LoanModel {
	
	// Fields
	String NumPret;
	String RefContact;
	String RefLivre;
	String DateDuPret;
	String DateDuRetour;

	// Getters & Setters
	public String getNumPret() {
		return NumPret;
	}

	public void setNumPret(String numPret) {
		NumPret = numPret;
	}

	public String getRefContact() {
		return RefContact;
	}

	public void setRefContact(String refContact) {
		RefContact = refContact;
	}

	public String getRefLivre() {
		return RefLivre;
	}

	public void setRefLivre(String refLivre) {
		RefLivre = refLivre;
	}

	public String getDateDuPret() {
		return DateDuPret;
	}

	public void setDateDuPret(String dateDuPret) {
		DateDuPret = dateDuPret;
	}

	public String getDateDuRetour() {
		return DateDuRetour;
	}

	public void setDateDuRetour(String dateDuRetour) {
		DateDuRetour = dateDuRetour;
	}

	// Constructors
	public LoanModel(String numPret, String refContact, String refLivre, String dateDuPret, String dateDuRetour) {
		super();
		NumPret = numPret;
		RefContact = refContact;
		RefLivre = refLivre;
		DateDuPret = dateDuPret;
		DateDuRetour = dateDuRetour;
	}
	
	public LoanModel(ResultSet result) throws SQLException{
		super();
		for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
			if (result.getMetaData().getColumnName(i).equalsIgnoreCase("NumPret")) NumPret = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("RefContact")) RefContact = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("RefLivre")) RefLivre = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("DateDuPret")) DateDuPret = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("DateDuRetour")) DateDuRetour = result.getString(i);
		}
	}
}

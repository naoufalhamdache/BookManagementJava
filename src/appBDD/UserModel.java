package appBDD;

import java.sql.*;

public class UserModel {
	
	// Fields
	String RefContact;
	String Titre;
	String Nom;
	String Prenom;
	String Adresse;
	String Ville;
	String CodePostal;
	String NumeroTelephone;
	String Adherent;
	
	// Getters & Setters
	public String getRefContact() {
		return RefContact;
	}

	public void setRefContact(String refContact) {
		RefContact = refContact;
	}

	public String getTitre() {
		return Titre;
	}

	public void setTitre(String titre) {
		Titre = titre;
	}

	public String getNom() {
		return Nom;
	}

	public void setNom(String nom) {
		Nom = nom;
	}

	public String getPrenom() {
		return Prenom;
	}

	public void setPrenom(String prenom) {
		Prenom = prenom;
	}

	public String getAdresse() {
		return Adresse;
	}

	public void setAdresse(String adresse) {
		Adresse = adresse;
	}

	public String getVille() {
		return Ville;
	}

	public void setVille(String ville) {
		Ville = ville;
	}

	public String getCodePostal() {
		return CodePostal;
	}

	public void setCodePostal(String codePostal) {
		CodePostal = codePostal;
	}

	public String getNumeroTelephone() {
		return NumeroTelephone;
	}

	public void setNumeroTelephone(String numeroTelephone) {
		NumeroTelephone = numeroTelephone;
	}
	
	public String getAdherent() {
		return Adherent;
	}

	public void setAdherent(String adherent) {
		Adherent = adherent;
	}
	
	// Constructors
	public UserModel(String refContact, String titre, String nom, String prenom, String adresse,
			String ville, String codePostal, String numeroTelephone, String adherent) {
		super();
		RefContact = refContact;
		Titre = titre;
		Nom = nom;
		Prenom = prenom;
		Adresse = adresse;
		Ville = ville;
		CodePostal = codePostal;
		NumeroTelephone = numeroTelephone;
		Adherent = adherent;
	}
	
	public UserModel(ResultSet result) throws SQLException{
		super();
		for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
			if (result.getMetaData().getColumnName(i).equalsIgnoreCase("RefContact")) RefContact = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Titre")) Titre = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Nom")) Nom = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Prenom")) Prenom = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Adresse")) Adresse = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Ville")) Ville = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("CodePostal")) CodePostal = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("NumeroTelephone")) NumeroTelephone = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Adherent")) Adherent = result.getString(i);
		}
	}

}

package appBDD;

import java.sql.*;

public class BookModel {
	
	// Fields
	String RefLivre;
	String Auteur;
	String Titre;
	String Editeur;
	String Genre;
	String DateAchat;
	String PrixAchat;
	
	// Getters & Setters
	public String getRefLivre() {
		return RefLivre;
	}
	
	public void setRefLivre(String refLivre) {
		RefLivre = refLivre;
	}
	
	public String getAuteur() {
		return Auteur;
	}
	
	public void setAuteur(String auteur) {
		Auteur = auteur;
	}
	
	public String getTitre() {
		return Titre;
	}
	
	public void setTitre(String titre) {
		Titre = titre;
	}
	
	public String getEditeur() {
		return Editeur;
	}
	
	public void setEditeur(String editeur) {
		Editeur = editeur;
	}
	
	public String getGenre() {
		return Genre;
	}
	
	public void setGenre(String genre) {
		Genre = genre;
	}
	
	public String getDateAchat() {
		return DateAchat;
	}
	
	public void setDateAchat(String dateAchat) {
		DateAchat = dateAchat;
	}
	
	public String getPrixAchat() {
		return PrixAchat;
	}
	
	public void setPrixAchat(String prixAchat) {
		PrixAchat = prixAchat;
	}
	
	//Constructors
	public BookModel(String refLivre, String auteur, String titre, String editeur, String genre, String dateAchat, String prixAchat) {
		super();
		RefLivre = refLivre;
		Auteur = auteur;
		Titre = titre;
		Editeur = editeur;
		Genre = genre;
		DateAchat = dateAchat;
		PrixAchat = prixAchat;
	}
	
	public BookModel(ResultSet result) throws SQLException{
		super();
		for (int i = 1; i <= result.getMetaData().getColumnCount(); i++) {
			if (result.getMetaData().getColumnName(i).equalsIgnoreCase("RefLivre")) RefLivre = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Auteur")) Auteur = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Titre")) Titre = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Editeur")) Editeur = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("Genre")) Genre = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("DateAchat")) DateAchat = result.getString(i);
			else if (result.getMetaData().getColumnName(i).equalsIgnoreCase("PrixAchat")) PrixAchat = result.getString(i);
		}
	}
	
}

package appBDD;

import java.sql.*;
import java.util.*;

public class AddBookController {
	
	// Method for testing if primary key exists
	public static boolean findpk (BookModel bm) throws SQLException {
		boolean find = false;
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT * FROM Livre";
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				BookModel bm2 = new BookModel(result);
				if (bm.getRefLivre().equals(bm2.getRefLivre()))
					find = true;
			}
			
			return find;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method for adding a new book
	public static void update (BookModel bm) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "INSERT INTO LIVRE (RefLivre, Auteur, Titre, Editeur, Genre, DateAchat, PrixAchat) "
						 + "VALUES (\"" + bm.getRefLivre() + "\", \"" + bm.getAuteur() + "\", \"" + bm.getTitre() + "\", \"" +
						 bm.getEditeur() + "\", \"" + bm.getGenre() + "\", " + bm.getDateAchat() + ", \"" +
						 bm.getPrixAchat() + "\" )";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
}

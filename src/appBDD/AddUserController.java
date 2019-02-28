package appBDD;

import java.sql.*;
import java.util.*;

public class AddUserController {
	
	// Method for testing if primary key exists
	public static boolean findpk (UserModel um) throws SQLException {
		boolean find = false;
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT * FROM Adherent";
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				UserModel um2 = new UserModel(result);
				if (um.getRefContact().equals(um2.getRefContact()))
					find = true;
			}
			
			return find;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method for adding a new user
	public static void update (UserModel um) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "INSERT INTO ADHERENT (RefContact, Titre, Nom, Prenom, Adresse, CodePostal, Ville, NumeroTelephone, Adherent) "
						 + "VALUES (\"" + um.getRefContact() + "\", \"" + um.getTitre() + "\", \"" + um.getNom() + "\", \"" +
						 um.getPrenom() + "\", \"" + um.getAdresse() + "\", \"" + um.getCodePostal() + "\", \"" +
						 um.getVille() + "\", \"" + um.getNumeroTelephone() + "\", \"" + um.getAdherent() + "\" )";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
}

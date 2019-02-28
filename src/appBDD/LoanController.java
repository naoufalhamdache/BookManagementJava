package appBDD;

import java.sql.*;
import java.util.*;

import javafx.stage.Stage;

public class LoanController {
	
	// Delete loans from Database
	public static void delete (LoanModel elem) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Delete loan
			String query = "";
			query = "DELETE FROM Pret WHERE NumPret = \"" + elem.getNumPret() + "\"";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Delete loan on book delete
	public static void deletebook (BookModel elem)throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Delete loan
			String query = "";
			query = "DELETE FROM Pret WHERE RefLivre = \"" + elem.getRefLivre() + "\"";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Delete loan on user delete
	public static void deleteuser (UserModel elem)throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Delete loan
			String query = "";
			query = "DELETE FROM Pret WHERE RefContact = \"" + elem.getRefContact() + "\"";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Select loans from Database (on book reference)
	public static ResultSet execute (List<String> x, String RefLivre) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Create List with Column Names
			List<String> y = new ArrayList<String>();
			String req2 = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'Pret'";
			ResultSet result2 = connect.createStatement().executeQuery(req2);
			while (result2.next()) {
				y.add(result2.getString("COLUMN_NAME"));
			}
			
			// Create List with Valid Column Names from USER
			List<String> z = new ArrayList<String>();
			boolean find = false;
			for (String s1 : x)
				for (String s2 : y)
					if (s1.equalsIgnoreCase(s2)) {
						z.add(s2);
					}
			for (String s3 : z)
				if (s3.equalsIgnoreCase("NumPret"))
					find = true;
			if (!find && !z.isEmpty())
				z.add(0, "NumPret");
			else if (find && z.size() == 1 && z.contains("NumPret"))
				z.clear();
			
			// Prepare & Execute Queries
			String columns = "";
			String req1;
			ResultSet result;
			
			if (z.size() == 0) {
				String req0 = "SELECT * FROM Pret WHERE NumPret = \"-1\"";
				ResultSet result0;
				Statement S0 = connect.createStatement();
				result0 = S0.executeQuery(req0);
				return result0;
			}
			else
				columns += String.join(", ", z);

			if (!RefLivre.isEmpty()) {
				req1 = "SELECT "+ columns + " FROM Pret WHERE RefLivre = ?";
				// Prepare Statement
				PreparedStatement pS = connect.prepareStatement(req1);
				pS.setString(1, RefLivre);
				// Execute Statement & Save Result 
				result = pS.executeQuery();
			}
			else {
				req1 = "SELECT "+ columns + " FROM Pret";
				// Prepare Statement
				Statement S = connect.createStatement();
				// Execute Statement & Save Result 
				result = S.executeQuery(req1);
			}
			
			// Return Valid Column Names
			return result;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
}

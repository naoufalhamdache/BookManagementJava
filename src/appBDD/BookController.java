package appBDD;

import java.sql.*;
import java.util.*;

public class BookController {
	
	// Delete books from Database
	public static void delete (BookModel elem) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Delete all book loans
			LoanController.deletebook(elem);
			
			// Delete book
			String query = "";
			query = "DELETE FROM Livre WHERE RefLivre = \"" + elem.getRefLivre() + "\"";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Select books from Database (on gender)
	public static ResultSet execute (List<String> x, String Genre) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Create List with Column Names
			List<String> y = new ArrayList<String>();
			String req2 = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'Livre'";
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
				if (s3.equalsIgnoreCase("RefLivre"))
					find = true;
			if (!find && !z.isEmpty())
				z.add(0, "RefLivre");
			else if (find && z.size() == 1 && z.contains("RefLivre"))
				z.clear();
			
			// Prepare & Execute Queries
			String columns = "";
			String req1;
			ResultSet result;
			
			if (z.size() == 0) {
				String req0 = "SELECT * FROM Livre WHERE RefLivre = \"-1\"";
				ResultSet result0;
				Statement S0 = connect.createStatement();
				result0 = S0.executeQuery(req0);
				return result0;
			}
			else
				columns += String.join(", ", z);
			
			if (!Genre.isEmpty()) {
				req1 = "SELECT "+ columns + " FROM Livre WHERE Genre = ?";
				// Prepare Statement
				PreparedStatement pS = connect.prepareStatement(req1);
				pS.setString(1, Genre);
				// Execute Statement & Save Result 
				result = pS.executeQuery();
			}
			else {
				req1 = "SELECT "+ columns + " FROM Livre";
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

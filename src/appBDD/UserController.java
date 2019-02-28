package appBDD;

import java.sql.*;
import java.util.*;

public class UserController {
	
	// Delete users from Database
	public static void delete (UserModel elem) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Delete all user loans
			LoanController.deleteuser(elem);
			
			// Delete user
			String query = "";
			query = "DELETE FROM Adherent WHERE RefContact = \"" + elem.getRefContact() + "\"";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Select users from Database (on city)
	public static ResultSet execute (List<String> x, String Ville) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Create List with Column Names
			List<String> y = new ArrayList<String>();
			String req2 = "SELECT * FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = N'Adherent'";
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
				if (s3.equalsIgnoreCase("RefContact"))
					find = true;
			if (!find && !z.isEmpty())
				z.add(0, "RefContact");
			else if (find && z.size() == 1 && z.contains("RefContact"))
				z.clear();
			
			// Prepare & Execute Queries
			String columns = "";
			String req1;
			ResultSet result;
			
			if (z.size() == 0) {
				String req0 = "SELECT * FROM Adherent WHERE RefContact = \"-1\"";
				ResultSet result0;
				Statement S0 = connect.createStatement();
				result0 = S0.executeQuery(req0);
				return result0;
			}
			else
				columns += String.join(", ", z);
			
			if (!Ville.isEmpty()) {
				req1 = "SELECT "+ columns + " FROM Adherent WHERE Ville = ?";
				// Prepare Statement
				PreparedStatement pS = connect.prepareStatement(req1);
				pS.setString(1, Ville);
				// Execute Statement & Save Result 
				result = pS.executeQuery();
			}
			else {
				req1 = "SELECT "+ columns + " FROM Adherent";
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

package appBDD;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AddLoanController {
	
	// Method for testing if primary key exists
	public static boolean findpk (LoanModel lm) throws SQLException {
		boolean find = false;
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT * FROM Pret";
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				LoanModel lm2 = new LoanModel(result);
				if (lm.getNumPret().equalsIgnoreCase(lm2.getNumPret()))
					find = true;
			}
			
			return find;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method for testing if a book isn't available
	public static boolean indispo (LoanModel lm) throws SQLException {
		boolean indispo = false;
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT * FROM Pret";
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				LoanModel lm2 = new LoanModel(result);
				if (lm.getRefLivre().equalsIgnoreCase(lm2.getRefLivre()) && (lm2.getDateDuRetour() == null))
					indispo = true;
			}
			
			return indispo;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method for testing if a user have 3 books
	public static boolean trop (LoanModel lm) throws SQLException {
		boolean trop = false;
		int count = 0;
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT * FROM Pret";
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				LoanModel lm2 = new LoanModel(result);
				if (lm.getRefContact().equalsIgnoreCase(lm2.getRefContact()) && (lm2.getDateDuRetour() == null))
					count++;
			}
			if (count >= 3)
				trop = true;
			
			return trop;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method for testing if a user has exceed the delay to return a book
	public static boolean retard (LoanModel lm) throws SQLException {
		boolean retard = false;
		
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT * FROM Pret";
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				LoanModel lm2 = new LoanModel(result);
				int diff = 0;
				if (lm.getRefContact().equalsIgnoreCase(lm2.getRefContact()) && lm2.getDateDuRetour() == null) {
					LocalDate now = LocalDate.now();
				    LocalDate datePret = LocalDate.parse(lm2.getDateDuPret());
				    Period period = Period.between(datePret, now);
				    diff = (period.getYears()*365)+(period.getMonths()*31)+(period.getDays());
				}
				if (lm.getRefContact().equalsIgnoreCase(lm2.getRefContact()) && (lm2.getDateDuRetour() == null) && diff >= 15)
					retard = true;
			}
			
			return retard;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method for adding a new load
	public static void update (LoanModel lm) throws SQLException {
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "INSERT INTO PRET (NumPret, RefLivre, RefContact, DateDuPret, DateDuRetour) "
						 + "VALUES (\"" + lm.getNumPret() + "\", \"" + lm.getRefLivre() + "\", \"" + lm.getRefContact() + "\", \"" +
						 lm.getDateDuPret() + "\", " + lm.getDateDuRetour() + " )";
			Statement stmt = connect.createStatement();
			stmt.executeUpdate(query);
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
	
	// Method to get list of references
	public static ObservableList<String> getRef (String colname, String table) throws SQLException {
		ObservableList<String> resultList = FXCollections.observableArrayList();
		try {
			// Connect to Database
			Connection connect = DBConnector.getConnection();
			
			// Prepare & Execute Query
			String query = "SELECT " + colname + " FROM " + table;
			Statement stmt = connect.createStatement();
			ResultSet result = stmt.executeQuery(query);
			
			// Test result
			while (result.next()) {
				resultList.add(result.getString(1));
			}
			
			return resultList;
		}
		catch (SQLException e) {
			//e.printStackTrace();
			throw new SQLException();
		}
	}
}

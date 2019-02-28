package appBDD;

import java.sql.*;

import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class DBConnector {
	public static Connection getConnection () throws SQLException{
		try {
			Connection myConnex = DriverManager.getConnection("jdbc:mysql://localhost:3306/dbn?useLegacyDatetimeCode=false&serverTimezone=MST", "root", "");
			return myConnex;
		}
		catch (SQLException e) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Erreur");
			alert.setHeaderText("Connexion impossible");
			alert.showAndWait();
			throw new SQLException();
		}
	}
}

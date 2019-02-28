package appBDD;

import java.math.*;
import java.sql.*;
import java.time.*;
import java.util.*;
import java.util.function.*;

import javax.swing.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.collections.ListChangeListener.*;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.*;

public class AddUserView extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage window) throws Exception {
		
		// Create Fields
		TextField tref = new TextField ();
		TextField ttitre = new TextField ();
		TextField tnom = new TextField ();
		TextField tprenom = new TextField ();
		TextField tadresse = new TextField ();
		TextField tcp = new TextField ();
		TextField tville = new TextField ();
		TextField tnum = new TextField ();
		TextField tad = new TextField ();
		
		// Handle TextFields
		EventHandler<KeyEvent> hand = e -> {
        	TextField x = (TextField) e.getSource();
        	String tempText;
        	int len = 0;
        	if (e.getSource() == tref) len = 3;
        	else if (e.getSource() == ttitre) len = 12;
        	else if (e.getSource() == tnom) len = 6;
        	else if (e.getSource() == tprenom) len = 8;
        	else if (e.getSource() == tadresse) len = 20;
        	else if (e.getSource() == tcp) len = 5;
        	else if (e.getSource() == tville) len = 4;
        	else if (e.getSource() == tnum) len = 14;
        	else if (e.getSource() == tad) len = 1;
        	if (x.getLength() > len-1) {
        		int pos = x.getCaretPosition();
        		tempText = x.getText().substring(0, len);
        		x.clear();
            	x.setText(tempText);
            	x.positionCaret(pos);
        	}
        };
		
		// Create Stage
        window.setWidth(400);
        window.setHeight(470);
        window.setTitle("Ajout Adhérent");
        
        // Create Nodes + HBoxes
        Label lref = new Label("Référence:"); lref.setMinWidth(100);
        tref.setMinWidth(200);
        HBox hb0 = new HBox();
        hb0.getChildren().addAll(lref, tref);
        tref.setOnKeyPressed(hand); tref.setOnKeyReleased(hand);
        hb0.setSpacing(10); hb0.setPadding(new Insets(5, 5, 5, 5));
        
        Label ltitre = new Label("Civilité:"); ltitre.setMinWidth(100);
        ttitre.setMinWidth(200);
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(ltitre, ttitre);
        ttitre.setOnKeyPressed(hand); ttitre.setOnKeyReleased(hand);
        hb1.setSpacing(10); hb1.setPadding(new Insets(5, 5, 5, 5));
        
        Label lnom = new Label("Nom:"); lnom.setMinWidth(100);
        tnom.setMinWidth(200);
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(lnom, tnom);
        tnom.setOnKeyPressed(hand); tnom.setOnKeyReleased(hand);
        hb2.setSpacing(10); hb2.setPadding(new Insets(5, 5, 5, 5));
        
        Label lprenom = new Label("Prenom:"); lprenom.setMinWidth(100);
        tprenom.setMinWidth(200);
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(lprenom, tprenom);
        tprenom.setOnKeyPressed(hand); tprenom.setOnKeyReleased(hand);
        hb3.setSpacing(10); hb3.setPadding(new Insets(5, 5, 5, 5));
        
        Label ladresse = new Label("Adresse:"); ladresse.setMinWidth(100);
        tadresse.setMinWidth(200);
        HBox hb4 = new HBox();
        hb4.getChildren().addAll(ladresse, tadresse);
        tadresse.setOnKeyPressed(hand); tadresse.setOnKeyReleased(hand);
        hb4.setSpacing(10); hb4.setPadding(new Insets(5, 5, 5, 5));
        
        Label lcp = new Label("Code Postal:"); lcp.setMinWidth(100);
        tcp.setMinWidth(200);
        HBox hb5 = new HBox();
        hb5.getChildren().addAll(lcp, tcp);
        tcp.setOnKeyPressed(hand); tcp.setOnKeyReleased(hand);
        hb5.setSpacing(10); hb5.setPadding(new Insets(5, 5, 5, 5));
        
        Label lville = new Label("Ville:"); lville.setMinWidth(100);
        tville.setMinWidth(200);
        HBox hb6 = new HBox();
        hb6.getChildren().addAll(lville, tville);
        tville.setOnKeyPressed(hand); tville.setOnKeyReleased(hand);
        hb6.setSpacing(10); hb6.setPadding(new Insets(5, 5, 5, 5));
        
        Label lnum = new Label("Téléphone:"); lnum.setMinWidth(100);
        tnum.setMinWidth(200);
        HBox hb7 = new HBox();
        hb7.getChildren().addAll(lnum, tnum);
        tnum.setOnKeyPressed(hand); tnum.setOnKeyReleased(hand);
        hb7.setSpacing(10); hb7.setPadding(new Insets(5, 5, 5, 5));
        
        Label lad = new Label("Adherent:"); lad.setMinWidth(100);
        tad.setMinWidth(200);
        HBox hb8 = new HBox();
        hb8.getChildren().addAll(lad, tad);
        tad.setOnKeyPressed(hand); tad.setOnKeyReleased(hand);
        hb8.setSpacing(10); hb8.setPadding(new Insets(5, 5, 5, 5));
        
        HBox hb9 = new HBox();
        Button add = new Button("Add"); add.setMinWidth(100);
        hb9.getChildren().add(add);
        hb9.setSpacing(10); hb9.setPadding(new Insets(30, 5, 5, 115));
        
        // Create VBox
        VBox vb = new VBox();
        vb.getChildren().addAll(hb0, hb1, hb2, hb3, hb4, hb5, hb6, hb7, hb8, hb9);
        vb.setPadding(new Insets(30, 30, 30, 30));
        
        // Create & Set RootPane
        BorderPane parent = new BorderPane();
        parent.getChildren().add(vb);
    
	    // Create & Set Scene
	    Scene scene = new Scene(parent);
	    window.setScene(scene);
	
		// Show Stage
		window.show();

		// Handle add Button
		add.setOnAction(e -> {
			Alert alert = new Alert(AlertType.ERROR);
			if (tref.getText().isEmpty()) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez une référence");
				alert.setContentText("Référence manquante");
				alert.showAndWait();
			}
			else if (ttitre.getText().isEmpty()) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez une civilité");
				alert.setContentText("Civilité manquante");
				alert.showAndWait();
			}
			else if (tnom.getText().isEmpty()) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez un nom");
				alert.setContentText("Nom manquant");
				alert.showAndWait();
			}
			else if (tprenom.getText().isEmpty()) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez un prenom");
				alert.setContentText("Prenom manquant");
				alert.showAndWait();
			}
			else if (!tref.getText().isEmpty() && !isANumber(tref.getText())) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez une référence avec des chiffres uniquement");
				alert.setContentText("Format de référence invalide");
				alert.showAndWait();
			}
			else if (!tcp.getText().isEmpty() && !isANumber(tcp.getText())) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez un code postal avec des chiffres uniquement");
				alert.setContentText("Format de code postal invalide");
				alert.showAndWait();
			}
			else if (!isANumber(tad.getText()) || (tad.getText().charAt(0) != '0' && tad.getText().charAt(0) != '1')) {
				alert.setTitle("Erreur");
				alert.setHeaderText("Saisissez uniquement 0 ou 1 dans le champ adhérent");
				alert.setContentText("Caractère invalide");
				alert.showAndWait();
		 	}
			else {
				
				UserModel um = new UserModel(tref.getText(), ttitre.getText(), tnom.getText(), tprenom.getText(), tadresse.getText(), tville.getText(), tcp.getText(), tnum.getText(), tad.getText());
				if (tcp.getText().isEmpty())
					um.setCodePostal("0");
				try {
					if (AddUserController.findpk(um)) {
						alert.setTitle("Erreur");
						alert.setHeaderText("Saisissez une référence unique");
						alert.setContentText("Référence deja existante");
						alert.showAndWait();
					}
					else {
						AddUserController.update(um);
						Stage owner = (Stage) window.getOwner();
						UserView uv = new UserView();
						uv.start(owner);
						window.close();
					}
				} catch (Exception e2) {
					//e1.printStackTrace();
				}
			}
		});
	} // Close start()
	
	// Method for testing TextFields
	private boolean isANumber(String s) {
	  if (s == null) return false;
	  try {
	    new BigInteger(s);
	    return true;
	  } catch (NumberFormatException e) {
	    return false;
	  }
	}
	
}

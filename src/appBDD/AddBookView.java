package appBDD;

import java.math.*;
import java.sql.*;
import java.time.*;
import java.util.*;

import javax.swing.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.*;
import javafx.scene.control.cell.*;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class AddBookView extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage window) throws Exception {
		
		// Create Stage
        window.setWidth(400);
        window.setHeight(400);
        window.setTitle("Ajout Livre");
        
        // Create Nodes + HBoxes
        Label lref = new Label("Référence:"); lref.setMinWidth(100);
        TextField tref = new TextField (); tref.setMinWidth(200);
        HBox hb0 = new HBox();
        hb0.getChildren().addAll(lref, tref);
        hb0.setSpacing(10); hb0.setPadding(new Insets(5, 5, 5, 5));
        
        Label lauteur = new Label("Auteur:"); lauteur.setMinWidth(100);
        TextField tauteur = new TextField (); tauteur.setMinWidth(200);
        HBox hb1 = new HBox();
        hb1.getChildren().addAll(lauteur, tauteur);
        hb1.setSpacing(10); hb1.setPadding(new Insets(5, 5, 5, 5));
        
        Label ltitre = new Label("Titre:"); ltitre.setMinWidth(100);
        TextField ttitre = new TextField (); ttitre.setMinWidth(200);
        HBox hb2 = new HBox();
        hb2.getChildren().addAll(ltitre, ttitre);
        hb2.setSpacing(10); hb2.setPadding(new Insets(5, 5, 5, 5));
        
        Label lediteur = new Label("Editeur:"); lediteur.setMinWidth(100);
        TextField tediteur = new TextField (); tediteur.setMinWidth(200);
        HBox hb3 = new HBox();
        hb3.getChildren().addAll(lediteur, tediteur);
        hb3.setSpacing(10); hb3.setPadding(new Insets(5, 5, 5, 5));
        
        Label lgenre = new Label("Genre:"); lgenre.setMinWidth(100);
        TextField tgenre = new TextField (); tgenre.setMinWidth(200);
        HBox hb4 = new HBox();
        hb4.getChildren().addAll(lgenre, tgenre);
        hb4.setSpacing(10); hb4.setPadding(new Insets(5, 5, 5, 5));
        
        Label ldachat = new Label("Date d'achat:"); ldachat.setMinWidth(100);
        DatePicker tdachat = new DatePicker (); tdachat.setMinWidth(200); tdachat.setEditable(false);
        HBox hb5 = new HBox();
        hb5.getChildren().addAll(ldachat, tdachat);
        hb5.setSpacing(10); hb5.setPadding(new Insets(5, 5, 5, 5));
        
        Label lpachat = new Label("Prix d'achat:"); lpachat.setMinWidth(100);
        TextField tpachat = new TextField (); tpachat.setMinWidth(200);
        HBox hb6 = new HBox();
        hb6.getChildren().addAll(lpachat, tpachat);
        hb6.setSpacing(10); hb6.setPadding(new Insets(5, 5, 5, 5));
        
        HBox hb7 = new HBox();
        Button add = new Button("Add"); add.setMinWidth(100);
        hb7.getChildren().add(add);
        hb7.setSpacing(10); hb7.setPadding(new Insets(30, 5, 5, 115));
        
        // Create VBox
        VBox vb = new VBox();
        vb.getChildren().addAll(hb0, hb1, hb2, hb3, hb4, hb5, hb6, hb7);
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
				else if (tauteur.getText().isEmpty()) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez un auteur");
					alert.setContentText("Auteur manquant");
					alert.showAndWait();
				}
				else if (ttitre.getText().isEmpty()) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez un titre");
					alert.setContentText("Titre manquant");
					alert.showAndWait();
				}
				else if (tediteur.getText().isEmpty()) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez un editeur");
					alert.setContentText("Editeur manquant");
					alert.showAndWait();
				}
				else if (tgenre.getText().isEmpty()) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez un genre");
					alert.setContentText("genre manquant");
					alert.showAndWait();
				}
				else if (!tpachat.getText().isEmpty() && !isANumber(tpachat.getText())){
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez un prix d'achat valide (entier)");
					alert.setContentText("Prix d'achat invalide");
					alert.showAndWait();
				}
				else {
					String daten;
					if (tdachat.getValue() == null)
						daten = "NULL";
					else
						daten = "\"" + tdachat.getValue().toString() + "\"";
					
					BookModel bm = new BookModel(tref.getText(), tauteur.getText(), ttitre.getText(), tediteur.getText(), tgenre.getText(), daten, tpachat.getText());
					
					try {
						if (AddBookController.findpk(bm)) {
							alert.setTitle("Erreur");
							alert.setHeaderText("Saisissez une référence unique");
							alert.setContentText("Référence deja existante");
							alert.showAndWait();
						}
						else if (bm.getRefLivre().length() > 6) {
							alert.setTitle("Erreur");
							alert.setHeaderText("Saisissez une référence qui ne dépasse pas 6 caractères");
							alert.setContentText("Référence trop longue");
							alert.showAndWait();
						}
						else {
							AddBookController.update(bm);
							Stage owner = (Stage) window.getOwner();
							BookView bv = new BookView();
							bv.start(owner);
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

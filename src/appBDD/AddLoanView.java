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

public class AddLoanView extends Application {
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage window) throws Exception {
		try {
			// Create Fields
			TextField tnumpret = new TextField ();
			ObservableList<String> livreoptions = AddLoanController.getRef("RefLivre", "Livre");
			ComboBox<String> treflivre = new ComboBox<>(livreoptions);
			ObservableList<String> contactoptions = AddLoanController.getRef("RefContact", "Adherent");
			ComboBox<String> trefcontact = new ComboBox<>(contactoptions);
			DatePicker tdatepret = new DatePicker ();
			DatePicker tdateretour = new DatePicker ();
			
			// Create Stage
	        window.setWidth(400);
	        window.setHeight(340);
	        window.setTitle("Ajout Prêt");
	        
	        // Create Nodes + HBoxes
	        Label lnumpret = new Label("Numéro Prêt:"); lnumpret.setMinWidth(100);
	        tnumpret.setMinWidth(200);
	        HBox hb0 = new HBox();
	        hb0.getChildren().addAll(lnumpret, tnumpret);
	        EventHandler<KeyEvent> hand = e -> {
	        	TextField x = (TextField) e.getSource();
	        	String tempText;
	        	if (x.getLength() > 1) {
	        		int pos = x.getCaretPosition();
	        		tempText = x.getText().substring(0, 2);
	        		x.clear();
	            	x.setText(tempText);
	            	x.positionCaret(pos);
	        	}
	        };
	        tnumpret.setOnKeyPressed(hand); tnumpret.setOnKeyReleased(hand);
	        hb0.setSpacing(10); hb0.setPadding(new Insets(5, 5, 5, 5));
	        
	        Label lreflivre = new Label("Ref. Livre:"); lreflivre.setMinWidth(100);
	        treflivre.setMinWidth(200);
	        HBox hb1 = new HBox();
	        hb1.getChildren().addAll(lreflivre, treflivre);
	        treflivre.setEditable(false);
	        hb1.setSpacing(10); hb1.setPadding(new Insets(5, 5, 5, 5));
	        
	        Label lrefcontact = new Label("Ref. Contact:"); lrefcontact.setMinWidth(100);
	        trefcontact.setMinWidth(200);
	        HBox hb2 = new HBox();
	        hb2.getChildren().addAll(lrefcontact, trefcontact);
	        trefcontact.setEditable(false);
	        hb2.setSpacing(10); hb2.setPadding(new Insets(5, 5, 5, 5));
	        
	        Label ldatepret = new Label("Date Prêt:"); ldatepret.setMinWidth(100);
	        tdatepret.setMinWidth(200);
	        HBox hb3 = new HBox();
	        hb3.getChildren().addAll(ldatepret, tdatepret);
	        tdatepret.setEditable(false);
	        hb3.setSpacing(10); hb3.setPadding(new Insets(5, 5, 5, 5));
	        
	        Label ldateretour = new Label("Date Retour:"); ldateretour.setMinWidth(100);
	        tdateretour.setMinWidth(200);
	        HBox hb4 = new HBox();
	        hb4.getChildren().addAll(ldateretour, tdateretour);
	        tdateretour.setEditable(false);
	        hb4.setSpacing(10); hb4.setPadding(new Insets(5, 5, 5, 5));
	        
	        HBox hb5 = new HBox();
	        Button add = new Button("Add"); add.setMinWidth(100);
	        hb5.getChildren().add(add);
	        hb5.setSpacing(10); hb5.setPadding(new Insets(30, 5, 5, 115));
	        
	        // Create VBox
	        VBox vb = new VBox();
	        vb.getChildren().addAll(hb0, hb1, hb2, hb3, hb4, hb5);
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
				String dater = "NULL";
				if (tnumpret.getText().isEmpty()) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez le numéro du prêt");
					alert.setContentText("Numéro du prêt manquant");
					alert.showAndWait();
				}
				else if (treflivre.getValue() == null) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez la référence du livre");
					alert.setContentText("Référence du livre manquante");
					alert.showAndWait();
				}
				else if (trefcontact.getValue() == null) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez la référence du contact");
					alert.setContentText("Référence du contact manquante");
					alert.showAndWait();
				}
				else if (tdatepret.getValue() == null) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez la date du prêt");
					alert.setContentText("Date du prêt manquante");
					alert.showAndWait();
				}
				else if (!tnumpret.getText().isEmpty() && !isANumber(tnumpret.getText())) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez un numéro de prêt avec des chiffres uniquement");
					alert.setContentText("Numéro de prêt invalide");
					alert.showAndWait();
				}
				else if (!(trefcontact.getValue() == null) && !isANumber(trefcontact.getValue())) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez une référence contact avec des chiffres uniquement");
					alert.setContentText("Format de référence contact invalide");
					alert.showAndWait();
				}
				else if ((tdateretour.getValue() != null) && (tdatepret.getValue().isAfter(tdateretour.getValue()))) {
					alert.setTitle("Erreur");
					alert.setHeaderText("Saisissez une date de retour supérieure ou égale à la date de prêt");
					alert.setContentText("Date de retour invalide");
					alert.showAndWait();
				}
				else {
					if (tdateretour.getValue() != null)
						dater = "\"" + tdateretour.getValue().toString() + "\"";
					LoanModel lm = new LoanModel(tnumpret.getText(), trefcontact.getValue(), treflivre.getValue(), tdatepret.getValue().toString(), dater);
					
					try {
						if (AddLoanController.findpk(lm)) {
							alert.setTitle("Erreur");
							alert.setHeaderText("Saisissez un numéro de prêt unique");
							alert.setContentText("Prêt deja existant");
							alert.showAndWait();
						}
						else if (AddLoanController.indispo(lm)) {
							alert.setTitle("Erreur");
							alert.setHeaderText("Livre indisponible");
							alert.setContentText("Livre deja loué");
							alert.showAndWait();
						}
						else if (AddLoanController.trop(lm)) {
							alert.setTitle("Erreur");
							alert.setHeaderText("La personne a empreinté deja 3 livres qu'elle n'a pas rendu");
							alert.setContentText("Prêt supplementaire impossible");
							alert.showAndWait();
						}
						else if (AddLoanController.retard(lm)) {
							alert.setTitle("Erreur");
							alert.setHeaderText("La personne a un livre en retard qu'elle n'a pas rendu");
							alert.setContentText("Prêt supplementaire impossible");
							alert.showAndWait();
						}
						else {
							AddLoanController.update(lm);
							Stage owner = (Stage) window.getOwner();
							LoanView lv = new LoanView();
							lv.start(owner);
							window.close();
						}
					} catch (Exception e2) {
						//e2.printStackTrace();
					}
				}
			});
		} catch (Exception e) {
			//e.printStackTrace();
		}
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

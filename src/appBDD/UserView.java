package appBDD;

import java.sql.*;
import java.util.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class UserView extends Application {
	// Fields
	String lastVille = "";
	ResultSet result;
	List<String> columns = new ArrayList<String>();
	TableView<UserModel> table;
	List<CheckBox> cbList = new ArrayList<>();
	String[] orderCol = new String[8];
	Button btp1 = new Button();
	Button btp2 = new Button();
	Button btp2b = new Button();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage window) throws Exception {
		try {
			// Prepare Columns
			columns.add("RefContact");
			columns.add("Titre"); orderCol[0] = "Titre";
			columns.add("Nom"); orderCol[1] = "Nom";
			columns.add("Prenom"); orderCol[2] = "Prenom";
			columns.add("Adresse"); orderCol[3] = "Adresse";
			columns.add("Ville"); orderCol[4] = "Ville";
			columns.add("CodePostal"); orderCol[5] = "CodePostal";
			columns.add("NumeroTelephone"); orderCol[6] = "NumeroTelephone";
			columns.add("Adherent"); orderCol[7] = "Adherent";
			
			// Prepare Checkboxes
			for (int i = 0; i < 8; i++)
		    	cbList.add(new CheckBox());
			for (int i = 0; i < cbList.size(); i++)
				cbList.get(i).setSelected(true);
			
			// Launch first view
			select (window, lastVille, columns);
		}
		catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	private void select(Stage window, String ville, List<String> columns) throws SQLException {
		// Execute Select Query
		result = UserController.execute(columns, ville);
		
		// Create Stage
        window.setMinHeight(200);
        window.sizeToScene();
        window.setTitle("Adherents");
        
        				// Create & Set Columns
        				List<TableColumn> tcList = new ArrayList<TableColumn>();
        				for (int i = 1; i <= result.getMetaData().getColumnCount() ; i++)
        					tcList.add(new TableColumn(result.getMetaData().getColumnName(i)));
        				
        				// Populate Columns
        				for (int i = 0; i < tcList.size(); i++)
        					tcList.get(i).setCellValueFactory(new PropertyValueFactory<UserModel, String>(tcList.get(i).getText()));
        				ObservableList<UserModel> data = FXCollections.observableArrayList();
        				while (result.next()) {
        					data.add(new UserModel(result));
        				}
        				
			        // Create & Set Table
			        table = new TableView<UserModel>();
			        for (TableColumn tc : tcList)
			        	table.getColumns().add(tc);
			        table.setItems(data);
			        
			        // Create & Set View Buttons
			        HBox hbox0 = new HBox();
			        hbox0.setPadding(new Insets(5, 5, 5, 5));
			        hbox0.setSpacing(5);
			        btp1 = new Button("Livres");
			        btp2 = new Button("Adherents");
			        btp2b = new Button("Prêts");
			        
			        // Create & Set + Menu
			        MenuBar menuBar = new MenuBar();
			        menuBar.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
			        Menu menu = new Menu("+");
			        MenuItem mlivre = new MenuItem("Livres");
			        MenuItem madherent = new MenuItem("Adherents");
			        MenuItem mpret = new MenuItem("Prêts");
			        menu.getItems().addAll(mlivre, madherent, mpret);
			        menuBar.getMenus().addAll( menu);
			        Region spacer = new Region();
			        HBox.setHgrow(spacer, Priority.SOMETIMES);
			        hbox0.getChildren().addAll(btp1, btp2, btp2b, spacer, menuBar);
			        
			        // Create & Set Checkboxes
			        HBox hbox1 = new HBox();
			        hbox1.setPadding(new Insets(5, 5, 5, 5));
			        hbox1.setSpacing(5);
			        cbList.get(0).setText("Titre");
			        cbList.get(1).setText("Nom");
			        cbList.get(2).setText("Prenom");
			        cbList.get(3).setText("Adresse");
			        cbList.get(4).setText("Ville");
			        cbList.get(5).setText("CodePostal");
			        cbList.get(6).setText("NumeroTelephone");
			        cbList.get(7).setText("Adherent");
			        for (int i = 0; i < cbList.size(); i++) {
			        	cbList.get(i).setPadding(new Insets(0, 10, 0, 0));
			        	hbox1.getChildren().add(cbList.get(i));
			        }
			        
			        // Create & Set Search Bar
			        HBox hbox2 = new HBox();
			        hbox2.setPadding(new Insets(5, 5, 5, 5));
			        hbox2.setSpacing(5);
			        TextField txt = new TextField();
			        txt.setPrefWidth(650);
			        txt.setPromptText("Saisissez la ville");
			        Button bt1 = new Button("Delete");
			        Button bt2 = new Button("Select");
			        hbox2.getChildren().addAll(bt1, txt, bt2);
			        
			        // Group Checkboxes & Search Bar
			        FlowPane flow = new FlowPane();
			        flow.getChildren().addAll(hbox1, hbox2);
			        flow.setAlignment(Pos.CENTER);
		        
		        // Create & Set RootPane
		        BorderPane parent = new BorderPane();
		        parent.setTop(hbox0);
		        parent.setCenter(table);
		        parent.setBottom(flow);
	        
	        // Create & Set Scene
	        Scene scene = new Scene(parent);
	        window.setScene(scene);
        
        // Show Stage
        window.show();
        btp2.requestFocus();
        
        // Handle Delete Button
        bt1.setOnAction(e -> {
			try {
				if (table.getSelectionModel().isSelected(table.getSelectionModel().getSelectedIndex()))
					UserController.delete(table.getSelectionModel().getSelectedItem());
				select (window, lastVille, columns);
			} catch (SQLException e1) {
				//e1.printStackTrace();
			}
			finally {
				txt.clear();
			}
        });
        
        // Handle Select Button
        bt2.setOnAction(e -> {
        	try {
	        	lastVille = txt.getText();
				select (window, lastVille, columns);
				txt.clear();
			} catch (SQLException e1) {
				//e1.printStackTrace();
			}
        });
        
        // Handle Checkboxes
        cbList.get(0).setOnAction(e -> {handleCb(window, 0, "Titre");});
        cbList.get(1).setOnAction(e -> {handleCb(window, 1, "Nom");});
        cbList.get(2).setOnAction(e -> {handleCb(window, 2, "Prenom");});
        cbList.get(3).setOnAction(e -> {handleCb(window, 3, "Adresse");});
        cbList.get(4).setOnAction(e -> {handleCb(window, 4, "Ville");});
        cbList.get(5).setOnAction(e -> {handleCb(window, 5, "CodePostal");});
        cbList.get(6).setOnAction(e -> {handleCb(window, 6, "NumeroTelephone");});
        cbList.get(7).setOnAction(e -> {handleCb(window, 7, "Adherent");}); 
        
        // Handle Books Button
        btp1.setOnAction(e -> {
			try {
				BookView bk = new BookView();
				bk.start(window);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
        });
        
        // Handle Loans Button
        btp2b.setOnAction(e -> {
			try {
				LoanView lk = new LoanView();
				lk.start(window);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
        });
        
        // Handle Add Buttons
        EventHandler<ActionEvent> handleBook = e -> {
        	try {
				AddBookView av = new AddBookView();
				Stage window2 = new Stage();
				window2.initOwner(window);
				av.start(window2);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
        };
        mlivre.setOnAction(handleBook);
        
        EventHandler<ActionEvent> handleUser = e -> {
        	try {
				AddUserView av = new AddUserView();
				Stage window2 = new Stage();
				window2.initOwner(window);
				av.start(window2);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
        };
        madherent.setOnAction(handleUser);
        
        EventHandler<ActionEvent> handleLoan = e -> {
        	try {
				AddLoanView av = new AddLoanView();
				Stage window2 = new Stage();
				window2.initOwner(window);
				av.start(window2);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
        };
        mpret.setOnAction(handleLoan);
	} // Close select()
	
	// Method for handling Checkboxes
	private void handleCb (Stage window, int index, String name) {
		try {
			if (!cbList.get(index).isSelected()) {
				orderCol[index] = "";
	    		columns.remove(name);
	    		cbList.get(index).setSelected(false);
	    	}
			else {
				orderCol[index] = name;
				columns.clear();
				for (String x : orderCol) {
					columns.add(columns.size(), x);
				};
				cbList.get(index).setSelected(true);
			}
			select (window, lastVille, columns);
		} catch (Exception e1) {
			//e1.printStackTrace();
		}
    }
}

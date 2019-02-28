package appBDD;

import java.sql.*;
import java.util.*;

import javafx.application.*;
import javafx.collections.*;
import javafx.event.*;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.*;
import javafx.scene.layout.*;
import javafx.stage.*;

public class LoanView extends Application {
	// Fields
	String LastRefPret = "";
	ResultSet result;
	List<String> columns = new ArrayList<String>();
	TableView<LoanModel> table;
	List<CheckBox> cbList = new ArrayList<>();
	String[] orderCol = new String[4];
	Button btp5 = new Button();
	Button btp6 = new Button();
	Button btp7 = new Button();
	
	public static void main(String[] args) {
		launch(args);
	}
	
	public void start(Stage window) throws SQLException {
		try {
			// Prepare Columns
			columns.add("NumPret");
			columns.add("RefLivre"); orderCol[0] = "RefLivre";
			columns.add("RefContact"); orderCol[1] = "RefContact";
			columns.add("DateDuPret"); orderCol[2] = "DateDuPret";
			columns.add("DateDuRetour"); orderCol[3] = "DateDuRetour";
			
			// Prepare Checkboxes
			for (int i = 0; i < 4; i++)
		    	cbList.add(new CheckBox());
			for (int i = 0; i < cbList.size(); i++)
				cbList.get(i).setSelected(true);
			
			// Launch first view
			select (window, LastRefPret, columns);
		}
		catch (SQLException e) {
			//e.printStackTrace();
		}
	}
	
	private void select(Stage window, String RefPret, List<String> columns) throws SQLException {

		// Execute Select Query
		result = LoanController.execute(columns, RefPret);
		
		// Create Stage
        window.setMinHeight(200);
        window.sizeToScene();
        window.setTitle("Prêts");
        
        				// Create & Set Columns
        				List<TableColumn> tcList = new ArrayList<TableColumn>();
        				for (int i = 1; i <= result.getMetaData().getColumnCount() ; i++)
        					tcList.add(new TableColumn(result.getMetaData().getColumnName(i)));
        				
        				// Populate Columns
        				for (int i = 0; i < tcList.size(); i++)
        					tcList.get(i).setCellValueFactory(new PropertyValueFactory<LoanModel, String>(tcList.get(i).getText()));
        				ObservableList<LoanModel> data = FXCollections.observableArrayList();
        				while (result.next()) {
        					data.add(new LoanModel(result));
        				}
        				
			        // Create & Set Table
			        table = new TableView<LoanModel>();
			        for (TableColumn tc : tcList)
			        	table.getColumns().add(tc);
			        table.setItems(data);
			        
			        // Create & Set View Buttons
			        HBox hbox0 = new HBox();
			        hbox0.setPadding(new Insets(5, 5, 5, 5));
			        hbox0.setSpacing(5);
			        btp5 = new Button("Livres");
			        btp6 = new Button("Adherents");
			        btp7 = new Button("Prêts");
			        
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
			        hbox0.getChildren().addAll(btp5, btp6, btp7, spacer, menuBar);
			        
			        // Create & Set Checkboxes
			        HBox hbox1 = new HBox();
			        hbox1.setPadding(new Insets(5, 5, 5, 5));
			        hbox1.setSpacing(5);
			        cbList.get(0).setText("RefLivre");
			        cbList.get(1).setText("RefContact");
			        cbList.get(2).setText("DateDuPret");
			        cbList.get(3).setText("DateDuRetour");
			        for (int i = 0; i < cbList.size(); i++) {
			        	cbList.get(i).setPadding(new Insets(0, 20, 0, 0));
			        	hbox1.getChildren().add(cbList.get(i));
			        }
			        
			        // Create & Set Search Bar
			        HBox hbox2 = new HBox();
			        hbox2.setPadding(new Insets(5, 5, 5, 5));
			        hbox2.setSpacing(5);
			        TextField txt = new TextField();
			        txt.setPrefWidth(650);
			        txt.setPromptText("Saisissez la référence du livre");
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
        btp7.requestFocus();
        
        // Handle Delete Button
        bt1.setOnAction(e -> {
			try {
				if (table.getSelectionModel().isSelected(table.getSelectionModel().getSelectedIndex()))
					LoanController.delete(table.getSelectionModel().getSelectedItem());
				select (window, LastRefPret, columns);
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
	        	LastRefPret = txt.getText();
				select (window, LastRefPret, columns);
				txt.clear();
			} catch (SQLException e1) {
				//e1.printStackTrace();
			}
        });
        
        // Handle Checkboxes
        cbList.get(0).setOnAction(e -> {handleCb(window, 0, "RefLivre");});
        cbList.get(1).setOnAction(e -> {handleCb(window, 1, "RefContact");});
        cbList.get(2).setOnAction(e -> {handleCb(window, 2, "DateDuPret");});
        cbList.get(3).setOnAction(e -> {handleCb(window, 3, "DateDuRetour");});
        
        // Handle Books Button
        btp5.setOnAction(e -> {
			try {
				BookView bk = new BookView();
				bk.start(window);
			} catch (Exception e1) {
				//e1.printStackTrace();
			}
        });
        
        // Handle Users Button
        btp6.setOnAction(e -> {
			try {
				UserView uv = new UserView();
				uv.start(window);
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
			select (window, LastRefPret, columns);
		} catch (Exception e1) {
			//e1.printStackTrace();
		}
    }
	
}

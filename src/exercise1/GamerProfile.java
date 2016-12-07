package exercise1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GamerProfile extends Application {
	private TextField userName, firstName, lastName, address, postalCode, province, phoneNumber, playingDate, score;
	private ObservableList<Player> players = FXCollections.observableArrayList();
	private List<Game> playersListOfGames = new ArrayList<>();
	
	private Button btnSubmit = new Button("Submit");
	private Button btnDisplay = new Button("Display Information");
	private Button btnUpdate = new Button("Update Information");
	private ComboBox<String> listOfGames = new ComboBox<>();
	
	private PreparedStatement insertPlayerStatement;
	private Connection conn;
	
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;";
	private static final String DATABASE_USER = "lab5user";
	private static final String DATABASE_PASSWORD = "password";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
	
		//initialize declared TextFields
		userName = new TextField();
		firstName = new TextField();
		lastName = new TextField();
		address = new TextField();
		postalCode = new TextField();
		province = new TextField();
		phoneNumber = new TextField();
		playingDate = new TextField();
		score = new TextField();
		
		GridPane formPane = new GridPane();
		GridPane.setConstraints(formPane, 10, 10, 10 , 10);
		btnSubmit.setAlignment(Pos.BASELINE_CENTER);
			formPane.setPadding(new Insets(10,10,10,10));
			formPane.setHgap(10);
			formPane.setVgap(10);
			//Add labels to declared GridPane formPane
			formPane.add(new Label("User Name: "), 0, 0);
			formPane.add(new Label("First Name: "), 0, 1);
			formPane.add(new Label("Last Name: "),0, 2);
			formPane.add(new Label("Address: "), 0, 3);
			formPane.add(new Label("Postal Code: "), 0, 4);
			formPane.add(new Label("Province: "), 0, 5);
			formPane.add(new Label("Phone Number: "), 0, 6);
			
			formPane.add(new Label("What games have you played: "), 0, 10);
			formPane.add(new Label("Playing Date: "), 0, 11);
			formPane.add(new Label("Score: "), 0, 12);
		
			//Add input TextBoxes to formPane
			formPane.add(userName, 1, 0);	
			formPane.add(firstName, 1, 1);		
			formPane.add(lastName, 1, 2);
			formPane.add(address, 1, 3);
			formPane.add(postalCode, 1, 4);
			formPane.add(province, 1, 5);
			formPane.add(phoneNumber, 1, 6);
			formPane.add(listOfGames, 1, 10);
			formPane.add(playingDate, 1, 11);
			formPane.add(score, 1, 12);
			
			
		//Table of players
		TableView<Player> playerTable = new TableView<>();
		TableColumn<Player, String> colUserName = new TableColumn<>("User Name");
		colUserName.setMinWidth(100);
		colUserName.setCellValueFactory(new PropertyValueFactory<>("userName"));
		
		TableColumn<Player, String> colFirstName = new TableColumn<>("First Name");
		colFirstName.setMinWidth(100);
		colFirstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		
		TableColumn<Player, String> colLastName = new TableColumn<>("Last Name");
		colLastName.setMinWidth(100);
		colLastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		
		TableColumn<Player, String> colAddress = new TableColumn<>("Address");
		colAddress.setMinWidth(100);
		colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));
		
		TableColumn<Player, String> colPostalCode = new TableColumn<>("Postal Code");
		colPostalCode.setMinWidth(100);
		colPostalCode.setCellValueFactory(new PropertyValueFactory<>("postalCode"));
		
		TableColumn<Player, String> colProvince = new TableColumn<>("Province");
		colProvince.setMinWidth(100);
		colProvince.setCellValueFactory(new PropertyValueFactory<>("province"));
		
		TableColumn<Player, String> colPhoneNumber = new TableColumn<>("Phone Number");
		colPhoneNumber.setMinWidth(150);
		colPhoneNumber.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
		
		playerTable.setItems(players);
		playerTable.getColumns().addAll(colUserName, colFirstName, colLastName, colAddress, colPostalCode, colProvince, colPhoneNumber);
		
		//Display players list of played games
		ListView<String> playersGames = new ListView<>();
		
		
		GridPane buttonPane = new GridPane();
		buttonPane.setPadding(new Insets(10,10,10,10));
		buttonPane.setHgap(10);
		buttonPane.add(btnSubmit,0,0);
		buttonPane.add(btnUpdate,1,0);
		buttonPane.add(btnDisplay,2,0);

		
		//Main Application layout
		BorderPane mainLayout = new BorderPane();
			mainLayout.setPadding(new Insets(10, 10, 10, 10));
			mainLayout.setLeft(formPane);
			mainLayout.setCenter(playerTable);
			mainLayout.setRight(playersGames);
			mainLayout.setBottom(buttonPane);
			
		//Add event handlers to submit button
		btnSubmit.setOnAction(e -> {
			try {
				// Database driver
				Class.forName(DRIVER);
				// Set database connection options
				conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
				
				//Create the prepared statement for inserting a player
				insertPlayerStatement = conn.prepareStatement("INSERT INTO Player (player_id, first_name, last_name, address, postal_code, province, phone_number) VALUES (?, ?, ?, ?, ?, ?, ?)");
				
				//Parameters for the statement
				insertPlayerStatement.setString(1, userName.getText());
				insertPlayerStatement.setString(2, firstName.getText());
				insertPlayerStatement.setString(3, lastName.getText());
				insertPlayerStatement.setString(4, address.getText());
				insertPlayerStatement.setString(5, postalCode.getText());
				insertPlayerStatement.setString(6, province.getText());
				insertPlayerStatement.setString(7, phoneNumber.getText());
				
				//Execute the statement
				insertPlayerStatement.executeUpdate();
				
				//Alert message on player insert success
				Alert alert = new Alert(AlertType.INFORMATION, "Player has been successfully added!");
				alert.setHeaderText("Information");
				alert.setTitle("Player Added");
				alert.show();
			}
			catch (Exception ex) {
				// Alert message on error
				Alert alert = new Alert(AlertType.ERROR, ex.getMessage());
				alert.setHeaderText("Error");
				alert.setTitle("Error");
				alert.show();
			}
			finally {
				// Close connection and statement; must be surrounded with try/catch
				try {
					insertPlayerStatement.close();
					conn.close();
				}
				catch (Exception ex) {}
			}	
		});
		
		Scene scene = new Scene(mainLayout);
			primaryStage.setMinHeight(600);
			primaryStage.setHeight(500);
			primaryStage.setMinWidth(1024);
			primaryStage.setTitle("Gamer Profile");
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

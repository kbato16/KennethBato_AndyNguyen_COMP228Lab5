package exercise1;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

public class GamerProfile extends Application {
	private TextField firstName, lastName, address, postalCode, province, phoneNumber;
	private Button btnSubmit = new Button("Submit");
	private Player player;
	private List<String> listOfGames = new ArrayList<String>();
	
	private PreparedStatement insertPlayerStatement;
	private Connection conn;
	
	private static final String DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	private static final String DATABASE_URL = "jdbc:sqlserver://localhost:1433;";
	private static final String DATABASE_USER = "lab5user";
	private static final String DATABASE_PASSWORD = "password";
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		//initialize declared TextFields
		firstName = new TextField();
		lastName = new TextField();
		address = new TextField();
		postalCode = new TextField();
		province = new TextField();
		phoneNumber = new TextField();
		
		GridPane formPane = new GridPane();
		GridPane.setConstraints(formPane, 10, 10, 10 , 10);
		formPane.setPadding(new Insets(10,10,10,10));
		formPane.setHgap(10);
		formPane.setVgap(10);
		//Add labels to declared GridPane formPane
		formPane.add(new Label("First Name: "), 0, 0);
		formPane.add(new Label("Last Name: "),0, 1);
		formPane.add(new Label("Address: "), 0, 2);
		formPane.add(new Label("Postal Code: "), 0, 3);
		formPane.add(new Label("Province: "), 0, 4);
		formPane.add(new Label("Phone Number: "), 0, 5);
		
		//Add input textboxes to formPane
		formPane.add(firstName, 1, 0);		
		formPane.add(lastName, 1, 1);
		formPane.add(address, 1, 2);
		formPane.add(postalCode, 1, 3);
		formPane.add(province, 1, 4);
		formPane.add(phoneNumber, 1, 5);

		
		//Main Application layout
		BorderPane mainLayout = new BorderPane();
			mainLayout.setPadding(new Insets(10, 10, 10, 10));
			//add formPane to mainLayout
			mainLayout.setCenter(formPane);
			mainLayout.setBottom(new StackPane(btnSubmit));
			
		//Add event handlers to submit button
		btnSubmit.setOnAction(e -> {
			try {
				// Database driver
				Class.forName(DRIVER);
				// Set database connection options
				conn = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
				
				//Create the prepared statement for inserting a player
				insertPlayerStatement = conn.prepareStatement("INSERT INTO Player (first_name, last_name, address, postal_code, province, phone_number) VALUES (?, ?, ?, ?, ?, ?)");
				
				//Parameters for the statement
				insertPlayerStatement.setString(1, firstName.getText());
				insertPlayerStatement.setString(2, lastName.getText());
				insertPlayerStatement.setString(3, address.getText());
				insertPlayerStatement.setString(4, postalCode.getText());
				insertPlayerStatement.setString(5, province.getText());
				insertPlayerStatement.setString(6, phoneNumber.getText());
				
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
			primaryStage.setMinHeight(500);
			primaryStage.setHeight(500);
			primaryStage.setMinWidth(350);
			primaryStage.setTitle("Gamer Profile");
			primaryStage.setScene(scene);
			primaryStage.show();
	}

	public static void main(String[] args) {
		launch(args);
	}
}

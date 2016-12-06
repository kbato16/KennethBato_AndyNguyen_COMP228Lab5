package exercise1;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.scene.Scene;
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
		btnSubmit.setOnAction(null);
		
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

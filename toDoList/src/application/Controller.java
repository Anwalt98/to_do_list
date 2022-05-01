package application;



import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Optional;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;






public class Controller {
	static int i;
	static int j;
	boolean check;
	String numberOfGoalInSQL;
	
	@FXML
	public GridPane gp ;
	public TextField nameTextField;
	public TextField passField;
	private Stage stage;
	private Scene scene;
	private Parent root;
	public Label labelLogedOut;
	public Label nameLabel;
	public Button logoutButton;
	public Button createGoalButton;
	public Button refBut;
	public Button delBut;
	public Button registerButton;	
	public Label box;
	public Label boxR;
	public Label numBox;
	private String newUserName;
	private String password;
	public static String finalUsername;
	
 
	
	
	public void displayName(String name) {
		nameLabel.setText("You've entered, " + name);
	}
	public void  setOut(String outed) {
		labelLogedOut.setText(outed);
	}
	public void createNewGoal(ActionEvent event) throws NoSuchElementException  {
		int changable = j + 1;
		TextInputDialog txp = new TextInputDialog();
		txp.setTitle("Creating goal");
		txp.setContentText("Goal:");
		txp.setHeaderText("Enter your goal");
		Optional <String> str = txp.showAndWait();
		if (str.isPresent() && str.get()!=""   && j != 6) {
			box = new Label();
			box.setText(str.get());
			gp.add(box, 1, j);
			numBox = new Label();
			numBox.setText(Integer.toString(j+1));
			gp.add(numBox,0,j);
			
			
			numberOfGoalInSQL = "goal" + changable;
			j++;
			User.getInstance().goals.add(str.get());
			SqlConnector.setGoals(str.get(),numberOfGoalInSQL, User.getUsername());
		}
	}
	public void deleteGoal(ActionEvent e) throws NoSuchElementException {
		TextInputDialog txp = new TextInputDialog();
		txp.setTitle("Deleting");
		txp.setContentText("Number of goal you want to delete:");
		Optional <String> str = txp.showAndWait();
		if (str.isPresent()) {
		if ((str.get().equals("6") ||str.get().equals("5")||str.get().equals("4") ||str.get().equals("3")||str.get().equals("2")||str.get().equals("1"))&& ((Integer.parseInt(str.get())) - 1) < User.getInstance().goals.size() ) {
			User.getInstance().goals.remove((Integer.parseInt(str.get())) - 1);
			SqlConnector.deleteGoalSQL(Integer.parseInt(str.get()));
			User.getInstance().goals.clear();
	SqlConnector.findGoals(finalUsername);
	drawGoals();
		}
		}
	}
	
		public void drawGoals() {
			j =0;
			
				gp.getChildren().removeAll(gp.getChildren());
			
			for (String goal : User.getInstance().goals) {
				if (goal != null) {
					
					box = new Label();
					box.setText(goal);
					gp.add(box, 1, j);
					numBox = new Label();
					numBox.setText(Integer.toString(j+1));
					gp.add(numBox,0,j);
					j++;
			}
		}
	}			
		
	
	
	

	public void logout(ActionEvent event) throws IOException {
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Log out");
		alert.setHeaderText("You're about to log out.");
		alert.setContentText("Are you sure to log out?");
		
		if (alert.showAndWait().get()== ButtonType.OK) {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene1.fxml"));
			root = loader.load();
	
			
			
		
			
			((Controller) loader.getController()).setOut("You succesfully loged out.");
			
	
			stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			
			
			
		}
	}	
	
	
	


	public void login(ActionEvent event) throws IOException {
		User.getInstance().goals.clear();
		try {
			check = SqlConnector.checkCoincidence(nameTextField.getText(), passField.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch bloc
			e.printStackTrace();
		}
		if (check == true) {
		
		finalUsername = nameTextField.getText();
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
		root = loader.load();
		j = 0;
		User.setUsername(finalUsername);
		SqlConnector.findGoals(finalUsername);
		Controller Scenecontroller2 = loader.getController();
		
		
		Scenecontroller2.displayName(finalUsername);
		Scenecontroller2.drawGoals();
		
	
		

		stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		
		}
		else {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setHeaderText("Error");
			alert.setContentText("Incorrect username and password.");
			alert.showAndWait();
		}
	}
	
	public void register(ActionEvent e) {
		
		inputName(e);
		inputPass(e);
		SqlConnector.register(getNewUserName(), getPassword());
		
		
		
	

	}
	public void usedNameAlert(ActionEvent e) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Error");
		alert.setContentText("Chosen name already exists.");
		alert.showAndWait();
		
	}


   public void inputName(ActionEvent e) throws NoSuchElementException {
	   Optional <String> str = null;
	TextInputDialog txp = new TextInputDialog();
	txp.setTitle("Create new user");
	txp.setContentText("Login:");
	txp.setContentText("Enter login");
	str = txp.showAndWait();
	
	
	
	if ( str.get()== "" ||str.isEmpty()) {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Error of nullname");
		alert.setContentText("Username cannot be empty");
		alert.showAndWait();
		inputName(e);
		
	}
	if (str.isPresent()) {
	setNewUserName(str.get());
   	}
   }

    public void inputPass(ActionEvent e) {
	TextInputDialog txp = new TextInputDialog();
	txp.setTitle("Password for user");
	txp.setContentText("Password:");
	
	
	Optional <String> str = txp.showAndWait();
	if (str.isEmpty() || str.get()== "") {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setHeaderText("Error of nullpass");
		alert.setContentText("Password cannot be empty");
		alert.showAndWait();
		inputPass(e);
	}
	if (str.isPresent()) {
	setPassword(str.get());
   	}
    }
    
	public String getNewUserName() {
		return newUserName;
	}
	public void setNewUserName(String newUserName) {
		
		this.newUserName = newUserName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	
   
}



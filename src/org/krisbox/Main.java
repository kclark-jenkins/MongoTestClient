package org.krisbox;
	
import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.Date;

import org.krisbox.utils.impl.MongoAuthenticationTypes;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;


public class Main extends Application {
	private boolean hasPinged = false;
	//private MongoWrapper mw = new MongoContainer();
	@Override
	public void start(Stage primaryStage) {
		try {
			GridPane grid = new GridPane();
			
			GridPane gridAuthentication = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			gridAuthentication.setHgap(10);
			gridAuthentication.setVgap(2);
			gridAuthentication.setPadding(new Insets(25,25,25,25));
			Text scenetitle = new Text("MongoDB Kerberos Test Client");
			
			scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
			gridAuthentication.add(scenetitle, 0, 0, 2, 1);

			Label authTypes = new Label("Select an authentication type:");
			gridAuthentication.add(authTypes, 0, 1);
			
			final ToggleGroup group = new ToggleGroup();
		    RadioButton btnAuthScram = new RadioButton("SCRAM-SHA-1");
		    RadioButton btnAuthMongoCr = new RadioButton("MONGODB-CR");
		    RadioButton btnAuth509 = new RadioButton("x.509");
		    RadioButton btnAuthLDAP = new RadioButton("LDAP Proxy");
		    RadioButton btnAuthKerberos = new RadioButton("Kerberos");
		    
		    btnAuthScram.setUserData(MongoAuthenticationTypes.SCRAM_SHA1);
		    btnAuthMongoCr.setUserData(MongoAuthenticationTypes.MONGODB_CR);
		    btnAuth509.setUserData(MongoAuthenticationTypes.X509);
		    btnAuthLDAP.setUserData(MongoAuthenticationTypes.LDAP_PROXY);
		    btnAuthKerberos.setUserData(MongoAuthenticationTypes.KERBEROS);
		    
		    btnAuthScram.setToggleGroup(group);
		    btnAuthMongoCr.setToggleGroup(group);
		    btnAuth509.setToggleGroup(group);
		    btnAuthLDAP.setToggleGroup(group);
		    btnAuthKerberos.setToggleGroup(group);
		    
		    btnAuthKerberos.setSelected(true);
		    
		    btnAuthScram.setDisable(true);
		    btnAuthMongoCr.setDisable(true);
		    btnAuth509.setDisable(true);
		    btnAuthLDAP.setDisable(true);
		    
			gridAuthentication.add(btnAuthScram, 0, 4);
			gridAuthentication.add(btnAuthMongoCr, 1, 4);
			gridAuthentication.add(btnAuth509, 0, 6);
			gridAuthentication.add(btnAuthLDAP, 1, 6);
			gridAuthentication.add(btnAuthKerberos, 0,  8);
			
			Label lblAuthTypeMessage = new Label("");
			gridAuthentication.add(lblAuthTypeMessage, 0, 10);
			
			grid.add(gridAuthentication, 0, 0);
			
			// Kerberos Grid
			GridPane gridKerberos = new GridPane();
			gridKerberos.setHgap(10);
			gridKerberos.setVgap(2);
			gridKerberos.setPadding(new Insets(0,25,25,25));
			Label         lblPrincipal = new Label("Kerberos Principal:");
			Label         lblPassword  = new Label("Password:");
			Label		  lblKrb5	   = new Label("krb5.conf location");
			TextField     txtPrincipal = new TextField();
			PasswordField txtPassword  = new PasswordField();
			CheckBox 	  cbPassword   = new CheckBox("Use password");
			TextField     txtKrb5      = new TextField("Please select your krb5.conf...");
			Button 		  btnKrb5      = new Button("Browse");
			
			txtKrb5.setStyle("-fx-text-fill: red;");
			
			HBox hbKrb5 = new HBox(10);
			hbKrb5.setAlignment(Pos.BOTTOM_LEFT);
			hbKrb5.getChildren().add(btnKrb5);
			
			lblPrincipal.setMinWidth(130);
			
			gridKerberos.add(lblPrincipal, 0, 0);
			gridKerberos.add(txtPrincipal, 1, 0);
			gridKerberos.add(lblPassword,  0, 1);
			gridKerberos.add(txtPassword,  1, 1);
			gridKerberos.add(cbPassword, 3, 1);
			gridKerberos.add(lblKrb5, 0, 2);
			gridKerberos.add(txtKrb5, 1, 2);
			gridKerberos.add(hbKrb5, 3, 2);
			
			txtPassword.setDisable(true);
			
			grid.add(gridKerberos, 0,  1, 2, 1);
			
			// Host information
			GridPane gridHostInfo = new GridPane();
			gridHostInfo.setHgap(10);
			gridHostInfo.setVgap(2);
			gridHostInfo.setPadding(new Insets(0,25,25,25));
			Label  lblHostname    = new Label("Hostname");
			Label  lblPort        = new Label("Port");
			Button btnPingHost    = new Button("Test Connection");
			
			TextField txtHostname = new TextField();
			TextField txtPort     = new TextField();
			
			
			HBox hbBtn = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btnPingHost);
			
			lblHostname.setMinWidth(130);
			
			gridHostInfo.add(lblHostname, 0, 0);
			gridHostInfo.add(txtHostname, 1, 0);
			gridHostInfo.add(lblPort, 0, 1);
			gridHostInfo.add(txtPort, 1, 1);
			gridHostInfo.add(btnPingHost, 1, 2);
			GridPane.setHalignment(gridHostInfo.getChildren().get(4), HPos.CENTER);
			
			grid.add(gridHostInfo, 0, 2, 2, 1);
			
			GridPane gridDatabase = new GridPane();
			gridDatabase.setHgap(10);
			gridDatabase.setVgap(2);
			gridDatabase.setPadding(new Insets(0,25,25,25));
			
			Label lblDatabase		 = new Label("MongoDB Database");
			Label lblCommand         = new Label("MongoDB Command");
			TextField txtCommand     = new TextField("yourCommand");
			TextField txtDatabase	 = new TextField("Database Name");
			Button btnExecuteCommand = new Button("Execute");
			HBox hbExecute = new HBox(10);
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(hbExecute);
			
			if(hasPinged == false) {
				txtCommand.setText("Test the connection first!");
				txtCommand.setDisable(true);
				txtDatabase.setDisable(true);
				btnExecuteCommand.setDisable(true);
			}
			
			lblCommand.setMinWidth(130);
			txtCommand.setMinWidth(130);
			
			gridDatabase.add(lblDatabase, 0, 0);
			gridDatabase.add(txtDatabase, 1, 0);
			gridDatabase.add(lblCommand, 0, 1);
			gridDatabase.add(txtCommand, 1, 1);
			gridDatabase.add(hbBtn, 0, 2);
			gridDatabase.add(btnExecuteCommand, 1, 2);
			GridPane.setHalignment(gridDatabase.getChildren().get(3), HPos.CENTER);
			grid.add(gridDatabase, 0, 3);
			
			GridPane gridOutput = new GridPane();
			gridOutput.setHgap(10);
			gridOutput.setVgap(2);
			gridOutput.setPadding(new Insets(0,0,0,25));
			Label    lblOutput = new Label("Console Log");
			ListView<String> lvOutput = new ListView<String>();
			Button 		  btnSaveOutput       = new Button("Save Output");
			Button 		  btnClearOutput      = new Button("Clear Output");
			
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btnSaveOutput);
			
			hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
			hbBtn.getChildren().add(btnClearOutput);
			
			lvOutput.setPrefWidth(425);
			lvOutput.setPrefHeight(100);
			
			gridOutput.add(lblOutput, 0, 0, 2, 1);
			gridOutput.add(lvOutput, 0, 1);
			
			grid.add(gridOutput, 0, 4);
			GridPane gridSave = new GridPane();
			gridSave.setPadding(new Insets(0,0,0,145));
			gridSave.setHgap(10);
			gridSave.add(btnSaveOutput, 0, 1);
			gridSave.add(btnClearOutput, 1, 1);
			grid.add(gridSave, 0, 6, 3, 1);
			
			//GridPane.setHalignment(gridHostInfo.getChildren().get(4), HPos.CENTER);
			
			group.selectedToggleProperty().addListener(new ChangeListener<Toggle>(){
				@Override
				public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle1, Toggle toggle2) {
					RadioButton chk = (RadioButton)toggle1.getToggleGroup().getSelectedToggle(); // Cast object to radio button
					
					switch((MongoAuthenticationTypes)chk.getUserData()) {
					case SCRAM_SHA1:
						lblAuthTypeMessage.setTextFill(Color.web("#FF0000"));
						lblAuthTypeMessage.setText("Please choose another authentication mechinism");
						
						gridKerberos.setVisible(false);
						break;
					case MONGODB_CR:
						lblAuthTypeMessage.setTextFill(Color.web("#FF0000"));
						lblAuthTypeMessage.setText("Please choose another authentication mechinism");
						
						gridKerberos.setVisible(false);
						break;
					case X509:
						lblAuthTypeMessage.setTextFill(Color.web("#FF0000"));
						lblAuthTypeMessage.setText("Please choose another authentication mechinism");
						
						gridKerberos.setVisible(false);
						break;
					case LDAP_PROXY:
						lblAuthTypeMessage.setTextFill(Color.web("#FF0000"));
						lblAuthTypeMessage.setText("Please choose another authentication mechinism");
						
						gridKerberos.setVisible(false);
						break;
					case KERBEROS:
						lblAuthTypeMessage.setTextFill(Color.web("#000000"));
						lblAuthTypeMessage.setText("OK");
						
						gridKerberos.setVisible(true);
						break;
					}
				}}
	        );
			
			// Checkbox listeners below
			cbPassword.selectedProperty().addListener(new ChangeListener<Boolean>() {
		        public void changed(ObservableValue<? extends Boolean> ov,
		            Boolean old_val, Boolean new_val) {
		        		if(new_val == true)
		        			txtPassword.setDisable(false);
		        		else
		        			txtPassword.clear();
		        			txtPassword.setDisable(true);
		        }
		    });
			
			
			// Button listeners below
			btnKrb5.setOnAction(
		            new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							FileChooser fileChooser = new FileChooser();
							File file = fileChooser.showOpenDialog(primaryStage);
			                	if (file != null) {
			                		txtKrb5.setText(file.getAbsolutePath());
			                		txtKrb5.setStyle("-fx-text-fill: black;");
			                	}
							}
		            });
			
			btnPingHost.setOnAction(
		            new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							if(pingHost(txtHostname.getText(), Integer.parseInt(txtPort.getText()), 6000)) {
								lvOutput.setItems(appendListView(lvOutput, "[" + new Date() + "] (" + txtHostname.getText() + ":" + txtPort.getText() + ") Ping Succesful!"));
								hasPinged = true;;
							}else{
								lvOutput.setItems(appendListView(lvOutput, "[" + new Date() + "] (" + txtHostname.getText() + ":" + txtPort.getText() + ") Ping Failed!"));
								hasPinged = false;
							}
							
							if(hasPinged) {
								txtCommand.setDisable(false);
								txtDatabase.setDisable(false);
								btnExecuteCommand.setDisable(false);
								
								txtCommand.clear();
								txtDatabase.clear();
							}else{
								txtCommand.setDisable(true);
								txtDatabase.setDisable(true);
								btnExecuteCommand.setDisable(false);
								txtCommand.setText("Test the connection first!");
							}
							
							btnSaveOutput.setDisable(false);
							btnClearOutput.setDisable(false);
						}
		            });
			
			btnSaveOutput.setOnAction(
		            new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							FileChooser fileChooser = new FileChooser();
							File file = fileChooser.showSaveDialog(primaryStage);
			                	if (file != null) {
			                		//Save file
			                		System.out.println("Saving");
			                	}
							}
		            });
			
			btnClearOutput.setOnAction(
		            new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							Alert alert = new Alert(AlertType.CONFIRMATION, "Clear the log output?", ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
							alert.showAndWait();

							if (alert.getResult() == ButtonType.YES) {
								lvOutput.getItems().clear();
								
								btnSaveOutput.setDisable(true);
								btnClearOutput.setDisable(true);
							}
						}
		            });
			
			btnExecuteCommand.setOnAction(
		            new EventHandler<ActionEvent>() {

						@Override
						public void handle(ActionEvent event) {
							if(txtCommand.getText().isEmpty() && txtDatabase.getText().isEmpty()) {
								showErrorDialog("Insufficient Information", "Missing Parameters", "Database name and command string are both missing!");
							}else if(txtCommand.getText().isEmpty()) {
								showErrorDialog("Insufficient Information", "Missing Parameters", "Command string is missing!");
							}else if(txtDatabase.getText().isEmpty()) {
								showErrorDialog("Insufficient Information", "Missing Parameters", "Database name is missing!");
							}else{
								lvOutput.setItems(appendListView(lvOutput, "[" + new Date() + "] (" + txtCommand.getText() + ")"));
								
								//try {
									//mc = new MongoConnection((MongoAuthenticationTypes)group.selectedToggleProperty().get().getUserData(),
									//		txtPrincipal.getText(), txtPassword.getText(),
									//		txtHostname.getText(), txtPort.getText(), txtDatabase.getText());
								//} catch (Exception e) {
								//	System.out.println("Caught");
								//}
							}
						}
		            });
			
			if(lvOutput.getItems().size() < 1) {
				btnSaveOutput.setDisable(true);
				btnClearOutput.setDisable(true);
			}
			
			// TODO: This
			//lvOutput.setItems(appendListView(lvOutput, "test"));
			
			Scene scene = new Scene(grid,470,675);
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			
			primaryStage.setScene(scene);
			primaryStage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	private ObservableList<String> appendListView(ListView<String> lv, String item) {
		ObservableList<String> lvItems = 
		        FXCollections.observableArrayList();
		
		lvItems.addAll(lv.getItems());
		lvItems.add(item);
		
		return lvItems;
	}
	
	private boolean pingHost(String host, int port, int timeout) {
	    try (Socket socket = new Socket()) {
	        socket.connect(new InetSocketAddress(host, port), timeout);
	        return true;
	    } catch (IOException e) {
	        return false; // Either timeout or unreachable or failed DNS lookup.
	    }
	}
	
	private void showErrorDialog(String title, String header, String message) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(message);

		alert.showAndWait();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}

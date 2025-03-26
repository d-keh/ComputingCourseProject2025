package com.example;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LoginWindow {
    //------------------------------------------------------------------
    //user data and main menu initialization
    //------------------------------------------------------------------

    private final UserData userData;
    private MainMenu menu = new MainMenu();

    public LoginWindow(UserData userData) {
        this.userData = userData;
    }

    public void show() {
        //------------------------------------------------------------------
        //creating the stage
        //------------------------------------------------------------------

        Stage loginStage = new Stage();
        
        //------------------------------------------------------------------
        //create input fields for username and password
        //------------------------------------------------------------------
        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter Password");
        
        Button loginButton = new Button("Login");
        
        //------------------------------------------------------------------
        //action for the Login button
        //------------------------------------------------------------------

        loginButton.setOnAction(event -> {
            String inputUsername = usernameField.getText();
            String inputPassword = passwordField.getText();
            
            if (inputUsername.equals(userData.getUsername()) && inputPassword.equals(userData.getPassword())) {
                System.out.println("Login successful!");
                loginStage.close(); // Close the login window
                menu.show();
            } else {
                System.out.println("Username or password is incorrect.");
            }
        });
        
        //------------------------------------------------------------------
        //layout for login window
        //------------------------------------------------------------------
        
        VBox layout = new VBox(10, new Label("Login"), usernameField, passwordField, loginButton);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene scene = new Scene(layout, 300, 200);
        loginStage.setTitle("Login Window");
        loginStage.setScene(scene);
        loginStage.show();
    }
}

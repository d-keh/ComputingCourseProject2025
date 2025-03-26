package com.example;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class SignUpWindow {
    //------------------------------------------------------------------
    //user data initialization
    //------------------------------------------------------------------

    private final UserData userData;

    public SignUpWindow(UserData userData) {
        this.userData = userData;
    }

    public void show() {
        Stage signUpStage = new Stage();
        
        //------------------------------------------------------------------
        //create input fields for username and password
        //------------------------------------------------------------------

        TextField usernameField = new TextField();
        usernameField.setPromptText("Choose a Username");
        
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Choose a Password");
        
        Button signUpButton = new Button("Sign Up");
        
        //------------------------------------------------------------------
        //action for the signup button
        //------------------------------------------------------------------

        signUpButton.setOnAction(event -> {
            String username = usernameField.getText();
            String password = passwordField.getText();
            
            if (!username.isEmpty() && !password.isEmpty()) {
                userData.setUsername(username); // Save username
                userData.setPassword(password); // Save password
                System.out.println("User signed up with Username: " + username);
                signUpStage.close(); // Close the sign-up window
            } else {
                System.out.println("Please fill in both fields.");
            }
        });
        
        //------------------------------------------------------------------
        //layout for the signup window
        //------------------------------------------------------------------

        VBox layout = new VBox(10, new Label("Sign Up"), usernameField, passwordField, signUpButton);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center;");
        
        Scene scene = new Scene(layout, 300, 200);
        signUpStage.setTitle("Sign-Up Window");
        signUpStage.setScene(scene);
        signUpStage.show();
    }
}

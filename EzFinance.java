package com.example;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class EzFinance extends Application
{


@Override
public void start(@SuppressWarnings("exports") Stage primaryStage){
    //------------------------------------------------------------------
    //Title and userdata
    //------------------------------------------------------------------

    UserData userData = new UserData();
    Text title = new Text(250 ,100,"EZFINANCE");
    Font font = Font.font("Imapct", FontWeight.EXTRA_BOLD, 45);
    title.setFont(font);

    //------------------------------------------------------------------
    //button initialization and properties
    //------------------------------------------------------------------

    Button login = new Button("Login");
    Button signup = new Button("Sign Up");

    login.setLayoutX(70);
    login.setLayoutY(175);
    login.setPrefSize(200, 100);
    login.setFont(new Font("Impact", 35));
    login.setStyle("-fx-text-fill: black");
    login.setTextAlignment(TextAlignment.CENTER); 

    login.setOnAction(event -> {
        LoginWindow loginWindow = new LoginWindow(userData);
        loginWindow.show();
    });

    signup.setLayoutX(480);
    signup.setLayoutY(175);
    signup.setPrefSize(200, 100);
    signup.setFont(new Font("Impact", 35));
    signup.setStyle("-fx-text-fill: black");
    signup.setTextAlignment(TextAlignment.CENTER); 

    signup.setOnAction(event -> {
        SignUpWindow signUpWindow = new SignUpWindow(userData);
        signUpWindow.show();
    });

    //------------------------------------------------------------------
    //setting the scene
    //------------------------------------------------------------------

    Group root = new Group(title, login, signup); 
    Scene scene = new Scene(root, 750, 350, Color.TEAL);
    primaryStage.setTitle("EzFinance");
    primaryStage.setScene(scene);
    primaryStage.show();
    }
    public static void main(String[] args)
    {
        launch(args);
    }
    }
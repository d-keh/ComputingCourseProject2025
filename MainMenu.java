package com.example;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class MainMenu {

    public void show() {
        Stage menu = new Stage();

        Text title = new Text(200, 100, "Finance Manager");
        Font font = Font.font("Impact", FontWeight.EXTRA_BOLD, 45);
        title.setFont(font);

        //------------------------------------------------------------------
        //buttons
        //------------------------------------------------------------------

        Button budgets = new Button("Budgets");
        Button income = new Button("Income");
        Button overview = new Button("Overview");

        //------------------------------------------------------------------
        //styling Buttons
        //------------------------------------------------------------------

        Font buttonFont = new Font("Impact", 35);
        budgets.setPrefSize(200, 100);
        budgets.setFont(buttonFont);
        budgets.setStyle("-fx-text-fill: black");

        income.setPrefSize(200, 100);
        income.setFont(buttonFont);
        income.setStyle("-fx-text-fill: black");

        overview.setPrefSize(200, 100);
        overview.setFont(buttonFont);
        overview.setStyle("-fx-text-fill: black");

        //------------------------------------------------------------------
        //positioning buttons
        //------------------------------------------------------------------

        budgets.setLayoutX(50);
        budgets.setLayoutY(175);

        overview.setLayoutX(275);
        overview.setLayoutY(175);

        income.setLayoutX(500);
        income.setLayoutY(175);

        //------------------------------------------------------------------
        //button actions
        //------------------------------------------------------------------

        budgets.setOnAction(event -> {
            BudgetsWindow budgetsWindow = new BudgetsWindow();
            budgetsWindow.show();
        });

        income.setOnAction(event -> {
            IncomeWindow incomeWindow = new IncomeWindow();
            incomeWindow.show();
        });

        overview.setOnAction(event -> {
            OverviewWindow overviewWindow = new OverviewWindow();
            overviewWindow.show();
        });

        //------------------------------------------------------------------
        //main menu scene
        //------------------------------------------------------------------
        
        Group root = new Group(budgets, income, overview, title);
        Scene scene = new Scene(root, 750, 350, Color.GOLD);
        menu.setTitle("Main Menu");
        menu.setScene(scene);
        menu.show();
    }
}
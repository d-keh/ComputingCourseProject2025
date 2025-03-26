package com.example;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BudgetsWindow {
    private final ObservableList<String> budgets = FXCollections.observableArrayList();

    public BudgetsWindow() {
        loadBudgets(); //load saved budgets on initialization
    }

    public ObservableList<String> getBudgets() {
        return budgets; //expose the budgets for use in OverviewWindow
    }

    public void show() {
        Stage budgetsStage = new Stage();
        VBox layout = new VBox(10);

        //------------------------------------------------------------------
        //ui elements
        //------------------------------------------------------------------


        Label title = new Label("Viewing Budgets");
        ListView<String> budgetsList = new ListView<>(budgets);
        TextField newBudgetField = new TextField();
        newBudgetField.setPromptText("Enter new budget");
        Button addBudgetButton = new Button("Add Budget");
        Button deleteBudgetButton = new Button("Delete Selected");

        //------------------------------------------------------------------
        //adding a budget
        //------------------------------------------------------------------

        addBudgetButton.setOnAction(event -> {
            String newBudget = newBudgetField.getText();
            if (!newBudget.isEmpty()) {
                budgets.add(newBudget);
                newBudgetField.clear();
                saveBudgets(); 
            }
        });

        //------------------------------------------------------------------
        //deleting a budget
        //------------------------------------------------------------------

        deleteBudgetButton.setOnAction(event -> {
            String selected = budgetsList.getSelectionModel().getSelectedItem();
            if (selected != null) {
                budgets.remove(selected);
                saveBudgets();
            }
        });

        //------------------------------------------------------------------
        //setting the scene
        //------------------------------------------------------------------

        layout.getChildren().addAll(title, budgetsList, newBudgetField, addBudgetButton, deleteBudgetButton);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-background-color: lightgreen;");

        Scene scene = new Scene(layout, 400, 400);
        budgetsStage.setTitle("Budgets");
        budgetsStage.setScene(scene);
        budgetsStage.show();
    }

    //------------------------------------------------------------------
    //saving budget data
    //------------------------------------------------------------------

    private void saveBudgets() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("budgets.dat"))) {
            out.writeObject(new ArrayList<>(budgets)); // Serialize as a plain list
        } catch (IOException e) {
            System.out.println("Error saving budgets: " + e.getMessage());
        }
    }

    //------------------------------------------------------------------
    //loading budget data
    //------------------------------------------------------------------
    
    private void loadBudgets() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("budgets.dat"))) {
            @SuppressWarnings("unchecked")
            List<String> loadedBudgets = (List<String>) in.readObject();
            budgets.setAll(loadedBudgets);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved budgets found.");
        }
    }
}
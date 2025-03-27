package com.example;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class IncomeWindow {
    private final ObservableList<String> incomeListData = FXCollections.observableArrayList();
    private final ObservableList<PieChart.Data> incomeData = FXCollections.observableArrayList();

    public IncomeWindow() {
        loadIncome(); //load saved income data on initialization
    }

    public ObservableList<String> getIncomeListData() {
        return incomeListData; //expose the income list for use in OverviewWindow
    }

    @SuppressWarnings("exports")
    public ObservableList<PieChart.Data> getIncomeData() {
        return incomeData; //expose the pie chart data for use in OverviewWindow
    }

    public void show() {
        //------------------------------------------------------------------
        //create the main income details window
        //------------------------------------------------------------------

        Stage incomeStage = new Stage();
        VBox layout = new VBox(10);

        Label title = new Label("Viewing Income (Hourly Rate)");
        ListView<String> incomeList = new ListView<>(incomeListData);
        TextField newIncomeSourceField = new TextField();
        newIncomeSourceField.setPromptText("Enter income source");
        TextField incomeAmountField = new TextField();
        incomeAmountField.setPromptText("Enter hourly rate");
        Button addIncomeButton = new Button("Add Income");
        Button deleteIncomeButton = new Button("Delete Selected");

        //------------------------------------------------------------------
        //adding income source
        //------------------------------------------------------------------

        addIncomeButton.setOnAction(event -> {
            String source = newIncomeSourceField.getText();
            String amountStr = incomeAmountField.getText();
            try {
                double amount = Double.parseDouble(amountStr);
                if (amount <= 0) {
                    System.out.println("Hourly rate must be greater than zero.");
                    return;
                }
                if (!source.isEmpty()) {
                    for (PieChart.Data data : incomeData) {
                        if (data.getName().equalsIgnoreCase(source)) {
                            System.out.println("Income source already exists.");
                            return;
                        }
                    }
                    incomeListData.add(source + " ($" + amount + "/hr)");
                    incomeData.add(new PieChart.Data(source, amount));
                    newIncomeSourceField.clear();
                    incomeAmountField.clear();
                    saveIncome(); // Save changes
                } else {
                    System.out.println("Income source cannot be empty.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid hourly rate. Please enter a numeric value.");
            }
        });

        //------------------------------------------------------------------
        //deleting income source
        //------------------------------------------------------------------
        
        deleteIncomeButton.setOnAction(event -> {
            int selectedIndex = incomeList.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                incomeListData.remove(selectedIndex);
                incomeData.remove(selectedIndex);
                saveIncome(); // Save changes
            }
        });

        //------------------------------------------------------------------
        //setting the scene
        //------------------------------------------------------------------

        layout.getChildren().addAll(title, incomeList, newIncomeSourceField, incomeAmountField, addIncomeButton, deleteIncomeButton);
        layout.setStyle("-fx-padding: 10; -fx-alignment: center; -fx-background-color: lightgreen;");

        Scene incomeScene = new Scene(layout, 500, 500);
        incomeStage.setTitle("Income");
        incomeStage.setScene(incomeScene);

        // Create the pie chart window
        Stage pieChartStage = new Stage();
        PieChart pieChart = new PieChart(incomeData);
        VBox pieChartLayout = new VBox(pieChart);
        pieChartLayout.setStyle("-fx-padding: 10; -fx-background-color: lightgreen;");

        Scene pieChartScene = new Scene(pieChartLayout, 333, 333); // 2/3 size of income window (500 x 500)
        pieChartStage.setTitle("Income Distribution");
        pieChartStage.setScene(pieChartScene);

        //------------------------------------------------------------------
        //centering both windows
        //------------------------------------------------------------------
        
        Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
        double screenCenterX = screenBounds.getWidth() / 2;
        double screenCenterY = screenBounds.getHeight() / 2;

        //------------------------------------------------------------------
        //main screen
        //------------------------------------------------------------------

        double incomeWindowX = screenCenterX - (500 / 2) - (333 / 2); 
        double incomeWindowY = screenCenterY - (500 / 2);
        incomeStage.setX(incomeWindowX);
        incomeStage.setY(incomeWindowY);

        //------------------------------------------------------------------
        //pie chart visual
        //------------------------------------------------------------------

        double pieChartWindowX = incomeWindowX + 500;
        double pieChartWindowY = incomeWindowY;
        pieChartStage.setX(pieChartWindowX);
        pieChartStage.setY(pieChartWindowY);

        incomeStage.show();
        pieChartStage.show();
    }

    //------------------------------------------------------------------
    //save income data
    //------------------------------------------------------------------

    private void saveIncome() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("income.dat"))) {
            out.writeObject(new ArrayList<>(incomeListData)); 
        } catch (IOException e) {
            System.out.println("Error saving income data: " + e.getMessage());
        }
    }

    //------------------------------------------------------------------
    //load income data
    //------------------------------------------------------------------

    private void loadIncome() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("income.dat"))) {
            @SuppressWarnings("unchecked")
            List<String> loadedIncome = (List<String>) in.readObject();
            incomeListData.setAll(loadedIncome);
            for (String income : loadedIncome) {
                String[] parts = income.split(" \\(\\$|/hr\\)"); // Split by " ($" and "/hr)"
                if (parts.length >= 2) {
                    try {
                        incomeData.add(new PieChart.Data(parts[0], Double.parseDouble(parts[1])));
                    } catch (NumberFormatException e) {
                        System.out.println("Error parsing income data: " + income);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No saved income data found.");
        }
    }
}
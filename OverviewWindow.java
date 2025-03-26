package com.example;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class OverviewWindow {

    private final BudgetsWindow budgetsWindow = new BudgetsWindow();
    private final IncomeWindow incomeWindow = new IncomeWindow();

    public void show() {
        Stage overviewStage = new Stage();

        //------------------------------------------------------------------
        //creating the lists
        //------------------------------------------------------------------

        //budgets list
        ListView<String> budgetsList = new ListView<>(budgetsWindow.getBudgets());

        //income list
        ListView<String> incomeList = new ListView<>(incomeWindow.getIncomeListData());

        //------------------------------------------------------------------
        //pie chart/visuals
        //------------------------------------------------------------------

        PieChart incomePieChart = new PieChart(incomeWindow.getIncomeData());

        //------------------------------------------------------------------
        //calculating total income
        //------------------------------------------------------------------

        double totalIncome = incomeWindow.getIncomeData().stream()
                .mapToDouble(PieChart.Data::getPieValue)
                .sum();

        //------------------------------------------------------------------
        //calculate total budget expenses
        //------------------------------------------------------------------

        double totalBudgetExpenses = budgetsWindow.getBudgets().stream()
                .mapToDouble(budget -> {
                    try {
                        // Assumes budget format like "Budget Name ($100)"
                        String[] parts = budget.split("\\$");
                        return Double.parseDouble(parts[1].replace(")", ""));
                    } catch (Exception e) {
                        return 0.0;
                    }
                }).sum();

        //------------------------------------------------------------------
        //calculate leftover funds
        //------------------------------------------------------------------

        double leftoverFunds = totalIncome - totalBudgetExpenses;
        Label leftoverFundsLabel = new Label("Leftover Funds: $" + String.format("%.2f", leftoverFunds));
        leftoverFundsLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        //------------------------------------------------------------------
        //setting the scene
        //------------------------------------------------------------------
        
        VBox budgetsBox = new VBox(new Label("Budgets"), budgetsList);
        budgetsBox.setAlignment(Pos.CENTER);

        VBox incomeBox = new VBox(new Label("Income Sources"), incomeList);
        incomeBox.setAlignment(Pos.CENTER);

        VBox centerBox = new VBox(incomePieChart, leftoverFundsLabel);
        centerBox.setAlignment(Pos.CENTER);
        centerBox.setSpacing(20);

        BorderPane layout = new BorderPane();
        layout.setLeft(budgetsBox);
        layout.setRight(incomeBox);
        layout.setCenter(centerBox);
        layout.setStyle("-fx-background-color: gold;"); 

        Scene scene = new Scene(layout, 800, 600);
        overviewStage.setTitle("Overview");
        overviewStage.setScene(scene);
        overviewStage.show();
    }
}
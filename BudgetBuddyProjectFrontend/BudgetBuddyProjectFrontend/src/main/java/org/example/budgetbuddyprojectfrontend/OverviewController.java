package org.example.budgetbuddyprojectfrontend;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.chart.PieChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.Map;

// defining the OverviewController as controller for Overview.fxml
public class OverviewController {

    // defining its controls
    @FXML
    private TableView<Map<String, Object>> incomeTable;

    @FXML
    private TableColumn<Map<String, Object>, String> incomeSource;

    @FXML
    private TableColumn<Map<String, Object>, Double> incomeAmount;

    @FXML
    private TableView<Map<String, Object>> expenseTable;

    @FXML
    private TableColumn<Map<String, Object>, String> expenseCategory;

    @FXML
    private TableColumn<Map<String, Object>, Double> expenseAmount;

    @FXML
    private Label totalBalanceLabel;

    @FXML
    private PieChart incomeChart;

    @FXML
    private PieChart expenseChart;

    @FXML
    private Hyperlink homeLink;

    private final getHandler getRequestHandler = new getHandler();

    // initialize the controls
    @FXML
    public void initialize() {
        loadIncomeData();
        loadExpenseData();
        calculateTotalBalance();
        populateIncomeChart();
        populateExpenseChart();
        homeLink.setOnAction(event -> goToHome());
    }

    // load the data for income
    private void loadIncomeData() {
        int userID = getRequestHandler.getCurrentUserId();
        List<Map<String, Object>> incomes = getRequestHandler.getAllIncomesForUser(userID);
        ObservableList<Map<String, Object>> incomeList = FXCollections.observableArrayList(incomes);
        incomeTable.setItems(incomeList);
        incomeSource.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("sourceName").toString()));
        incomeAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(Double.parseDouble(cellData.getValue().get("amount").toString())).asObject());
    }

    // load the data for expense
    private void loadExpenseData() {
        int userID = getRequestHandler.getCurrentUserId();
        List<Map<String, Object>> expenses = getRequestHandler.getAllExpensesForUser(userID);
        ObservableList<Map<String, Object>> expenseList = FXCollections.observableArrayList(expenses);
        expenseTable.setItems(expenseList);
        expenseCategory.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().get("categoryName").toString()));
        expenseAmount.setCellValueFactory(cellData -> new SimpleDoubleProperty(Double.parseDouble(cellData.getValue().get("amount").toString())).asObject());
    }

    // calculate the total balance
    private void calculateTotalBalance() {
        double totalIncome = incomeTable.getItems().stream()
                .mapToDouble(item -> Double.parseDouble(item.get("amount").toString()))
                .sum();
        double totalExpense = expenseTable.getItems().stream()
                .mapToDouble(item -> Double.parseDouble(item.get("amount").toString()))
                .sum();
        double totalBalance = totalIncome - totalExpense;
        totalBalanceLabel.setText(String.format("$%.2f", totalBalance));
    }

    // to fill up the chart with income data
    private void populateIncomeChart() {
        ObservableList<PieChart.Data> incomeChartData = FXCollections.observableArrayList();
        incomeTable.getItems().forEach(income ->
                incomeChartData.add(new PieChart.Data(income.get("sourceName").toString(), Double.parseDouble(income.get("amount").toString())))
        );
        incomeChart.setData(incomeChartData);
    }

    // to fill up the chart with expense data
    private void populateExpenseChart() {
        ObservableList<PieChart.Data> expenseChartData = FXCollections.observableArrayList();
        expenseTable.getItems().forEach(expense ->
                expenseChartData.add(new PieChart.Data(expense.get("categoryName").toString(), Double.parseDouble(expense.get("amount").toString())))
        );
        expenseChart.setData(expenseChartData);
    }

    // to navigate to homepage
    @FXML
    private void goToHome() {
        Stage stage = (Stage) homeLink.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
            Scene scene = new Scene(root);
            Image icon = new Image(getClass().getResource("/images/icon.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
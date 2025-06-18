package org.example.budgetbuddyprojectfrontend;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// defining the ExpenseController class as controller for Expense.fxml
public class ExpenseController {

    // defining all the controls
    @FXML
    private ChoiceBox<String> categoryChoiceBox;

    @FXML
    private TextField expenseAmountField;

    @FXML
    private Button addExpenseButton;

    @FXML
    private TableView<Map<String, Object>> expenseTableView;

    @FXML
    private TableColumn<Map<String, Object>, String> expenseCategoryColumn;

    @FXML
    private TableColumn<Map<String, Object>, Double> expenseAmountColumn;

    @FXML
    private Label totalExpenseLabel;

    @FXML
    private Hyperlink homeLink;

    @FXML
    private Label expenseAddedLabel;

    private ObservableList<Map<String, Object>> expenseList = FXCollections.observableArrayList();

    private final getHandler getRequestHandler = new getHandler();
    private final postRequestHandler postRequestHandler = new postRequestHandler();

    // defining the initialize method to initialize all controls
    @FXML
    public void initialize() {
        String categoriesResponse = getRequestHandler.getAllCategories();
        ArrayList<String> categoryNames = parseCategoriesResponse(categoriesResponse);
        categoryChoiceBox.setItems(FXCollections.observableArrayList(categoryNames));

        // Set up the columns
        expenseCategoryColumn.setCellValueFactory(cellData -> {
            Map<String, Object> rowData = cellData.getValue();
            return new SimpleStringProperty((String) rowData.get("categoryName"));
        });
        expenseAmountColumn.setCellValueFactory(cellData -> {
            Map<String, Object> rowData = cellData.getValue();
            return new SimpleDoubleProperty((Double) rowData.get("amount")).asObject();
        });

        int userId = getRequestHandler.getCurrentUserId();
        List<Map<String, Object>> expenses = getRequestHandler.getAllExpensesForUser(userId);
        expenseList.addAll(expenses);
        expenseTableView.setItems(expenseList);

        updateTotalExpenseLabel();

        addExpenseButton.setOnAction(event -> addExpense());
        homeLink.setOnAction(event -> goToHome());
    }

    // defining function to addExpense (calls addExpense from postRequestHandler)
    @FXML
    private void addExpense() {
        String categoryName = categoryChoiceBox.getValue();
        double amount = Double.parseDouble(expenseAmountField.getText());

        if (amount < 0) {
            expenseAddedLabel.setText("Error: Amount cannot be negative!");
            return;
        }

        String categoryIdStr = getRequestHandler.getCategoryIdByName(categoryName);
        int categoryId = Integer.parseInt(categoryIdStr);

        JSONObject requestBody = new JSONObject();
        requestBody.put("amount", amount);
        requestBody.put("userId", getRequestHandler.getCurrentUserId());
        requestBody.put("categoryId", categoryId);

        Map<String, Object> addExpenseResponse = postRequestHandler.addExpenseForUser(requestBody);

        // Check if the response contains the required keys
        if (addExpenseResponse.containsKey("amount") && addExpenseResponse.containsKey("categoryName")) {
            expenseList.add(addExpenseResponse);
            updateTotalExpenseLabel();
            expenseAddedLabel.setText("You have added " + amount + "$ for " + categoryName + "!");
            clearFields();
            showAlert("Expense added successfully!");
        } else {
            System.err.println("Failed to add expense.");
        }
    }

    // to clear the fields after submission
    private void clearFields() {
        expenseAmountField.clear();
        categoryChoiceBox.setValue(null);
    }

    // to update the total amount of expense
    private void updateTotalExpenseLabel() {
        double totalExpense = expenseList.stream()
                .mapToDouble(item -> (double) item.get("amount"))
                .sum();
        totalExpenseLabel.setText(totalExpense + "$");
    }

    // to navigate to Homepage
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

    // to parse categories responses to needed format
    private ArrayList<String> parseCategoriesResponse(String response) {
        ArrayList<String> categoryNames = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String categoryName = jsonObject.getString("name");
            categoryNames.add(categoryName);
        }
        return categoryNames;
    }

    // function to show alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// defining IncomeController as controller for Income.fxml
public class IncomeController {

    // defining the controls
    @FXML
    private ChoiceBox<String> sourceChoiceBox;

    @FXML
    private TextField incomeAmountField;

    @FXML
    private Button addIncomeButton;

    @FXML
    private TableView<Map<String, Object>> incomeTableView;

    @FXML
    private TableColumn<Map<String, Object>, String> incomeSourceColumn;

    @FXML
    private TableColumn<Map<String, Object>, Double> incomeAmountColumn;

    @FXML
    private Label totalIncomeLabel;

    @FXML
    private Hyperlink homeLink;

    @FXML
    private Label incomeAddedLabel;

    private ObservableList<Map<String, Object>> incomeList = FXCollections.observableArrayList();

    private final getHandler getRequestHandler = new getHandler();
    private final postRequestHandler postRequestHandler = new postRequestHandler();

    // to initialize all the controls
    @FXML
    public void initialize() {
        String sourcesResponse = getRequestHandler.getAllSources();
        ArrayList<String> sourceNames = parseSourcesResponse(sourcesResponse);
        sourceChoiceBox.setItems(FXCollections.observableArrayList(sourceNames));

        // Set up the columns
        incomeSourceColumn.setCellValueFactory(cellData -> {
            Map<String, Object> rowData = cellData.getValue();
            return new SimpleStringProperty((String) rowData.get("sourceName"));
        });

        incomeAmountColumn.setCellValueFactory(cellData -> {
            Map<String, Object> rowData = cellData.getValue();
            return new SimpleDoubleProperty((Double) rowData.get("amount")).asObject();
        });

        int userId = getRequestHandler.getCurrentUserId();
        List<Map<String, Object>> incomes = getRequestHandler.getAllIncomesForUser(userId);
        incomeList.addAll(incomes);
        incomeTableView.setItems(incomeList);

        // Update the total expense label
        updateTotalIncomeLabel();

        addIncomeButton.setOnAction(event -> addIncome());
        homeLink.setOnAction(event -> goToHome());
    }

    // function to add an income
    @FXML
    private void addIncome() {
        String sourceName = sourceChoiceBox.getValue();
        double amount = Double.parseDouble(incomeAmountField.getText());

        if (amount < 0) {
            incomeAddedLabel.setText("Error: Amount cannot be negative!");
            return;
        }

        String sourceIdStr = getRequestHandler.getSourceIdByName(sourceName);
        int sourceId = Integer.parseInt(sourceIdStr);

        // Create a JSON object to send as the request body
        JSONObject requestBody = new JSONObject();
        requestBody.put("amount", amount);
        requestBody.put("userId", getRequestHandler.getCurrentUserId());
        requestBody.put("sourceId", sourceId);

        Map<String, Object> addIncomeResponse = postRequestHandler.addIncomeForUser(requestBody);

        // Check if the response contains the required keys
        if (addIncomeResponse.containsKey("amount") && addIncomeResponse.containsKey("sourceName")) {
            incomeList.add(addIncomeResponse);
            updateTotalIncomeLabel();
            incomeAddedLabel.setText("You have added " + amount + "$ for " + sourceName + "!");
            clearFields();
            showAlert("Income added successfully!");
        } else {
            System.err.println("Failed to add income.");
        }
    }

    // clears the fields after submission
    private void clearFields() {
        incomeAmountField.clear();
        sourceChoiceBox.setValue(null);
    }

    // updates the total amount for income
    private void updateTotalIncomeLabel() {
        double totalIncome = incomeList.stream()
                .mapToDouble(item -> (double) item.get("amount"))
                .sum();
        totalIncomeLabel.setText(totalIncome + "$");
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

    // to parse source responses to needed format
    private ArrayList<String> parseSourcesResponse(String response) {
        ArrayList<String> sourceNames = new ArrayList<>();
        JSONArray jsonArray = new JSONArray(response);
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            String sourceName = jsonObject.getString("name");
            sourceNames.add(sourceName);
        }
        return sourceNames;
    }

    // function to show alerts
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}


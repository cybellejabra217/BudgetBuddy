package org.example.budgetbuddyprojectfrontend;

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
import org.json.JSONObject;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

// defining RemindersController as controller for Reminders.fxml
public class RemindersController {

    // defining its controls
    @FXML
    private Hyperlink homeLink;

    @FXML
    private TextField descriptionField;

    @FXML
    private DatePicker reminderDatePicker;

    @FXML
    private ChoiceBox<String> recurrencePatternChoiceBox;

    @FXML
    private Button addReminderButton;

    @FXML
    private TableView<Map<String, Object>> reminderTableView;

    @FXML
    private TableColumn<Map<String, Object>, String> reminderDescriptionColumn;

    @FXML
    private TableColumn<Map<String, Object>, String> reminderDateColumn;

    private ObservableList<Map<String, Object>> reminderList = FXCollections.observableArrayList();

    private final getHandler getRequestHandler = new getHandler();
    private final postRequestHandler postRequestHandler = new postRequestHandler();

    // initializing all controls
    @FXML
    public void initialize() {
        List<String> patterns = getRequestHandler.getRecurrencePatterns();
        recurrencePatternChoiceBox.setItems(FXCollections.observableArrayList(patterns));

        reminderDescriptionColumn.setCellValueFactory(cellData -> {
            Object value = cellData.getValue().get("description");
            return value == null ? new SimpleStringProperty("") : new SimpleStringProperty(value.toString());
        });
        reminderDateColumn.setCellValueFactory(cellData -> {
            Object value = cellData.getValue().get("reminderDate");
            return value == null ? new SimpleStringProperty("") : new SimpleStringProperty(value.toString());
        });

        reminderTableView.setItems(reminderList);
        addReminderButton.setOnAction(event -> addReminder());
        loadReminders();

        homeLink.setOnAction(event -> goToHome());
    }

    // function to add a reminder
    @FXML
    private void addReminder() {
        String description = descriptionField.getText();
        LocalDate date = reminderDatePicker.getValue();
        String pattern = recurrencePatternChoiceBox.getValue();
        if (description.isEmpty() || date == null || pattern == null) {
            showAlert("Please fill in all fields.");
            return;
        }

        Date reminderDate = Date.valueOf(date);
        int userId = getRequestHandler.getCurrentUserId();

        JSONObject requestBody = new JSONObject();
        requestBody.put("description", description);
        requestBody.put("reminderDate", reminderDate);
        requestBody.put("recurrencePatternStr", pattern);
        requestBody.put("userId", userId);

        Map<String, Object> addReminderResponse = postRequestHandler.addReminder(requestBody);

        // Check if the response contains the required keys
        if (addReminderResponse.containsKey("description") && addReminderResponse.containsKey("reminderDate") && addReminderResponse.containsKey("recurrencePatternStr")) {
            reminderList.add(addReminderResponse);
            clearFields();
            showAlert("Reminder added successfully!");
        } else {
            showAlert("Failed to add reminder. Please try again.");
        }
    }

    // function to load all reminders
    private void loadReminders() {
        int userId = getRequestHandler.getCurrentUserId();
        reminderList.clear();
        reminderList.addAll(getRequestHandler.getRemindersByUser(userId));
    }

    // function to clear the fields after submission
    private void clearFields() {
        descriptionField.clear();
        reminderDatePicker.setValue(null);
        recurrencePatternChoiceBox.setValue(null);
    }

    // function to show alert
    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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
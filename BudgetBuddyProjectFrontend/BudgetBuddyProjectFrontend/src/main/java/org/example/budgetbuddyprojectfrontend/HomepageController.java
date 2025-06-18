package org.example.budgetbuddyprojectfrontend;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.image.Image;
import javafx.stage.Stage;

// defining HomepageController as controller for Homepage.fxml
public class HomepageController {

    // defining all the controls
    @FXML
    private Hyperlink overviewLink;

    @FXML
    private Hyperlink incomeLink;

    @FXML
    private Hyperlink expenseLink;

    @FXML
    private Hyperlink remindersLink;

    // function to initialize all controls
    @FXML
    private void initialize() {
        overviewLink.setOnAction(e -> navigateToPage("Overview.fxml", "Overview"));
        incomeLink.setOnAction(e -> navigateToPage("Income.fxml", "Income"));
        expenseLink.setOnAction(e -> navigateToPage("Expense.fxml", "Expense"));
        remindersLink.setOnAction(e -> navigateToPage("Reminders.fxml", "Reminders"));
    }

    // to navigate to the required page
    private void navigateToPage(String fxmlFileName, String pageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFileName));
            Parent root = loader.load();
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle(pageTitle);
            Image icon = new Image(getClass().getResource("/images/icon.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.show();

            Stage homepageStage = (Stage) overviewLink.getScene().getWindow();
            homepageStage.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
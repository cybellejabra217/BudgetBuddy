package org.example.budgetbuddyprojectfrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

// defining SignupController as controller for Signup.fxml
public class SignupController {

    // defining its controls
    @FXML
    private TextField usernameField;

    @FXML
    private TextField passwordField;

    @FXML
    private TextField confirmPasswordField;

    @FXML
    private TextField emailField;

    @FXML
    private Button signupButton;

    private final postRequestHandler postRequestHandler = new postRequestHandler();

    // to initialize the controls
    @FXML
    private void initialize() {
        signupButton.setOnAction(event -> handleSignup());
    }

    // to handle the signup
    private void handleSignup() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String confirmPassword = confirmPasswordField.getText();
        String email = emailField.getText();

        // checks if any field is left empty
        if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty() || email.isEmpty()) {
            showError("Please fill in all fields.");
            return;
        }

        // checks if password and confirmPassword match
        if (!password.equals(confirmPassword)) {
            showError("Passwords do not match");
            return;
        }

        boolean signupResponse = postRequestHandler.addUser(username, password, email);

        if (signupResponse) {
            showSuccess("Signup Successful!");
            try {
                openLogin();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showError("Failed to add user.");
        }
    }

    // to navigate to login page
    private void openLogin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setScene(scene);
        Image icon = new Image(getClass().getResource("/images/icon.png").toExternalForm());
        stage.getIcons().add(icon);
        stage.setTitle("Login");
        stage.show();

        Stage signupStage = (Stage) signupButton.getScene().getWindow();
        signupStage.close();
    }

    // to show error
    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    // to show success
    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
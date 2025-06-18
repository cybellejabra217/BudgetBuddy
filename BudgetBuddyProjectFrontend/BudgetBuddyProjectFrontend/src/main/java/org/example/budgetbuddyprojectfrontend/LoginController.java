package org.example.budgetbuddyprojectfrontend;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

// defining LoginController as controller for Login.fxml
public class LoginController {

    // defining the controls
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Hyperlink signupLink;

    private final postRequestHandler requestHandler = new postRequestHandler();

    // to initialize the controls
    @FXML
    private void initialize() {
        loginButton.setOnAction(event -> handleLogin());
        signupLink.setOnAction(event -> navigateToSignup());
    }

    // to handle the login
    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (!username.isEmpty() && !password.isEmpty()) {
            boolean loginSuccessful = requestHandler.login(username, password);
            if (loginSuccessful) {
                openHomepage();
            } else {
                showError("Invalid username or password.");
            }
        } else {
            showError("Please enter both username and password.");
        }
    }

    // to navigate to the signup page
    private void navigateToSignup() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Signup.fxml")));
            Scene scene = new Scene(root);
            Stage stage = (Stage) signupLink.getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open signup page.");
        }
    }

    // to navigate to the homepage
    private void openHomepage() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
            Scene scene = new Scene(root);
            Stage stage = (Stage) loginButton.getScene().getWindow();
            stage.setScene(scene);
            Image icon = new Image(getClass().getResource("/images/icon.png").toExternalForm());
            stage.getIcons().add(icon);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to open homepage.");
        }
    }

    // function to properly show errors
    private void showError(String message) {
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setTitle("Error");
        error.setHeaderText(null);
        error.setContentText(message);
        error.showAndWait();
    }
}
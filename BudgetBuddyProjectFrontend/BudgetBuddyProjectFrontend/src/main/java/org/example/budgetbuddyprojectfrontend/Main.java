package org.example.budgetbuddyprojectfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        try {
            // loading the first scene to be Login.fxml
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Login.fxml")));
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("Login Page");
            // adding an image
            Image icon = new Image(getClass().getResource("/images/icon.png").toExternalForm());
            primaryStage.getIcons().add(icon);
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // launching
    public static void main(String[] args) {
        launch(args);
    }
}
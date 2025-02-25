package com.example.calculator;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

import java.io.IOException;

public class Calculator extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(SimpleCalculator.class.getResource("calculator.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        try {
            Image icon = new Image(getClass().getResourceAsStream("/icons/icon.jpg"));
            stage.getIcons().add(icon);
        } catch (NullPointerException e) {
            System.err.println("Icon file not found!");
        }
        stage.setTitle("Simple calculator");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
package com.baymotors;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class HelloFX extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hello World JavaFX");

        Label label = new Label("Hello World!");
        StackPane root = new StackPane();
        root.getChildren().add(label);

        primaryStage.setScene(new Scene(root, 300, 250));
        primaryStage.show();
    }
}
package javaproject.expense_management;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("firstUI.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 823, 534);
//        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("themthuchi.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 550, 650);
        stage.setTitle("MANAGER!");
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch();
    }
}
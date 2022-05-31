package main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Inventory;

/**
 * @author Dwight Hickel
 *
 * Student ID: #001378298
 *
 * FUTURE ENHANCEMENT Database integration so changes can persist after close
 *
 * JAVADOC directory /javadoc/
 */


public class Main extends Application{
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        stage.setTitle("Inventory Manager");
        stage.setScene(new Scene(root,1024 , 762));
        stage.setResizable(false);
        stage.show();
    }
    
    public static void main(String[] args) {
       //Inventory.generateTestItems();
        launch(args);
    }
}

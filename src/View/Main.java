package View;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("MyView.fxml"));
        Parent root = loader.load();
        MyViewController myView = loader.getController();
        primaryStage.setTitle("Chicken Invaders Maze");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.setOnCloseRequest(event -> myView.endGame());
        primaryStage.getIcons().add(new Image("Images/icon.png"));
        primaryStage.show();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Welcome to The Chicken Invaders Maze\nChoose your maze size and complete it as fast as possible!");
        alert.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}

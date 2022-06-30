package View;

import ViewModel.MyViewModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;


import java.io.IOException;
import java.net.URL;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Observer, Initializable {

    public BorderPane MainBorderPane;
    public Pane centerPane;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public MyViewModel viewModel;
    public MediaPlayer mediaPlayer;
    public ScrollPane scrollPane;
    public Menu exit;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = new MyViewModel();
        viewModel.addObserver(this);

        // resize maze when user change window size.
        mazeDisplayer.heightProperty().bind(centerPane.heightProperty());
        mazeDisplayer.widthProperty().bind(centerPane.widthProperty());

        //initialize zoom in/out
        scrollPane = new ScrollPane(mazeDisplayer);
        scrollPane.setPannable(true);
        centerPane.getChildren().add(scrollPane);
        addMouseScrolling(mazeDisplayer);

        //initialize music
        backGroundMusic("Resources/Audio/GameMusic.mp3");
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
                backGroundMusic("Resources/Audio/GameMusic.mp3");
            }
        });

        // initialize exit button
        setExitButton();

    }

    public void setExitButton(){
        Label menuLabel = new Label("Exit");
        menuLabel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              endGame();
            }
            });
            exit.setGraphic(menuLabel);
        }

    public void addMouseScrolling(MazeDisplayer md) {
        md.setOnScroll((ScrollEvent event) -> {
            // Adjust the zoom factor as per your requirement
            double zoomFactor = 1.05;
            double deltaY = event.getDeltaY();
            if (deltaY < 0){
                zoomFactor = 2.0 - zoomFactor;
            }
            double scaleY = md.getScaleY() * zoomFactor;
            double scaleX = md.getScaleX() * zoomFactor;
            if(scaleY > 2) scaleY = 2;
            else if ( scaleY < 0.5) scaleY = 0.5;

            if(scaleX > 2) scaleX = 2;
            else if ( scaleX < 0.5) scaleX = 0.5;

            md.setScaleX(scaleX);
            md.setScaleY(scaleY);






        });
    }

    public void backGroundMusic(String path){
        Media music = new Media(Paths.get(path).toUri().toString());
        mediaPlayer = new MediaPlayer(music);
        mediaPlayer.play();
    }

    public void generateMaze(ActionEvent actionEvent) {

        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        viewModel.generateMaze(rows,cols);
    }

    public void solveMaze(ActionEvent actionEvent) {
        viewModel.solveMaze();
    }

    public void showWinningMessage(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("You reach the Target congratulation!!");
        alert.showAndWait();
    }

    @Override
    public void update(Observable o, Object arg) {

        String option = (String) arg;

        switch (option) {
            case "loaded successfully", "Maze generated" -> {
                mazeDisplayer.clearMaze();
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Maze solved" -> mazeDisplayer.drawSolveMaze(viewModel.getSolutionPath());
            case "Changed Location and win" -> {
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(), viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
                backGroundMusic("Resources/Audio/Victory.mp3");
                showWinningMessage();
                mazeDisplayer.clearMaze();
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Character Location Changed" -> {
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(), viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            default -> {
            }
        }
    }

    public void setViewModel(MyViewModel viewModel){
        this.viewModel = viewModel;
        viewModel.addObserver(this);
    }

    public void playerMovement(KeyEvent keyEvent) {
        viewModel.moveCharacter(keyEvent);
        keyEvent.consume();
    }

    public void mouseClicked(MouseEvent mouseEvent) {
        mazeDisplayer.requestFocus();
    }

    public void saveMaze(String fileName) {
        viewModel.saveMaze(fileName);
    }

    public void loadMaze(String fileName){ viewModel.loadMaze(fileName);}

    public void popUpSaveName(ActionEvent actionEvent)  {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMaze.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveLoadController inputController = loader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Save Maze");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.showAndWait();

        String fileName = inputController.getSavedName();
        if(fileName != null)
            saveMaze(fileName);
    }

    public void popUpLoadName(ActionEvent actionEvent)  {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadMaze.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveLoadController inputController = loader.getController();
        stage.setScene(new Scene(root));
        stage.setTitle("Load Maze");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.showAndWait();

        String fileName = inputController.getLoadName();
        if(fileName != null)
            loadMaze(fileName);

    }

    public void popUpProperties(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("Properties.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        PropController propController = loader.getController();
        propController.setMatrix();
        stage.setScene(new Scene(root));
        stage.setTitle("Maze Properties");
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.showAndWait();

    }

    public void endGame() {
        viewModel.endGame();
        Stage stage = (Stage) MainBorderPane.getScene().getWindow();
        stage.close();
        Platform.exit();
    }
}

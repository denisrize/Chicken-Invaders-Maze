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
import javafx.scene.transform.Scale;
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
    public Menu Exit;
    public Menu Help;
    public Menu About;



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
        setMenuButton();

    }


    public void setMenuButton(){
        Label menuLabelExit = new Label("Exit");
        Label menuLabelHelp = new Label("Help");
        Label menuLabelAbout = new Label("About");

        menuLabelAbout.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popUpPage("About");
            }
        });
        About.setGraphic(menuLabelAbout);

        menuLabelHelp.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                popUpPage("Help");
            }
        });
        Help.setGraphic(menuLabelHelp);

        menuLabelExit.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
              endGame();
            }
            });
            Exit.setGraphic(menuLabelExit);
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
                showWinningWindow();
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

    public void showWinningWindow(){
        double pSteps = viewModel.getPlayerSteps();
        double oSteps = viewModel.getBestSteps();
        int gameP = 0;
        Boolean helpFlag = viewModel.getAskForHelp();
        if(!helpFlag)gameP = (int)Math.floor((oSteps/pSteps)*100);


        String rows = String.valueOf(viewModel.getRows());
        String cols = String.valueOf(viewModel.getCols());
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource( "Win.fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene winScene = new Scene(root);
        stage.setScene(winScene);
        stage.setTitle("MAZE SOLVED");
        Win winController = loader.getController();
        winController.setWinWindow(rows,cols,String.valueOf(pSteps),String.valueOf(oSteps),String.valueOf(gameP),helpFlag,this);

        stage.initModality(Modality.APPLICATION_MODAL);
        stage.initOwner(mazeDisplayer.getScene().getWindow());
        stage.show();
    }

    public void popUpPage(String page) {
        Stage stage = new Stage();
        Parent root = null;

        FXMLLoader loader = new FXMLLoader(getClass().getResource(page +".fxml"));
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Maze " + page);
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

package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
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
            case "loaded successfully" -> {
                mazeDisplayer.clearMaze();
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Maze solved" -> mazeDisplayer.drawSolveMaze(viewModel.getSolutionPath());
            case "Changed Location and win" -> {
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(), viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
                showWinningMessage();
                mazeDisplayer.clearMaze();
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Character Location Changed" -> {
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(), viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
            }
            case "Maze generated" -> {
                mazeDisplayer.clearMaze();
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

    public void popUpSaveName(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("SaveMaze.fxml"));
        root = loader.load();
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

    public void popUpLoadName(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        Parent root;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("LoadMaze.fxml"));
        root = loader.load();
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
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        viewModel = new MyViewModel();
        viewModel.addObserver(this);
        // resize maze when user change window size.
        mazeDisplayer.heightProperty().bind(centerPane.heightProperty());
        mazeDisplayer.widthProperty().bind(centerPane.widthProperty());
    }
}

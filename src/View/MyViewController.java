package View;

import ViewModel.MyViewModel;
import com.sun.glass.ui.Window;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.WindowEvent;


import java.util.Observable;
import java.util.Observer;
import java.util.Optional;

public class MyViewController implements Observer {

    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public MyViewModel viewModel;


    public void generateMaze(ActionEvent actionEvent) {
        if(viewModel == null){
            viewModel = new MyViewModel();
            viewModel.addObserver(this);
        }

        int rows = Integer.parseInt(textField_mazeRows.getText());
        int cols = Integer.parseInt(textField_mazeColumns.getText());
        viewModel.generateMaze(rows,cols);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
    }

    public void showWinningMessage(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("You reach the Target congratulation!!");
        /*alert.setOnCloseRequest( e ->{
            mazeDisplayer.requestFocus();
            mazeDisplayer.clearMaze();
        } );
        */
        alert.showAndWait();


    }

    @Override
    public void update(Observable o, Object arg) {

        String option = (String) arg;

        switch (option)
        {
            case "Changed Location and win":
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(),viewModel.getColLocation());
                mazeDisplayer.drawMaze(viewModel.getMaze());
                showWinningMessage();
                mazeDisplayer.setPlayerPosition(-1,-1);
                break;
            case "Character Location Changed":
                mazeDisplayer.setPlayerPosition(viewModel.getRowLocation(),viewModel.getColLocation());
                break;
            case "Maze generated":
                mazeDisplayer.setPlayerPosition(-1,-1);
                mazeDisplayer.setTargetPosition(-1,-1);
                break;
            default:
                break;

        }
        mazeDisplayer.drawMaze(viewModel.getMaze());

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
}

package View;

import ViewModel.MyViewModel;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

import java.util.Observable;
import java.util.Observer;

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


    @Override
    public void update(Observable o, Object arg) {

        String option = (String) arg;

        switch (option)
        {
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

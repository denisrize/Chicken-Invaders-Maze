package View;

import ViewModel.MyViewModel;
import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.SearchableMaze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.awt.event.KeyEvent;
import java.net.URL;
import java.util.Observable;
import java.util.Observer;
import java.util.ResourceBundle;

public class MyViewController implements Observer, Initializable {

    public IMazeGenerator generator;
    public TextField textField_mazeRows;
    public TextField textField_mazeColumns;
    public MazeDisplayer mazeDisplayer;
    public MyViewModel viewModel;


    public void generateMaze(ActionEvent actionEvent) {
        if(generator == null)
            generator = new MyMazeGenerator();

        int rows = Integer.valueOf(textField_mazeRows.getText());
        int cols = Integer.valueOf(textField_mazeColumns.getText());

        Maze m = generator.generate(rows, cols);
        SearchableMaze maze = new SearchableMaze(m);

        mazeDisplayer.drawMaze(maze);
    }

    public void solveMaze(ActionEvent actionEvent) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText("Solving maze...");
        alert.show();
    }

    public void playerMovement(KeyEvent keyEvent) {

        viewModel.moveCharacter(keyEvent);
    }

    @Override
    public void update(Observable o, Object arg) {

        String option = (String) arg;

        switch (option)
        {
            case "Character Location Changed":

        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void setViewModel(MyViewModel viewModel){
        this.viewModel = viewModel;
    }
}

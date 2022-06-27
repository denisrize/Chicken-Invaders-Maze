package View;

import javafx.event.ActionEvent;
import javafx.scene.Parent;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SaveLoadController {
    private Parent root;
    public TextField SaveInputName;
    public TextField LoadInputName;
    public String saveMazeName;
    public String loadMazeName;

    public void saveMaze(ActionEvent actionEvent) {
        saveMazeName = SaveInputName.getText();
        Stage stage = (Stage)SaveInputName.getScene().getWindow();
        stage.close();
    }
    public String getSavedName(){return saveMazeName;}

    public String getLoadName(){return loadMazeName;}

    public void loadMaze(ActionEvent actionEvent) {
        loadMazeName = LoadInputName.getText();
        Stage stage = (Stage) LoadInputName.getScene().getWindow();
        stage.close();
    }
}

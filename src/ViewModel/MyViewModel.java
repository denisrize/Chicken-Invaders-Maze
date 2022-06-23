package ViewModel;

import Model.IModel;
import Model.MyModel;
import algorithms.mazeGenerators.Maze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyEvent;

import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private MyModel model;

    public MyViewModel() {
        this.model = new MyModel();
        model.addObserver(this);
    }

    public void generateMaze(int row , int col ){
        model.generateMaze(row,col);
    }

    public void moveCharacter(KeyEvent keyEvent){
        // direcrtion 0 --> UP
        // direcrtion 1 --> DOWN
        // direcrtion 2 --> LEFT
        // direcrtion 3 --> RIGHT
        // direcrtion 4 --> <\
        // direcrtion 5 --> />
        // direcrtion 6 --> </
        // direcrtion 7 --> \>
        int direction = Integer.valueOf(keyEvent.getText());
        if(direction > 0 && direction < 10)
            model.updateCharacterLocation(direction);
    }

    @Override
    public void update(Observable o, Object arg) {

        if(o instanceof IModel){

            String option = (String) arg;

            switch (option)
            {
                case "Changed Location and win":
                    break;
                case "Character Location Changed":
                    break;
                case "Maze generated":
                    break;

            }
            setChanged();
            notifyObservers(option);
        }

    }

    public Maze getMaze(){
        return model.getMaze();
    }

    public int getRowLocation() {
        return model.getRowLocation();
    }

    public int getColLocation() {
        return model.getColLocation();
    }



    public void setModel(MyModel model) {
        this.model = model;
    }
}

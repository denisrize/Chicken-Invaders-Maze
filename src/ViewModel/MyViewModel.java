package ViewModel;

import Model.MyModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.awt.event.KeyEvent;
import java.util.Observable;
import java.util.Observer;

public class MyViewModel extends Observable implements Observer {

    private int rowLocation;
    private int colLocation;
    private MyModel model;

    public MyViewModel(MyModel model) {
        this.model = model;

        model.addObserver(this);
    }

    public void moveCharacter(KeyEvent keyEvent){

        int direction;

        switch (keyEvent.getKeyChar()) {

            // direcrtion 0 --> UP
            // direcrtion 1 --> DOWN
            // direcrtion 2 --> LEFT
            // direcrtion 3 --> RIGHT
            // direcrtion 4 --> <\
            // direcrtion 5 --> />
            // direcrtion 6 --> </
            // direcrtion 7 --> \>

            case 8 -> direction = 0;
            case 2 -> direction = 1;
            case 4 -> direction = 2;
            case 6 -> direction = 3;
            case 7 -> direction = 4;
            case 9 -> direction = 5;
            case 1 -> direction = 6;
            case 3 -> direction = 7;
        }
    }

    @Override
    public void update(Observable o, Object arg) {

        if (arg == null)
            return;

        String option = (String) arg;

        switch (option)
        {
            case "Character Location Changed":

                rowLocation = model.getRowLocation();
                colLocation = model.getColLocation();

                setChanged();
                notifyObservers("Character Location Changed");
                break;
        }

    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }

    public void setRowLocation(int rowLocation) {
        this.rowLocation = rowLocation;
    }

    public void setColLocation(int colLocation) {
        this.colLocation = colLocation;
    }

    public void setModel(MyModel model) {
        this.model = model;
    }
}

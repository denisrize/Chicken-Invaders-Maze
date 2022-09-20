package View;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Observable;


public class Win {
    public Label mazeRows;
    public Label mazeCols;
    public Label playerSteps;
    public Label optimalSteps;
    public Label gamePoints;
    public Label help;
    public ImageView trophyImage;
    public MyViewController myView;



    public void setWinWindow(String rows,String cols,String pSteps,String oSteps, String gameP,Boolean helpFlag,MyViewController mv){
        mazeRows.setText(rows);
        mazeCols.setText(cols);
        playerSteps.setText(pSteps);
        optimalSteps.setText(oSteps);
        gamePoints.setText(gameP);
        if(helpFlag) help.setText("Yes");
        else help.setText("No");
        FileInputStream inputstream = null;
        int points = Integer.parseInt(gameP);
        try{
            if( points >= 80 )
                inputstream = new FileInputStream("Resources/Images/gold.png");
            else if( points >= 50)
                inputstream = new FileInputStream("Resources/Images/silver.png");
            else
                inputstream = new FileInputStream("Resources/Images/bronze.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        trophyImage.setImage(new Image(inputstream));

    }


    public void closeWindow(MouseEvent mouseEvent) {
        Stage stage = (Stage) trophyImage.getScene().getWindow();
        stage.close();
    }
}

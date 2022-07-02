package View;


import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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
        Image tr = null;
        int points = Integer.parseInt(gameP);
        if( points >= 80 )
            tr = new Image("Images/gold.jpg");
        else if( points >= 50)
            tr = new Image("Images/silver.jpg");
        else
            tr = new Image("Images/bronze.jpg");

        trophyImage.setImage(tr);

    }




}

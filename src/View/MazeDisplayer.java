package View;

import algorithms.mazeGenerators.Maze;
import algorithms.search.SearchableMaze;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class MazeDisplayer extends Canvas {

    private Maze maze;
    StringProperty imageFileNameWall = new SimpleStringProperty();
    StringProperty imageFileNamePlayer = new SimpleStringProperty();
    StringProperty imageFileNameTarget = new SimpleStringProperty();

    private int row_player =-1;
    private int col_player =-1;
    private int row_target = -1;
    private int col_target = -1;

    public int getRow_target() {
        return row_target;
    }

    public int getCol_target() {
        return col_target;
    }

    public int getRow_player() {
        return row_player;
    }

    public int getCol_player() {
        return col_player;
    }

    public void setPlayerPosition(int row,int col){
        this.row_player = row;
        this.col_player = col;
    }

    public void setTargetPosition(int row,int col){
        this.row_target = row;
        this.col_target = col;
    }

    public Maze getMaze() {
        return maze;
    }

    public String getImageFileNameWall() {
        return imageFileNameWall.get();
    }

    public String getImageFileNamePlayer() {
        return imageFileNamePlayer.get();
    }

    public String getImageFileNameTarget() {
        return imageFileNameTarget.get();
    }
    public void setImageFileNameTarget(String imageFileNameTarget) {
        this.imageFileNameTarget.set(imageFileNameTarget);
    }

    public void setImageFileNameWall(String imageFileNameWall) {
        this.imageFileNameWall.set(imageFileNameWall);
    }

    public void setImageFileNamePlayer(String imageFileNamePlayer) {
        this.imageFileNamePlayer.set(imageFileNamePlayer);
    }

    public void drawMaze(Maze maze) {
        this.maze = maze;
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.getRow();
            int cols = maze.getCol();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.BROWN);

            Image wallImage = null;
            try {
                wallImage = new Image(new FileInputStream(getImageFileNameWall()));
            } catch (FileNotFoundException e) {
                System.out.println("There is no wall image.");
            }

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    //if it is a wall:
                    if(maze.getPosition(i, j).isWall()){

                        double w = j * cellWidth;
                        double h = i * cellHeight;
                        // set wall image
                        if(wallImage == null)
                            graphicsContext.fillRect(h, w, cellWidth, cellHeight);
                        else
                            graphicsContext.drawImage(wallImage,w,h,cellWidth,cellHeight);
                    }
                }
            }

            setTargetImage(cellWidth,cellHeight);
            setPlayerImage(cellWidth,cellHeight);
        }
    }

    private void setTargetImage(double cellWidth,double cellHeight){
        if( row_target == -1 || col_target == -1){
            row_target = maze.getGoalPosition().getRowIndex();
            col_target = maze.getGoalPosition().getColumnIndex();
        }
        GraphicsContext graphicsContext = getGraphicsContext2D();
        double h_player =  row_target * cellHeight;
        double w_player =  col_target * cellWidth;
        Image TargetImage = null;
        try {
            TargetImage = new Image(new FileInputStream(getImageFileNameTarget()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image target.");
        }

        graphicsContext.setFill(Color.CHOCOLATE);
        if(TargetImage == null)
            graphicsContext.fillRect(h_player, w_player, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(TargetImage,w_player,h_player,cellWidth,cellHeight);
    }

    private void setPlayerImage(double cellWidth,double cellHeight){
        if( col_player == -1 || row_player == -1){
            col_player = maze.getStartPosition().getColumnIndex();
            row_player = maze.getStartPosition().getRowIndex();
        }
        GraphicsContext graphicsContext = getGraphicsContext2D();
        double h_player = row_player * cellHeight;
        double w_player = col_player * cellWidth;
        Image playerImage = null;
        try {
            playerImage = new Image(new FileInputStream(getImageFileNamePlayer()));
        } catch (FileNotFoundException e) {
            System.out.println("There is no Image player.");
        }
        double h = maze.getStartPosition().getColumnIndex() * cellWidth;
        double w = maze.getStartPosition().getRowIndex() * cellHeight;
        graphicsContext.setFill(Color.BLUE);
        if(playerImage == null)
            graphicsContext.fillRect(h_player, w_player, cellWidth, cellHeight);
        else
            graphicsContext.drawImage(playerImage,w_player,h_player,cellWidth,cellHeight);
    }
}

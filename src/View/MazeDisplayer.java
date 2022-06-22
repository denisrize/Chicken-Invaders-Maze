package View;

import algorithms.search.SearchableMaze;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class MazeDisplayer extends Canvas {

    private SearchableMaze maze;

    public void drawMaze(SearchableMaze maze) {
        this.maze = maze;
        draw();
    }

    private void draw() {
        if(maze != null){
            double canvasHeight = getHeight();
            double canvasWidth = getWidth();
            int rows = maze.maze.getRow();
            int cols = maze.maze.getCol();

            double cellHeight = canvasHeight / rows;
            double cellWidth = canvasWidth / cols;

            GraphicsContext graphicsContext = getGraphicsContext2D();
            //clear the canvas:
            graphicsContext.clearRect(0, 0, canvasWidth, canvasHeight);
            graphicsContext.setFill(Color.RED);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    //if it is a wall:
                    if(maze.maze.getPosition(i, j).isWall()){

                        double x = j * cellWidth;
                        double y = i * cellHeight;
                        graphicsContext.fillRect(x, y, cellWidth, cellHeight);
                    }
                }
            }
        }
    }
}

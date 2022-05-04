package algorithms.mazeGenerators;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    Random rand = new Random();

    public Maze generate(int row, int col){
        Maze maze = new Maze(row,col);
        Position[][] grid = maze.getGrid();
        Position start = grid[rand.nextInt(row)][rand.nextInt(col)];
        start.setWall(false);
        maze.setStartPosition(start);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(rand.nextInt(2) == 0) grid[i][j].setWall(false);

            }
        }

        Position goal;
        do{
            goal = grid[rand.nextInt(row)][rand.nextInt(col)];
        }while(goal.isWall());
        maze.setGoalPosition(goal);

        return maze;

    }




}

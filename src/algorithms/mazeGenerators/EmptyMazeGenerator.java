package algorithms.mazeGenerators;

import java.util.Random;

public class EmptyMazeGenerator extends AMazeGenerator{

    public Maze generate(int row, int col){
        Random rand = new Random();
        Maze maze = new Maze(row,col);
        Position[][] grid = maze.getGrid();
        maze.setStartPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);
        maze.setGoalPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                removeAllWalls(grid[i][j]);
            }
        }
        return maze;
    }

    private void removeAllWalls(Position cell){
        boolean[] walls = cell.getWalls();
        walls[0] = false;
        walls[1] = false;
        walls[2] = false;
        walls[3] = false;
    }
}

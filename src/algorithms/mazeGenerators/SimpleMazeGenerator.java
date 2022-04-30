package algorithms.mazeGenerators;
import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator{
    Random rand = new Random();

    public Maze generate(int row, int col){
        Maze maze = new Maze(row,col);
        Position[][] grid = maze.getGrid();
        maze.setStartPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);
        maze.setGoalPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                removeRandomWall(grid[i][j]);
            }
        }
        return maze;

    }

    private void removeRandomWall(Position cell){
        boolean[] walls = cell.getWalls();
        walls[rand.nextInt(3)] = false;
    }


}

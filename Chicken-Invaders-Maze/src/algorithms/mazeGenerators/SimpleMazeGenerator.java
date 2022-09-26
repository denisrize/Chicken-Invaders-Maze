package algorithms.mazeGenerators;
import algorithms.mazeGenerators.AMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.Random;

public class SimpleMazeGenerator extends AMazeGenerator {
    Random rand = new Random();


    public Maze generate(int row, int col){
        if( row < 1 || col < 1){
            return null;
        }
        Maze maze = new Maze(row,col);
        Position [][] grid = maze.getGrid();

        if( row < 4 || col < 4){
            createDefaultMaze(maze,grid);
            return maze;
        }


        Position start = grid[0][rand.nextInt(col)];
        start.setWall(false);
        maze.setStartPosition(start);

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(rand.nextInt(2) == 0) grid[i][j].setWall(false);

            }
        }

        Position goal;
        goal = grid[row-1][rand.nextInt(col)];
        goal.setWall(false);
        maze.setGoalPosition(goal);

        createPassage(start,goal,grid);
        return maze;

    }

    private void createDefaultMaze(Maze maze,Position[][] grid){

        for(int i=0;i< maze.getCol();i++){
            grid[0][i].setWall(false);
        }
        maze.setStartPosition(grid[0][0]);
        maze.setGoalPosition(grid[0][maze.getCol()-1]);
    }

    private void createPassage(Position start,Position goal, Position[][] grid){
        Position initial,target;
        if(start.getRowIndex() < goal.getRowIndex()){
            initial = start;
            target = goal;
        }
        else {
            target = start;
            initial = goal;
        }

        for(int i = initial.getRowIndex()+1 ;i<= target.getRowIndex() ;i++){
            grid[i][initial.getColumnIndex()].setWall(false);
        }

        int targetRow = target.getRowIndex();
        if(initial.getColumnIndex() > target.getColumnIndex()){
            Position temp = target;
            target = initial;
            initial = temp;

        }

        for(int i=initial.getColumnIndex()+1;i<target.getColumnIndex();i++){
            grid[targetRow][i].setWall(false);
        }

    }




}

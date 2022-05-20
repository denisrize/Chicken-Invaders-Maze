package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {

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

        Stack<Position> stack = new Stack<Position>();
        maze.setStartPosition(grid[0][rand.nextInt(col)]);
        Position next,current = maze.getStartPosition();
        stack.push(current);
        current.setWall(false);

        while(!stack.isEmpty()){
            while( (next = getRandomOption(current,grid,row,col)) == null && !stack.isEmpty()){
                current = stack.pop();
            }
            if( next == null) break;

            next.setWall(false);
            createPassage(current,next,grid);
            current = next;
            stack.push(current);
            if(maze.getGoalPosition() == null && (current.getRowIndex() == row-1 || current.getColumnIndex() == col-1 || current.getColumnIndex() == 0)){
                maze.setGoalPosition(current);
            }
        }
        return maze;
    }

    private void createDefaultMaze(Maze maze,Position[][] grid){

        for(int i=0;i< maze.getCol();i++){
            grid[0][i].setWall(false);
        }
        maze.setStartPosition(grid[0][0]);
        maze.setGoalPosition(grid[0][maze.getCol()-1]);
    }


    private void createPassage(Position cell1, Position cell2, Position[][] grid){
         int x = (cell1.getRowIndex() + cell2.getRowIndex())/2;
         int y = (cell1.getColumnIndex() + cell2.getColumnIndex())/2;
         grid[x][y].setWall(false);
    }

    private Position getRandomOption( Position cell, Position[][] grid, int row , int col){
        ArrayList<Position> neighbours = new ArrayList<Position>();
        int y = cell.getColumnIndex();
        int x = cell.getRowIndex();

        if( x > 1 && grid[x-2][y].isWall()){ neighbours.add(grid[x -2][y]);} // check top option
        if( y > 1 && grid[x][y-2].isWall()){ neighbours.add(grid[x][y-2]);} // check left option
        if( x < row-2 && grid[x+2][y].isWall()){ neighbours.add(grid[x+2][y]);} // check bottom option
        if( y < col-2 && grid[x][y+2].isWall()){ neighbours.add(grid[x][y+2]);} // check right border

        if( neighbours.size() > 0){return neighbours.get(rand.nextInt(neighbours.size()));}
        return null;
    }




}

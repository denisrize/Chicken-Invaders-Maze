package algorithms.mazeGenerators;

import java.util.*;

public class MyMazeGenerator extends AMazeGenerator {

    Random rand = new Random();

    public Maze generate(int row, int col){
        Stack<Position> stack = new Stack<Position>();
        Maze maze = new Maze(row,col);
        Position [][] grid = maze.getGrid();
        maze.setStartPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);

        Position next,current = maze.getStartPosition();
        stack.push(current);
        current.setWall(false);

        while(!stack.isEmpty()){
            while( (next = getRandomOption(current,grid,row,col)) == null && !stack.isEmpty()){
                current = stack.pop();
                next = getRandomOption(current,grid,row,col);
            }
            if( next == null) break;

            next.setWall(false);
            makePassage(current,next,grid);
            current = next;
            stack.push(current);
        }

        Position goal;
        do{
            goal = grid[rand.nextInt(row)][rand.nextInt(col)];
        }while(goal.isWall());
        maze.setGoalPosition(goal);

        return maze;
    }

    private void makePassage(Position cell1, Position cell2, Position[][] grid){
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
        if( x < row-3 && grid[x+2][y].isWall()){ neighbours.add(grid[x+2][y]);} // check bottom option
        if( y < col-3 && grid[x][y+2].isWall()){ neighbours.add(grid[x][y+2]);} // check right border

        if( neighbours.size() > 0){return neighbours.get(rand.nextInt(neighbours.size()));}
        return null;
    }




}

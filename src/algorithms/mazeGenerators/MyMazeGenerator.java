package algorithms.mazeGenerators;
import java.io.*;
import java.util.*;

public class MyMazeGenerator extends AMazeGenerator{

    Random rand = new Random();

    public Maze generate(int row, int col){
        Stack<Position> stack = new Stack<Position>();
        Maze maze = new Maze(row,col);
        Position [][] grid = maze.getGrid();
        maze.setStartPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);
        maze.setGoalPosition(grid[rand.nextInt(row)][rand.nextInt(col)]);

        Position next,current = maze.getStartPosition();
        current.visited = true;
        int i=0;
        while(i < (row*col)-1) {

            next = checkNeighbours(grid, current,row,col);
            while(next == null && stack.size() > 0  ){
                    current = stack.pop();
                    if(current.getNeighbours() != null){current.getNeighbours().clear(); } // Check different neighbour

                    next = checkNeighbours(grid, current,row,col);
            }

            next.visited = true;
            stack.push(current);
            removeWalls(current, next);
            current = next;
            i++;
        }
        return maze;
    }


    private Position checkNeighbours(Position [][] grid, Position cell,int row,int col){
        ArrayList<Position> neighbours = cell.getNeighbours();
        int y = cell.getColumnIndex();
        int x = cell.getRowIndex();

        if( x > 0 && !grid[x -1][y].visited){ neighbours.add(grid[x -1][y]);} // check top border
        if( y > 0 && !grid[x][y-1].visited){ neighbours.add(grid[x][y-1]);} // check left border
        if( x < row-1 && !grid[x+1][y].visited){ neighbours.add(grid[x+1][y]);} // check bottom border
        if( y < col-1 && !grid[x][y+1].visited){ neighbours.add(grid[x][y+1]);} // check right border

        if( neighbours.size() > 0){return neighbours.get(rand.nextInt(neighbours.size()));}

        return null;
    }

    private void removeWalls(Position cell1, Position cell2){
        boolean[] walls1 = cell1.getWalls();
        boolean[] walls2 = cell2.getWalls();

        // check bottom && top walls
        int x = cell1.getRowIndex() - cell2.getRowIndex();
        if( x > 0){
            walls1[0] = false;
            walls2[2] = false;
        }
        if(x < 0){
            walls1[2] = false;
            walls2[0] = false;
        }

        //check right&& left walls
        int y = cell1.getColumnIndex() - cell2.getColumnIndex();
        if( y > 0){
            walls1[3] = false;
            walls2[1] = false;
        }
        if( y < 0){
            walls1[1] = false;
            walls2[3] = false;
        }
    }


}

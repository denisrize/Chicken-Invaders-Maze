package algorithms.mazeGenerators;
import algorithms.search.MazeState;

import java.io.Serializable;
import java.util.ArrayList;

public class Position implements Serializable {
    int row;
    int col;
    boolean wall = true;
    boolean visited = false;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean isVisited() {return visited;}

    public void setVisited(boolean vis) {visited = vis;}

    public void setWall(boolean wall) {
        this.wall = wall;
    }

    public boolean isWall() {
        return wall;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return col;
    }

    @Override
    public boolean equals(Object ob){
        if( ob == this) return true;
        if(!(ob instanceof Position)) return false;

        Position p = (Position) ob;
        return this.getColumnIndex() == p.getColumnIndex() && (this.getRowIndex() == p.getRowIndex());
    }

    @Override
    public String toString(){
        int x = row+1;
        int y = col+1;
        return "{"+ x + "," + y +"}";
    }
}

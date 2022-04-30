package algorithms.mazeGenerators;
import java.util.ArrayList;

public class Position {
    int row;
    int col;
    public boolean visited = false;
    boolean[] walls = {true,true,true,true}; // top,right,bottom,left
    ArrayList<Position> neighbours = new ArrayList<Position>();

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public boolean[] getWalls() {
        return walls;
    }

    public ArrayList<Position> getNeighbours() {
        return neighbours;
    }

    public int getRowIndex() {
        return row;
    }

    public int getColumnIndex() {
        return col;
    }

    public String toString(){
        return "{"+ row+1 + "," + col+1 +"}";
    }
}

package algorithms.mazeGenerators;
import java.util.ArrayList;

public class Position {
    int row;
    int col;
    boolean wall = true;

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

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

    public String toString(){
        int x = row+1;
        int y = col+1;
        return "{"+ x + "," + y +"}";
    }
}

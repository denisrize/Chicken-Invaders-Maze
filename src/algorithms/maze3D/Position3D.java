package algorithms.maze3D;

public class Position3D {
    int row;
    int col;
    int depth;
    boolean wall = true;

    public Position3D(int depth,int row,int col){
        this.row = row;
        this.col = col;
        this.depth = depth;
    }

    public int getDepthIndex(){return 0;}
    public int getRowIndex(){return 0;}
    public int getColumnIndex(){return 0;}
}

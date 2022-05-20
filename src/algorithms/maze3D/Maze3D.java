package algorithms.maze3D;

public class Maze3D {
    private Position3D startPos;
    private Position3D goalPos;
    private Position3D [][][] grid;
    int depth;
    int row;
    int col;

    public Maze3D(int depth, int row, int col) {
        this.depth = depth;
        this.row = row;
        this.col = col;
        for( int i=0;i<depth;i++){
            for(int j=0;j<row;j++){
                for(int w=0;w<col;w++){
                    grid[i][j][w] = new Position3D(i,j,w);

                }
            }
        }
    }




    public int[][][] getMap(){return null;};
    public Position3D getStartPosition(){return startPos;};
    public Position3D getGoalPosition(){return goalPos;};

}

package algorithms.mazeGenerators;

public class Maze {
    private Position startPos;
    private Position goalPos;
    private Position [][] grid;
    int row,col;

    public Maze(int row, int col) {
        this.row = row;
        this.col = col;
        this.grid = new Position[row][col];
        for( int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                grid[i][j] = new Position(i,j);
            }
        }
    }

    public Position getStartPosition(){ return startPos;}

    public Position getGoalPosition(){return goalPos;}

    public void print(){
        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(grid[i][j] == startPos){
                    System.out.print("S ");
                    continue;
                }
                if(grid[i][j] == goalPos){
                    System.out.print("E ");
                    continue;
                }

                if(grid[i][j].isWall()) System.out.print("1 ");
                else System.out.print("0 ");
            }
            System.out.print("\n");
        }

    }

    public int getRow() {return row;}

    public int getCol() {return col;}

    public void setStartPosition(Position startPos) {
        this.startPos = startPos;
    }

    public void setGoalPosition(Position goalPos) {
        this.goalPos = goalPos;
    }

    public Position[][] getGrid() {
        return grid;
    }
}

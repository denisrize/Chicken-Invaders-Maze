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

        System.out.printf(" _ _".repeat(col)+ "\n");
        for(int i=0;i<row;i++){
            String bottom = new String() ;
            for(int j=-1;j<= col;j++){
                if( j==-1 || j == col){
                    System.out.printf("|");
                    bottom += "|";
                    continue;
                }

                Position current = grid[i][j];
                if( current == startPos) {System.out.printf("S ");}

                else if( current == goalPos) {System.out.printf("E ");}

                else {System.out.printf("0 ");}

                //check right border
                if(j < col-1){
                    if( current.getWalls()[1] ) {System.out.printf("1 ");}
                    else {System.out.printf("0 ");}
                }
                if(i < row-1 ){
                    if(current.getWalls()[2]){ bottom += "1 "; }
                    else { bottom += "0 "; }
                    if(j < col-1){ bottom += "1 ";}

                }



            }
            System.out.printf("\n");
            if(i < row-1){System.out.println(bottom);};

        }
        System.out.printf(" _ _".repeat(col));


    }

    public void setStartPosition(Position startPos) {
        this.startPos = startPos;
    }
    public void setGoalPosition(Position endPos) {
        this.goalPos = endPos;
    }

    public Position[][] getGrid() {
        return grid;
    }
}

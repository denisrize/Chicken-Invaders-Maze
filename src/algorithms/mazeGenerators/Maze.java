package algorithms.mazeGenerators;

import java.io.Serializable;

public class Maze implements Serializable {
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

    // TODO: Finish the decompressor and then build this constructor
    public Maze(byte[] byteArray)
    {

        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;

        // Row data
        for (int i=0; i<4; i++)
        {
            row += byteArray[i];
        }

        // Col data
        for (int i=4; i<8; i++)
        {
            col += byteArray[i];
        }

        // Start Position X
        for (int i=0; i<4; i++)
        {
            startX += byteArray[byteArray.length-16+i];
        }

        // Start Position Y
        for (int i=0; i<4; i++)
        {
            startY += byteArray[byteArray.length-12+i];
        }

        // End Position X
        for (int i=0; i<4; i++)
        {
            endX += byteArray[byteArray.length-8+i];
        }

        // End Position Y
        for (int i=0; i<4; i++)
        {
            endY += byteArray[byteArray.length-4+i];
        }


        int index = 8;

        this.grid = new Position[row][col];
        for( int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                grid[i][j] = new Position(i,j);

                // If byte is 0 so its Not a wall
                grid[i][j].setWall(byteArray[index++] - 48 != 0);
            }
        }

        this.setStartPosition(new Position(startX, startY));
        this.setGoalPosition(new Position(endX, endY));
    }

    public byte[] toByteArray(){

        byte[] rowOut = new byte[4];
        byte[] colOut = new byte[4];
        byte[] startPositionX = new byte[4];
        byte[] startPositionY = new byte[4];
        byte[] endPositionX = new byte[4];
        byte[] endPositionY = new byte[4];

        byte[] ans = new byte[col*row + 16];

        int temp = row;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                rowOut[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                rowOut[i] = (byte) temp;
                break;
            }
        }

        temp = col;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                colOut[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                colOut[i] = (byte) temp;
                break;
            }
        }


        temp = startPos.row;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                startPositionX[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                startPositionX[i] = (byte) temp;
                break;
            }
        }

        temp = startPos.col;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                startPositionY[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                startPositionY[i] = (byte) temp;
                break;
            }
        }

        temp = goalPos.row;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                endPositionX[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                endPositionX[i] = (byte) temp;
                break;
            }
        }

        temp = goalPos.col;
        for (int i=0; i<4; i++)
        {
            if (temp > 255)
            {
                endPositionY[i] = (byte) 255;
                temp -= 255;
            }
            else
            {
                endPositionY[i] = (byte) temp;
                break;
            }
        }

        // Copy to answer row array
        System.arraycopy(rowOut, 0, ans, 0, 4);

        // Copy to answer col array
        System.arraycopy(colOut, 0, ans, 4, 4);

        int index = 8;
        for (int i=0; i<col; i++)
        {
            for (int j=0; j<row; j++)
            {
                if (grid[i][j].isWall())
                    ans[index] = 1;
                else
                    ans[index] = 0;

                index++;
            }
        }

        // Copy startPositionX to ans
        System.arraycopy(startPositionX, 0, ans, ans.length - 16, 4);

        // Copy startPositionY to ans
        System.arraycopy(startPositionY, 0, ans, ans.length - 12, 4);

        // Copy endPositionX to ans
        System.arraycopy(endPositionX, 0, ans, ans.length - 8, 4);

        // Copy endPositionY to ans
        System.arraycopy(endPositionY, 0, ans, ans.length - 4, 4);

        return ans;
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

    public Position getPosition(int row,int col){
        if ( row < this.row && row >= 0 && col < this.col && col >= 0){
            return grid[row][col];
        }
        return null;
    }
    @Override
    public boolean equals(Object other){
        if( other == this) return true;

        if(!( other instanceof Maze)) return false;

        Maze otherMaze = (Maze)other;
        if( otherMaze.getRow() != row || otherMaze.getCol() != col ) return false;

        if( !(startPos.equals(otherMaze.getStartPosition())) || !(goalPos.equals(otherMaze.getGoalPosition()))) return false;

        for(int i=0;i<row;i++){
            for(int j=0;j<col;j++){
                if(!(getPosition(i,j).equals(otherMaze.getPosition(i,j)))) return false;
            }
        }
        return true;
    }
}

package Model;

import algorithms.mazeGenerators.IMazeGenerator;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.MyMazeGenerator;
import algorithms.search.SearchableMaze;

import java.util.Observable;

public class MyModel extends Observable implements IModel{

    private int rowLocation;
    private int colLocation;
    public IMazeGenerator generator;
    private Maze maze;


    public void updateCharacterLocation(int step) {

        switch (step)
        {
            case 1: // </
                if(rowLocation < maze.getRow()-1 && colLocation > 0){
                    rowLocation += 1;
                    colLocation -= 1;
                }
                break;
            case 2: // down
            if (rowLocation < maze.getRow() - 1)
                rowLocation += 1;
            break;
            case 3: // \>
                if( colLocation < maze.getCol() -1 && rowLocation < maze.getRow() - 1){
                    colLocation += 1;
                    rowLocation += 1;
                }
                break;
            case 4: // left
                if (colLocation > 0)
                    colLocation -= 1;
                break;
            case 5:
                break;
            case 6:
                if (colLocation < maze.getCol() - 1)
                    colLocation += 1;
                break;
            case 7: // <\
                if (rowLocation > 0 && colLocation > 0) {
                    rowLocation -= 1;
                    colLocation -= 1;
                }
                break;
            case 8://up
                if (rowLocation > 0)
                    rowLocation -= 1;
                break;
            case 9:
                if (rowLocation > 0 && colLocation < maze.getCol() - 1) {
                    rowLocation -= 1;
                    colLocation += 1;
                }
                break;
        }

        setChanged();
        notifyObservers("Character Location Changed");
    }

    public void generateMaze(int row,int col){
        if( generator == null)
            generator = new MyMazeGenerator();
        maze = generator.generate(row,col);
        setChanged();
        notifyObservers("Maze generated");
    }

    public Maze getMaze(){ return maze;}

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }
}

package Model;

import algorithms.search.SearchableMaze;

import java.util.Observable;

public class MyModel extends Observable implements IModel{

    private int rowLocation;
    private int colLocation;
    private SearchableMaze maze;


    public void updateCharacterLocation(int step) {

        switch (step)
        {
            case 8:
                if (rowLocation > 0)
                    rowLocation -= 1;
                break;

            case 2:
                if (rowLocation < maze.maze.getRow() - 1)
                    rowLocation += 1;
                break;

            case 4:
                if (colLocation > 0)
                    rowLocation -= 1;
                break;

            case 6:
                if (colLocation < maze.maze.getCol() - 1)
                    rowLocation += 1;
                break;

            case 7:
                if (rowLocation > 0 && colLocation > 0) {
                    rowLocation -= 1;
                    colLocation -=1;
                }
                break;

            case 9:
                if (rowLocation > 0 && colLocation < maze.maze.getCol() - 1) {
                    rowLocation -= 1;
                    colLocation += 1;
                }
                break;

            case 1:
                if (rowLocation < maze.maze.getRow() - 1 && colLocation > 0) {
                    rowLocation += 1;
                    colLocation -= 1;
                }
                break;

            case 3:
                if (rowLocation < maze.maze.getRow() - 1 && colLocation < maze.maze.getCol() - 1) {
                    rowLocation += 1;
                    colLocation += 1;
                }
                break;
        }

        setChanged();
        notifyObservers("Character Location Changed");
    }

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }
}

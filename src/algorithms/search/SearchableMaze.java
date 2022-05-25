package algorithms.search;

import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

import java.util.ArrayList;
import java.util.Comparator;

public class SearchableMaze implements ISearchable{


    public Maze maze;

    public SearchableMaze(Maze m) { maze = m;}

    @Override
    public MazeState getStartState() { return new MazeState(maze.getStartPosition()); }

    @Override
    public MazeState getGoalState() { return new MazeState(maze.getGoalPosition()); }


    @Override
    public ArrayList<AState> getAllPossibleStates(AState s) {

        ArrayList<AState> neighbors = new ArrayList<AState>();

        MazeState mazeState = (MazeState) s;    // DownCasting

        Position[][] curGrid = maze.getGrid();


        Position curPosition = mazeState.getPosition();

        int curRow = curPosition.getRowIndex();
        int curCol = curPosition.getColumnIndex();

        for (int row=-1; row<2; row++){
            for (int col=-1; col<2; col++){

                if (row == 0 && col == 0) continue;

                // Check all the 8 options beside the cur position and if valid add to neighbours

                if (curRow+row < maze.getRow() && curRow+row >= 0 && curCol+col >= 0  && curCol+col < maze.getCol() ) {

                    if (!curGrid[curRow + row][curCol + col].isWall() )
                        neighbors.add(new MazeState(curGrid[curRow + row][curCol + col]));
                }
            }
        }

        return neighbors;
    }

    @Override
    public int cleanSearchable() {
        int numOfNodesVisited = 0;
        for (int row=0; row<maze.getRow(); row++) {
            for (int col = 0; col < maze.getCol(); col++) {

                if (maze.getGrid()[row][col].isVisited() && !maze.getGrid()[row][col].isWall()){
                    numOfNodesVisited++;
                }
                maze.getGrid()[row][col].setVisited(false);

            }
        }
        return numOfNodesVisited;
    }
}
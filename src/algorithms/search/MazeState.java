package algorithms.search;

import java.lang.*;
import algorithms.mazeGenerators.Maze;
import algorithms.mazeGenerators.Position;

public class MazeState extends AState{

    private Position position;

    public MazeState() {position=null;}

    public MazeState(Position p){
        this.position = p;
    }



    @Override
    public boolean equals(Object os)
    {
        MazeState ms = (MazeState) os;
        return this.position.getColumnIndex() == ms.position.getColumnIndex() && (this.position.getRowIndex() == ms.position.getRowIndex());
    }

    public Position getPosition() {return position;}

    @Override
    public void setVisited(boolean visited) {
        this.visited = visited;
        position.setVisited(visited);
    }

    @Override
    public boolean isVisited() {return position.isVisited();}

    @Override
    public void setCost() {

        MazeState parent = (MazeState) this.getParentState();

        if (Math.abs(parent.getPosition().getRowIndex() - this.getPosition().getRowIndex()) == 1
            && Math.abs(parent.getPosition().getColumnIndex() - this.getPosition().getColumnIndex()) == 1) {

            this.setCost(parent.getCost() + 15);
        }

        else{
            this.setCost(parent.getCost() + 10);
        }
    }


    @Override
    public String toString() {
        return "MazeState{" +
                "position=" + position +
                '}';
    }
}

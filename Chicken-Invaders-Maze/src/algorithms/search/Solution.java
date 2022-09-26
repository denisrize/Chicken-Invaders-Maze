package algorithms.search;

import java.io.Serializable;
import java.util.ArrayList;

public class Solution implements Serializable {
    private volatile int numOfSteps;
    private ArrayList<AState>  solution;

    public Solution() {
        this.numOfSteps = 0;
        this.solution = new ArrayList<>();
    }

    public void setIntoSolutionArray(AState s) {
        solution.add(s);
        numOfSteps++;
    }

    public ArrayList<AState> getSolutionPath() {return solution; }

    public String toString(){
        return Integer.toString(numOfSteps);
    }
}

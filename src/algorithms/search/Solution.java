package algorithms.search;

import java.util.ArrayList;

public class Solution {

    private ArrayList<AState> solution = new ArrayList<>();

    public void setIntoSolutionArray(AState s) {solution.add(s);}

    public ArrayList<AState> getSolutionPath() {return solution; }
}

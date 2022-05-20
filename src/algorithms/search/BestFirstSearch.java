package algorithms.search;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

public class BestFirstSearch extends ASearchingAlgorithm{

    PriorityQueue<AState> openList = new PriorityQueue<AState>( new AStateComparator());

    @Override
    public Solution solve(ISearchable s) {

        if(s == null) return null;

        AState start = s.getStartState();
        AState goal = s.getGoalState();

        start.setParentState(null);
        start.setVisited(true);
        start.setCost(0);
        this.openList.add(start);

        while (!openList.isEmpty()) {

            AState curState = openList.poll();
            ArrayList<AState> curNeighbours = s.getAllPossibleStates(curState);

            for (AState curNeighbour : curNeighbours) {         // For loop through all neighbours
                    curNeighbour.setVisited(true);
                    openList.add(curNeighbour);
                    curNeighbour.setParentState(curState);
                    curNeighbour.setCost();

                if (curNeighbour.equals(goal)) {

                    Solution sol = new Solution();

                    while (curNeighbour != null){

                        sol.setIntoSolutionArray(curNeighbour);
                        curNeighbour = curNeighbour.getParentState();
                    }

                    visitedNodes = s.cleanSearchable();

                    return sol;
                }

            }
        }

        return null;
    }

    @Override
    public String getName() {
        return "Best First Search";
    }

    class AStateComparator implements Comparator<AState> {

        @Override
        public int compare(AState state1, AState state2) {

            if( state1.getCost() > state2.getCost()) return 1;
            else if(state1.getCost() < state2.getCost()) return -1;
            return 0;

        }
    }
}

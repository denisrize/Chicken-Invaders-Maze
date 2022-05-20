package algorithms.search;

import java.util.*;

public class DepthFirstSearch extends ASearchingAlgorithm {

    Stack<AState> openList = new Stack<>();

    @Override
    public Solution solve(ISearchable s) {
        AState start = s.getStartState();
        AState goal = s.getGoalState();
        Random rand = new Random();

        start.setParentState(null);
        start.setVisited(true);
        openList.add(start);
        while (!openList.empty()) {

            AState curState = openList.pop();

            while( true) {

                ArrayList<AState> neighbours = s.getAllPossibleStates(curState);
                if(neighbours.size() == 0) break;

                AState curNeighbour = neighbours.get(rand.nextInt(neighbours.size()));
                curNeighbour.setParentState(curState);
                curNeighbour.setVisited(true);

                openList.add(curNeighbour);
                curState = curNeighbour;

                if(curState.equals(goal)){
                    Solution sol = new Solution();

                    while (curState != null) {

                        sol.setIntoSolutionArray(curState);
                        curState = curState.getParentState();
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
        return "DFS";
    }


}


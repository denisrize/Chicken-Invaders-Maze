package algorithms.search;

import java.util.*;

public class BestFirstSearch extends ASearchingAlgorithm{

    PriorityQueue<AState> openList = new PriorityQueue<AState>( (Comparator<AState>)(AState s1,AState s2) -> s1.getCost() - s2.getCost());
    LinkedList<AState> closeList = new LinkedList<AState>();

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
            ArrayList<AState> curNeighbors = s.getAllPossibleStates(curState);

            for (AState curNeighbor : curNeighbors) {         // For loop through all neighbours

                    if(!curNeighbor.isVisited()){
                        curNeighbor.setVisited(true);
                        openList.add(curNeighbor);
                        curNeighbor.setParentState(curState);
                        curNeighbor.setCost(curState);
                    }
                    else{ // check if new path is better than the previous.
                        AState visitedState = getFromList(curNeighbor);
                        if( visitedState == null)  visitedState = getFromQueue(curNeighbor);

                        int oldCost = visitedState.getCost();
                        visitedState.setCost(curState);

                        if(oldCost < visitedState.getCost()){
                            visitedState.setCost(oldCost);
                            continue;
                        }

                        else if( !openList.contains(visitedState)){
                            openList.add(visitedState);
                            closeList.remove(visitedState);
                        }
                        visitedState.setParentState(curState);
                    }


                if (curNeighbor.equals(goal)) {

                    Solution sol = new Solution();

                    while (curNeighbor != null){

                        sol.setIntoSolutionArray(curNeighbor);
                        curNeighbor = curNeighbor.getParentState();
                    }

                    visitedNodes = s.cleanSearchable();

                    return sol;
                }

            }
            closeList.add(curState);
        }

        return null;
    }

    @Override
    public String getName() {
        return "Best First Search";
    }

    private AState getFromQueue(AState curr){
        for(AState s: openList){
            if( s.getState().equals(curr.getState())) return s;
        }
        return null;
    }
    private  AState getFromList(AState curr){
        for( AState s: closeList){
            if( s.getState().equals(curr.getState())) return s;
        }
        return null;
    }

    class AStateComparator implements Comparator<AState> {

        @Override
        public int compare(AState state1, AState state2) {

                if( state1.getCost() > state2.getCost()) return 1;
                else if(state1.getCost() < state2.getCost() ) return -1;
                return 0;

        }
    }

}

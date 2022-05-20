package algorithms.search;

import java.util.ArrayList;

public interface ISearchable {

    public ArrayList<AState> world = null;

    AState getStartState();
    AState getGoalState();
    ArrayList<AState> getAllPossibleStates(AState s);

    int cleanSearchable();
}

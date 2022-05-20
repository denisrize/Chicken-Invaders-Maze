package algorithms.search;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BestFirstSearchTest {

    BestFirstSearch bfs = new BestFirstSearch();
    @Test
    void solve() {
        assertEquals(null,bfs.solve(null));
    }

    @Test
    void getName() {
        assertEquals("Best First Search",bfs.getName());
    }
}
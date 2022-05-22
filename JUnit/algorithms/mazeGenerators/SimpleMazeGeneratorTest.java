package algorithms.mazeGenerators;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SimpleMazeGeneratorTest {

    IMazeGenerator simpleGen = new SimpleMazeGenerator();
    @Test
    void generate() {
        Maze maze = simpleGen.generate(2,2);
        assertNotNull(maze);

    }
}
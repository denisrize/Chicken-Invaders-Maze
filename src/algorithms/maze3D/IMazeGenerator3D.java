package algorithms.maze3D;

public interface IMazeGenerator3D {
    public Maze3D generate(int depth, int row, int column);

    public long measureAlgorithmTimeMillis(int depth, int row, int column);

}

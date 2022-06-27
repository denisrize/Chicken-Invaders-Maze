package Model;

import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

public class MyModel extends Observable implements IModel{

    private int rowLocation;
    private int colLocation;
    private Server genServer;
    private Server solServer;
    private Maze maze;
    private Solution sol;
    private ArrayList<MazeState> solPath;

    public MyModel(){
        IServerStrategy generator = new ServerStrategyGenerateMaze();
        IServerStrategy solver = new ServerStrategySolveSearchProblem();
        genServer = new Server(5400,5000,generator);
        solServer = new Server(5401,5000,solver);

    }

    public void updateCharacterLocation(int step) {

        switch (step)
        {
            case 1: // </
                if(rowLocation < maze.getRow()-1 && colLocation > 0 && !maze.getPosition(rowLocation+1,colLocation-1).isWall()){
                    rowLocation += 1;
                    colLocation -= 1;
                }
                break;
            case 2: // down
            if (rowLocation < maze.getRow() - 1 && !maze.getPosition(rowLocation+1,colLocation).isWall())
                rowLocation += 1;
            break;
            case 3: // \>
                if( colLocation < maze.getCol() -1 && rowLocation < maze.getRow() - 1 && !maze.getPosition(rowLocation+1,colLocation+1).isWall()){
                    colLocation += 1;
                    rowLocation += 1;
                }
                break;
            case 4: // left
                if (colLocation > 0 && !maze.getPosition(rowLocation,colLocation-1).isWall())
                    colLocation -= 1;
                break;
            case 5:
                break;
            case 6:
                if (colLocation < maze.getCol() - 1 && !maze.getPosition(rowLocation,colLocation+1).isWall())
                    colLocation += 1;
                break;
            case 7: // <\
                if (rowLocation > 0 && colLocation > 0 && !maze.getPosition(rowLocation-1,colLocation-1).isWall()) {
                    rowLocation -= 1;
                    colLocation -= 1;
                }
                break;
            case 8://up
                if (rowLocation > 0 && !maze.getPosition(rowLocation-1,colLocation).isWall())
                    rowLocation -= 1;
                break;
            case 9:
                if (rowLocation > 0 && colLocation < maze.getCol() - 1 && !maze.getPosition(rowLocation-1,colLocation+1).isWall()) {
                    rowLocation -= 1;
                    colLocation += 1;
                }
                break;
        }

        setChanged();
        if( rowLocation == maze.getGoalPosition().getRowIndex() && colLocation == maze.getGoalPosition().getColumnIndex())
            notifyObservers("Changed Location and win");
        notifyObservers("Character Location Changed");
    }


    public void loadMaze(String fileName) {
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(fileName + ".txt");
            ObjectInputStream oiss = new ObjectInputStream(fis);
            maze = (Maze) oiss.readObject();
            System.out.println("Maze start: " + maze.getStartPosition());
            oiss.close();
            rowLocation = maze.getStartPosition().getRowIndex();
            colLocation = maze.getStartPosition().getColumnIndex();

        } catch (FileNotFoundException e) {
            notifyObservers("load failed");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers("loaded successfully");

    }

    public void saveMaze(String fileName){
        if(maze != null){
            try {
                FileOutputStream fos = new FileOutputStream(fileName + ".txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(maze);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateMaze(int row,int col){
        genServer.start();
        try {
            Socket serverSocket = new Socket("127.0.0.1", 5400); // change to InetAddress.getLocalHost()
            ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
            ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
            toServer.flush();
            toServer.writeObject(new int[]{row, col});
            toServer.flush();
            byte[] compressedMaze = (byte[]) fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            InputStream is = new MyDecompressorInputStream(new ByteArrayInputStream(compressedMaze));
            byte[] decompressedMaze = new byte[row*col+24];
            is.read(decompressedMaze);
            maze = new Maze(decompressedMaze);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        genServer.stop();
        setChanged();
        notifyObservers("Maze generated");
    }


    public void solveMaze(){
        if(maze != null){
            solServer.start();
            try {
                Socket serverSocket = new Socket("127.0.0.1", 5400);// change to InetAddress.getLocalHost()
                ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
                ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
                toServer.flush();
                toServer.writeObject(maze); //send maze to server
                toServer.flush();
                sol = (Solution)fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            solServer.stop();
            if( sol != null){
                ArrayList<AState> origin = sol.getSolutionPath();
                solPath.clear();

                for (AState aState : origin) {
                    solPath.add((MazeState) aState);
                }
                setChanged();
                notifyObservers("Maze solved");
            }
        }
    }

    public Maze getMaze(){ return maze;}

    public ArrayList<MazeState> getSolutionPath(){ return solPath;}

    public int getRowLocation() {
        return rowLocation;
    }

    public int getColLocation() {
        return colLocation;
    }
}
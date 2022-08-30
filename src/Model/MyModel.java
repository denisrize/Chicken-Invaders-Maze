package Model;

import IO.MyDecompressorInputStream;
import Server.*;
import algorithms.mazeGenerators.Maze;
import algorithms.search.*;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class MyModel extends Observable implements IModel{

    private int rowLocation;
    private int colLocation;
    private final Server genServer;
    private final Server solServer;
    private Maze maze;
    private Solution sol;
    private ArrayList<MazeState> solPath;
    private int stepsCount;

    public MyModel(){
        solPath = new ArrayList<>();
        IServerStrategy generator = new ServerStrategyGenerateMaze();
        IServerStrategy solver = new ServerStrategySolveSearchProblem();
        genServer = new Server(5400,5000,generator);
        solServer = new Server(5401,5000,solver);
        genServer.start();
        solServer.start();

    }

    public void closeModel(){
        try {
            File directory = new File("SavedMazes");
            for (File f : directory.listFiles())
                f.delete();
        }catch (NullPointerException e){
            System.out.println("Need to open SavedMazes folder to save maze.");
        }
        genServer.stop();
        solServer.stop();
    }

    public void updateCharacterLocation(int step) {
        boolean wallFlag = true;
        switch (step)
        {
            case 1: // </
                if(rowLocation < maze.getRow()-1 && colLocation > 0 && !maze.getPosition(rowLocation+1,colLocation-1).isWall()){
                    rowLocation += 1;
                    colLocation -= 1;
                    wallFlag = false;
                }
                break;
            case 2: // down
                if (rowLocation < maze.getRow() - 1 && !maze.getPosition(rowLocation+1,colLocation).isWall()){
                    rowLocation += 1;
                    wallFlag = false;
                }
                break;
            case 3: // \>
                if( colLocation < maze.getCol() -1 && rowLocation < maze.getRow() - 1 && !maze.getPosition(rowLocation+1,colLocation+1).isWall()){
                    colLocation += 1;
                    rowLocation += 1;
                    wallFlag = false;
                }
                break;
            case 4: // left
                if (colLocation > 0 && !maze.getPosition(rowLocation,colLocation-1).isWall()){
                    colLocation -= 1;
                    wallFlag = false;
                }
                break;
            case 5: wallFlag = false;
                break;
            case 6:
                if (colLocation < maze.getCol() - 1 && !maze.getPosition(rowLocation,colLocation+1).isWall()){
                    colLocation += 1;
                    wallFlag = false;
                }
                break;
            case 7: // <\
                if (rowLocation > 0 && colLocation > 0 && !maze.getPosition(rowLocation-1,colLocation-1).isWall()) {
                    rowLocation -= 1;
                    colLocation -= 1;
                    wallFlag = false;
                }
                break;
            case 8://up
                if (rowLocation > 0 && !maze.getPosition(rowLocation-1,colLocation).isWall()){
                    rowLocation -= 1;
                    wallFlag = false;
                }
                break;
            case 9:
                if (rowLocation > 0 && colLocation < maze.getCol() - 1 && !maze.getPosition(rowLocation-1,colLocation+1).isWall()) {
                    rowLocation -= 1;
                    colLocation += 1;
                    wallFlag = false;
                }
                break;
        }
        if(!wallFlag) stepsCount++;


        setChanged();
        if( rowLocation == maze.getGoalPosition().getRowIndex() && colLocation == maze.getGoalPosition().getColumnIndex())
            notifyObservers("Changed Location and win");
        notifyObservers("Character Location Changed");
    }

    public int getPlayerSteps(){return stepsCount;}

    public void loadMaze(String fileName) {
        String result = "loaded successfully";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(new File("SavedMazes",fileName + ".txt"));
            ObjectInputStream oiss = new ObjectInputStream(fis);
            maze = (Maze) oiss.readObject();
            System.out.println("Maze start: " + maze.getStartPosition());
            oiss.close();
            rowLocation = maze.getStartPosition().getRowIndex();
            colLocation = maze.getStartPosition().getColumnIndex();

        } catch (FileNotFoundException e) {
            result = "load failed";
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers(result);

    }

    public void saveMaze(String fileName){
        if(maze != null){
            try {
                FileOutputStream fos = new FileOutputStream(new File("SavedMazes",fileName + ".txt"));
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(maze);
                oos.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void generateMaze(int row,int col){
        stepsCount = 0;
        try {
            Socket serverSocket = new Socket( "127.0.0.1", 5400); // change to InetAddress.getLocalHost() ,
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
            setChanged();
            notifyObservers("Maze generated");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        rowLocation = maze.getStartPosition().getRowIndex();
        colLocation = maze.getStartPosition().getColumnIndex();

    }

    public void solveMaze(){

        if(maze != null){
            try {
                Socket serverSocket = new Socket("127.0.0.1", 5401);// change to InetAddress.getLocalHost()
                ObjectOutputStream toServer = new ObjectOutputStream(serverSocket.getOutputStream());
                ObjectInputStream fromServer = new ObjectInputStream(serverSocket.getInputStream());
                toServer.flush();
                toServer.writeObject(maze); //send maze to server
                toServer.flush();
                sol = (Solution)fromServer.readObject(); //read generated maze (compressed with MyCompressor) from server
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }

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

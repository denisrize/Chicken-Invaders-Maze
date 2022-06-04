package Server;

import algorithms.mazeGenerators.*;
import algorithms.search.*;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.atomic.AtomicInteger;

public class ServerStrategySolveSearchProblem implements ServerStrategy{

    private static AtomicInteger numOfSolution ;
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        InputStream interruptibleInputStream = Channels.newInputStream(Channels.newChannel(inFromClient));
        try {
            ObjectInputStream fromClient = new ObjectInputStream(interruptibleInputStream);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ISearchingAlgorithm SearchAlgorithm = Configurations.getInstance().getSearchAlgorithm();

            Maze maze = (Maze)fromClient.readObject();
            Solution sol = findIfSolutionExist(maze);
            if(sol == null){

                SearchableMaze searchableMaze = new SearchableMaze(maze);
                sol = SearchAlgorithm.solve(searchableMaze);
                writeMazeSolutionToFile(maze,sol);
            }

            toClient.writeObject(sol);
            toClient.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private Solution findIfSolutionExist(Maze maze) {
        String tmpdir = System.getProperty("java.io.tmpdir");
        File folder = new File(tmpdir);
        File[] listOfTempFiles = folder.listFiles();

        Maze existMaze;
        for (int i = 0; i < listOfTempFiles.length; i++) {
            try {
                FileInputStream fis = new FileInputStream(listOfTempFiles[i]);
                ObjectInputStream ois = new ObjectInputStream(fis);
                existMaze = (Maze) ois.readObject();

                if (maze.equals(existMaze)) {

                    String fileName = listOfTempFiles[i].getName().substring(0,1) + "Solution.txt";
                    FileInputStream SolutionFile = new FileInputStream(fileName);
                    ObjectInputStream oiss = new ObjectInputStream(SolutionFile);

                    return (Solution) oiss.readObject();
                }

            } catch (FileNotFoundException e) {
                continue;
            } catch (IOException e) {
                continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    private void writeMazeSolutionToFile(Maze maze, Solution sol ){
        if(numOfSolution == null) numOfSolution = new AtomicInteger();
        int numSol = numOfSolution.incrementAndGet();
        String name = String.format("%dSolution.txt", numSol);
        try {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(sol);
            oos.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Path tempFile = Files.createTempFile(String.format("%dMaze",numSol), ".tmp");
            tempFile.toFile().deleteOnExit();
            FileOutputStream fos = new FileOutputStream(String.valueOf(tempFile));
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(maze);
            oos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }
}

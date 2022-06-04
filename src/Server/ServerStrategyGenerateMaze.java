package Server;

import IO.MyCompressorOutputStream;
import algorithms.mazeGenerators.*;

import java.io.*;
import java.nio.channels.Channels;

public class ServerStrategyGenerateMaze implements ServerStrategy{
    @Override
    public void applyStrategy(InputStream inFromClient, OutputStream outToClient) {
        IMazeGenerator generator = Configurations.getInstance().getGeneratorAlgorithm();
        InputStream interruptibleInputStream = Channels.newInputStream(Channels.newChannel(inFromClient));

        try {
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            ObjectInputStream fromClient = new ObjectInputStream(interruptibleInputStream);
            OutputStream compressor = new MyCompressorOutputStream(toClient);

            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze = generator.generate(mazeSize[0],mazeSize[1]);
            compressor.write(maze.toByteArray());
            toClient.flush();


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
}

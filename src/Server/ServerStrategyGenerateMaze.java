package Server;

import IO.MyCompressorOutputStream;
import IO.MyDecompressorInputStream;
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

            int[] mazeSize = (int[]) fromClient.readObject();
            Maze maze = generator.generate(mazeSize[0],mazeSize[1]);
            byte[] compressMaze = getCompressMaze(maze);
            toClient.writeObject(compressMaze);
            toClient.flush();


        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
    private byte[] getCompressMaze(Maze maze){
        String tempFileName = "mazeHolder.txt";
        try {
            OutputStream compressor = new MyCompressorOutputStream(new FileOutputStream(tempFileName));
            compressor.write(maze.toByteArray());
            compressor.flush();

            InputStream in = new FileInputStream(tempFileName);
            byte[] data = in.readAllBytes();
            File tempFile = new File(tempFileName);
            tempFile.delete();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}

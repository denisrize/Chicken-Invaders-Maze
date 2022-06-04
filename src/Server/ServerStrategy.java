package Server;

import java.io.InputStream;
import java.io.OutputStream;

public interface ServerStrategy {
    void applyStrategy(InputStream inFromClient, OutputStream outToClient);
}

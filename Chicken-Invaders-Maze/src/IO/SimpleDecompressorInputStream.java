package IO;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

public class SimpleDecompressorInputStream extends InputStream {

    InputStream in;

    SimpleDecompressorInputStream(InputStream i)
    {
        in = i;
    }

    @Override
    public int read() throws IOException {

        byte[] data = null;
        int index = 0;
        int startX = 0;
        int startY = 0;
        int endX = 0;
        int endY = 0;
        int row = 0;
        int col = 0;


        // Reads all the data
        data = in.readAllBytes();


        // Row data
        for (int i=0; i<4; i++)
        {
            row += data[i];
        }

        // Col data
        for (int i=4; i<8; i++)
        {
            col += data[i];
        }

        // Start Position X
        for (int i=0; i<4; i++)
        {
            startX += data[data.length-16+i];
        }

        // Start Position Y
        for (int i=0; i<4; i++)
        {
            startX += data[data.length-12+i];
        }

        // End Position X
        for (int i=0; i<4; i++)
        {
            endX += data[data.length-8+i];
        }

        // End Position Y
        for (int i=0; i<4; i++)
        {
            endY += data[data.length-4+i];
        }


        return 0;
    }

}

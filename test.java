import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

class test {

public static void main(String args[]) throws IOException  {
          
        //Read hexadecimal data from RAM
        DataInputStream input = new DataInputStream(new FileInputStream(
                "RAM.dat"));
        while (input.available() > 0) {
            String data = Integer.toHexString(Integer.parseInt(String.valueOf(input.read())));
            // System.out.println(data);
        }
        input.close();
        
        //Test case ram

        int S1 = Integer.parseInt(args[1]);
		int E1 = Integer.parseInt(args[3]);
		int b1 = Integer.parseInt(args[5]);

		int S2 = Integer.parseInt(args[7]);
		int E2 = Integer.parseInt(args[9]);
		int b2 = Integer.parseInt(args[11]);
        

		Cache L1I = new Cache(S1, E1, b1);
		Cache L1D = new Cache(S1, E1, b1);
		Cache L2 = new Cache(S2, E2, b2);

        

        // Trace file
        String trace = args[13];
        String trace1= "L 001da310, 6"; // Example trace

        

      

    }
}
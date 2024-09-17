
/**
 * Hello world!
 *
 */
import java.io.*;
 import java.net.*;
import java.util.List;
import java.util.StringTokenizer;
import org.jutils.jprocesses.JProcesses;
import org.jutils.jprocesses.model.ProcessInfo;


public class RClient 
{
    public static void main(String[] args) throws Exception {
        String command = args[0];
       
			// I/O operations
            try (Socket connectionToServer = new Socket("localhost", 80)) {

                
    
                InputStream in = connectionToServer.getInputStream();
                OutputStream out = connectionToServer.getOutputStream();
    
                BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
                BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));
                DataInputStream dataIn = new DataInputStream(in);
    
                if (command.equals("R")) {
                    String header = "Reboot " + fileName + "\n";
                    headerWriter.write(header, 0, header.length());
                    headerWriter.flush();
                  				
            }else if (command.equals("L")) {
				String header = "GetProcess"+"\n";
				headerWriter.write(header, 0, header.length());
				headerWriter.flush();

				System.out.println(headerReader.readLine()+"\n");
				
        }else if(command.equals("S")){
            String header = "Capture " + fileName + "\n";
            headerWriter.write(header, 0, header.length());
            headerWriter.flush();

            header = headerReader.readLine();

            if (header.equals("NOT FOUND")) {
                System.out.println("We're extremely sorry, the file you specified is not available!");
            } else {
                StringTokenizer strk = new StringTokenizer(header, " ");

                String status = strk.nextToken();

                if (status.equals("OK")) {

                    String temp = strk.nextToken();

                    int size = Integer.parseInt(temp);

                    byte[] space = new byte[size];

                    dataIn.readFully(space);

                    try (FileOutputStream fileOut = new FileOutputStream("ClientShare/" + fileName)) {
                        fileOut.write(space, 0, size);
                    }
                } else {
                    System.out.println("You're not connected to the right Server!");
                }

            }
            }


        }
	 
     
    }
}
           



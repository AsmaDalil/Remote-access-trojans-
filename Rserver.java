import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.StringTokenizer;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.InputMap;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
public class Rserver {

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {

		try (ServerSocket ss = new ServerSocket(8080)) {
			while (true) {
				System.out.println("Server waiting...");
				Socket connectionFromClient = ss.accept();
				System.out.println(
						"Server got a connection from a client whose port is: " + connectionFromClient.getPort());
				



                            
				try {
					InputStream in = connectionFromClient.getInputStream();
					OutputStream out = connectionFromClient.getOutputStream();

					String errorMessage = "NOT FOUND\n";

					BufferedReader headerReader = new BufferedReader(new InputStreamReader(in));
					BufferedWriter headerWriter = new BufferedWriter(new OutputStreamWriter(out));

					DataInputStream dataIn = new DataInputStream(in);
					DataOutputStream dataOut = new DataOutputStream(out);

					String header = headerReader.readLine();
					StringTokenizer strk = new StringTokenizer(header, " ");

					String command = strk.nextToken();
                    //String fileName = strk.nextToken();



					if (command.equals("Reboot")) {
						Runtime runtime = Runtime.getRuntime();
                        try
                        {
                            runtime.exec("shutdown -r -t 5");
                             System.out.println("Restarting the Computer after 5 seconds.");
                        }
                        catch(IOException e)
                        {
                            System.out.println("Exception: " +e);
                        }
                        catch (Exception ex) {
                            headerWriter.write(errorMessage, 0, errorMessage.length());
                            headerWriter.flush();

                        } finally {
                        connectionFromClient.close();
                        } 
                    }else if (command.equals("Capture")) {
						try {
                            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                            BufferedImage capture = new Robot().createScreenCapture(screenRect);
                            ImageIO.write(capture, "png", new File(capture.png));
                            FileInputStream fileIn = new FileInputStream("Capture.png");
							int fileSize = fileIn.available();
							header = "OK " + fileSize + "\n";

							headerWriter.write(header, 0, header.length());
							headerWriter.flush();

							byte[] bytes = new byte[fileSize];
							fileIn.read(bytes);

							fileIn.close();

							dataOut.write(bytes, 0, fileSize);
                        } catch (Exception ex) {
							headerWriter.write(errorMessage, 0, errorMessage.length());
							headerWriter.flush();


						} finally {
							connectionFromClient.close();
						}		
					}else if(command.equals("List")){
						try {
                            
								String line;
                                Process p = Runtime.getRuntime().exec
                                (System.getenv("windir") +"\\system32\\"+"tasklist.exe");
                                while ((line = input.readLine()) != null) {
                                     System.out.println(line); 
                                    }
                                    input.close();
                                }  catch (Exception ex) {
                                    headerWriter.write(errorMessage, 0, errorMessage.length());
                                    headerWriter.flush();
								} finally {
							connectionFromClient.close();
						        }
                
                    }else {

						System.out.println("Connection got from an incompatible client");

					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
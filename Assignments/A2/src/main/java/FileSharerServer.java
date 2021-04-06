import java.io.*;
import java.net.*;
import java.util.*;

public class FileSharerServer {
    protected Socket clientSocket           = null;
	protected ServerSocket serverSocket     = null;
	protected ClientConnectionHandler[] handler    = null;
	protected int numClients                = 0;
    protected Vector<File> files = new Vector<>();
    public static int SERVER_PORT = 16789;
	public static int MAX_CLIENTS = 100;

    public FileSharerServer() {

        try {
			//Connect the server
			serverSocket = new ServerSocket(SERVER_PORT);
			System.out.println("---------------------------");
			System.out.println("Chat Server Application is running");
			System.out.println("---------------------------");
			System.out.println("Listening to port: "+SERVER_PORT);
			handler = new ClientConnectionHandler[MAX_CLIENTS];

			//Set the path of the server storage
			String pathToServerStorage = System.getProperty("user.dir") + "/../../../Storage/";
			

			while(true) {

				// Fill a vector with the files in the storage folder
				File serverStorage = new File(pathToServerStorage);
				files = new Vector<>();
				if (serverStorage.isDirectory()) {
					for (File file: serverStorage.listFiles()) {
						files.add(file);
					}
				}

				// Start a new client thread
				clientSocket = serverSocket.accept();
				System.out.println("Client #"+(numClients+1)+" connected.");
				handler[numClients] = new ClientConnectionHandler(clientSocket, files);
				handler[numClients].start();
				numClients++;

			}
		} catch (IOException e) {
			System.err.println("IOException while creating server connection");
		}
    }

    public static void main(String[] args) {
        FileSharerServer fss = new FileSharerServer();
    }
}

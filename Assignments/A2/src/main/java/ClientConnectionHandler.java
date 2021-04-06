import java.io.*;
import java.lang.reflect.Field;
import java.net.*;
import java.util.*;

public class ClientConnectionHandler extends Thread{

    protected Socket socket       = null;
	protected PrintWriter out     = null;
	protected BufferedReader in   = null;
    protected Vector<File> files  = null;
    protected String clientPath = null;
    
    /**
     * This is the constructor fort he clientConnectionHandler
     * @param socket : The client socket
     * @param files : the files stored in the server
     */
    public ClientConnectionHandler(Socket socket, Vector<File> files) {
        // We set up the handler
		super();
		this.socket = socket;
		this.files = files;
		try {
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("IOEXception while opening a read/write connection");
		}
	}

    public void run() {

        boolean endOfSession = false;
        while (!endOfSession) {
            endOfSession = processCommand();
        }
        try {
            socket.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This processes the commands issued by the client
     * @return whether or not to continue 
     */
    protected boolean processCommand() {
        String message = null;
        try {
            message = in.readLine();
        } catch(IOException e) {
            System.err.println("Error reading comand from socket.");
            return true;
        }
        if (message == null) {
            return true;
        }
        StringTokenizer st = new StringTokenizer(message);
        String command = st.nextToken();
        String args = null;
        if (st.hasMoreTokens()) {
            args = message.substring(command.length()+1, message.length());
        }
        return processCommand(command, args);
    }

    /**
     * This processes specific commands and arguments
     * @param command : the name of the command
     * @param arguments : the possible arguments following the command
     * @return
     */
    protected boolean processCommand(String command, String arguments) {
        // Stores the client storage path
        if (command.equalsIgnoreCase("PATH")) {

            this.clientPath = arguments;
            return false;

        // returns the files in the server storage
        } else if (command.equalsIgnoreCase("DIR")) {

            out.println(String.valueOf(files.size()));

            for (int i = 0; i < files.size(); i++) {
                out.println(files.get(i).getName());
            }

            return true;

        // upload a file from the client to the server
        } else if (command.equalsIgnoreCase("UPLOAD")) {

            try {

                File fileToCopy = new File(this.clientPath + arguments);
                File copiedFile = new File(System.getProperty("user.dir") + "/../../../Storage/" + arguments);
                FileWriter fw = new FileWriter(copiedFile);
                Scanner scanner = new Scanner(fileToCopy);
                String toWrite = "";

                while (scanner.hasNextLine()) {
                    toWrite += scanner.nextLine() + "\n";
                }

                fw.write(toWrite);
                fw.close();
                files.add(copiedFile);
                scanner.close();

                out.println("200 Uploading: " + fileToCopy.getName());
            
            } catch (IOException e) {
                e.printStackTrace();
            } 

            return true;
        
        // download a file from the server to the client
        } else if (command.equalsIgnoreCase("DOWNLOAD")) {

            try {

                Iterator<File> iter = files.iterator();
                File next = null;
                while(iter.hasNext()) {
                    next = iter.next();
                    if (next.getName().equals(arguments)) {
                        break;
                    }
                }
                if (null == next) {
                    out.println("400 File does not exist");
                    return true;
                }

                FileWriter fw = new FileWriter(new File(clientPath + arguments));
                Scanner scanner = new Scanner(next);
                String toWrite = "";

                while (scanner.hasNextLine()) {
                    toWrite += scanner.nextLine() + "\n";
                }

                fw.write(toWrite);
                fw.close();
                scanner.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

            out.println("200 Downloading: " + arguments);

            return true;

        } else {
            out.println("400 Unrecognized Command: " + command);
            return false;
        }
    }

}

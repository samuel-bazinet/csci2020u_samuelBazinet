import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.collections.*;

public class Lab10Server extends Application{

    private static TextArea ta = new TextArea();
    protected  Socket clientSocket           = null;
	protected  ServerSocket serverSocket     = null;
	protected  Handler[] handler    = null;
	protected  int numClients                = 0;
    public static int SERVER_PORT = 16789;
	public static int MAX_CLIENTS = 100;
    public static boolean running = true;

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("Lab 10");


        Button btnExit = new Button ("Exit");

        ta.setPrefColumnCount(50);
        ta.setPrefRowCount(10);
        

        VBox vb = new VBox(ta, btnExit);

        

        Thread task = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //Connect the server
                    serverSocket = new ServerSocket(SERVER_PORT);
                    System.out.println("---------------------------");
                    System.out.println("Bulletin Board Server Application is running");
                    System.out.println("---------------------------");
                    System.out.println("Listening to port: "+SERVER_PORT);
                    handler = new Handler[MAX_CLIENTS];
                    
                    while(running) {
                        if (!running) {
                            try {
                                serverSocket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            return;
                        }
                        // Start a new client thread
                            
                        clientSocket = serverSocket.accept();
                        System.out.println("Client #"+(numClients+1)+" connected.");
                        handler[numClients] = new Handler(clientSocket);
                        handler[numClients].start();
                        numClients++;
                            
                    }

                    
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        btnExit.setOnAction( new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                running = false;
                try {
                    serverSocket.close();
                } catch (IOException er) {
                    er.printStackTrace();
                }
                primaryStage.close();
            }
        });

        Scene scene = new Scene(vb, 400, 250);
        primaryStage.setScene(scene);
        primaryStage.show();

        task.start();
    }
    
    public static void main(String[] args) {
        launch(args);
    }

    class Handler extends Thread{

        protected Socket socket       = null;
        protected PrintWriter out     = null;
        protected BufferedReader in   = null;

        public Handler(Socket socket) {
            super();
            this.socket = socket;
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

        private boolean processCommand(String command, String args) {
            if (command.equalsIgnoreCase("POST")) {
                System.err.println("Reading");
                ta.setText(ta.getText() + "\n\n" + args);
                return true;
            } else {
                out.println("400 Unrecognized Command: " + command);
                return true;
            }
        }

    }
}

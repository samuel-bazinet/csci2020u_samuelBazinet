import java.io.*;
import java.net.*;
import java.util.*;

import javax.swing.Action;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.collections.*;

public class FileSharerClient extends Application {
    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;

    private static String computerName = null;
    private static String path = null;

    public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 16789;
    ObservableList<String> listServer = null;
    ObservableList<String> listClient = null;
    ListView<String> uiListServer = new ListView<>();
    ListView<String> uiListClient = new ListView<>();


    public void start(Stage primaryStage) {

        primaryStage.setTitle("Assignment 2");

        listServer = getDir();

        //find the files in the local storage
        File clientDir = new File(path);
        Vector<File> clientDirContent = new Vector<>();
        if (clientDir.isDirectory()) {
            for (File file: clientDir.listFiles()) {
                clientDirContent.add(file);
            }
        }

        // Put the name of the files in an observable list so that we can see them
        listClient = FXCollections.observableArrayList();
        for (int i = 0; i < clientDirContent.size(); i++) {
            listClient.add(clientDirContent.elementAt(i).getName());
        }

        BorderPane bp = new BorderPane();
        bp.setLeft(uiListClient);
        bp.setRight(uiListServer);

        GridPane gp = new GridPane();

        // make buttons for the actions
        Button butUpload = new Button("Upload");
        Button butDownload = new Button("Download");
        Button butRefresh = new Button("Refresh");
        Button butUpContFol = new Button("Up. Cont. Fol.");

        butUpload.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                startSocket();

                networkOut.println("PATH " + path);
                networkOut.println("UPLOAD " + uiListClient.getSelectionModel().getSelectedItem());
                
                try {
                    System.out.println(networkIn.readLine());
                    socket.close();
                } catch (IOException er) {
                    er.printStackTrace();
                }
                // Try tp refresh the list
                boolean present = false;
                Iterator<String> iter = listServer.iterator();
                while(iter.hasNext()) {
                    if (uiListClient.getSelectionModel().getSelectedItem().equals(iter.next())) {
                        present = true;
                    }
                }

                if (!present) {
                    listServer.add(uiListClient.getSelectionModel().getSelectedItem());
                }
            }   
        });

        butDownload.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                startSocket();

                networkOut.println("PATH " + path);
                networkOut.println("DOWNLOAD " + uiListServer.getSelectionModel().getSelectedItem());
                
                try {
                    System.out.println(networkIn.readLine());
                    socket.close();
                } catch (IOException er) {
                    er.printStackTrace();
                }
                // Try tp refresh the list
                boolean present = false;
                Iterator<String> iter = listClient.iterator();
                while(iter.hasNext()) {
                    if (uiListServer.getSelectionModel().getSelectedItem().equals(iter.next())) {
                        present = true;
                    }
                }
                if (!present) {
                    listClient.add(uiListServer.getSelectionModel().getSelectedItem());
                }
            }
        });

        butRefresh.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                // Try tp refresh the list
                listServer = FXCollections.observableArrayList();
                listServer = getDir();
                File clientDir = new File(path);
                Vector<File> clientDirContent = new Vector<>();
                if (clientDir.isDirectory()) {
                    for (File file: clientDir.listFiles()) {
                        clientDirContent.add(file);
                    }
                }
                
                listClient = FXCollections.observableArrayList();
                for (int i = 0; i < clientDirContent.size(); i++) {
                    listClient.add(clientDirContent.elementAt(i).getName());
                }
            }
        });

        butUpContFol.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                File folder = new File(path + uiListClient.getSelectionModel().getSelectedItem());
                if (folder.isDirectory()) {
                    // upload all the files inside a folder
                    for (File file: folder.listFiles()) {
                        startSocket();

                        networkOut.println("PATH " + path + folder.getName());
                        networkOut.println("UPLOAD " + file.getName());
                        
                        try {
                            System.out.println(networkIn.readLine());
                            socket.close();
                        } catch (IOException er) {
                            er.printStackTrace();
                        }
                        boolean present = false;
                        Iterator<String> iter = listServer.iterator();
                        while(iter.hasNext()) {
                            if (file.getName().equals(iter.next())) {
                                present = true;
                            }
                        }
                        if (!present) {
                            listServer.add(file.getName());
                        }
                    }
                }
            }
        });

        uiListServer.setItems(listServer);
        uiListClient.setItems(listClient);

        gp.add(butUpload, 1, 0);
        gp.add(butDownload, 0, 0);
        gp.add(butRefresh, 2, 0);
        gp.add(butUpContFol, 3, 0);
        bp.setTop(gp);

        Scene scene = new Scene(bp, 500, 900);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Starts a server socket so that we can access it
     */
    public void startSocket() {
        try {
			socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
		} catch (UnknownHostException e) {
			System.err.println("Unknown host: "+SERVER_ADDRESS);
		} catch (IOException e) {
			System.err.println("IOEXception while connecting to server: "+SERVER_ADDRESS);
		}
		if (socket == null) {
			System.err.println("socket is null");
		}
		try {
			networkOut = new PrintWriter(socket.getOutputStream(), true);
			networkIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		} catch (IOException e) {
			System.err.println("IOEXception while opening a read/write connection");
		}
		
		in = new BufferedReader(new InputStreamReader(System.in));
    }

    /**
     * This gets the name of the files in the server storage
     * @return a list containing the name of the files stored in the folder
     */
    protected ObservableList<String> getDir() {

        String message = null;
        ObservableList<String> list = FXCollections.observableArrayList();
        
        startSocket();

        networkOut.println("PATH " + path);
        
        networkOut.println("DIR");

        try {
            message = networkIn.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        } 

        int id = Integer.parseInt(message.trim());

        for (int i = 0; i < id; i++) {
            try {
                message = networkIn.readLine();
                list.add(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;

    }

    public static void main(String[] args) {
        // We set the args before running the rest of the program
        if (args.length >= 2) {
            computerName = args[0];
            path = args[1];
            launch(args);

        } else {
            System.out.println("Not enough arguments");
        }
    }
}

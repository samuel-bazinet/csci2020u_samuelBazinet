import java.io.*;
import java.net.*;
import java.util.*;
import javafx.application.Application;
import javafx.event.*;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.collections.*;

public class Lab10Client extends Application {

    private Socket socket = null;
    private BufferedReader in = null;
    private PrintWriter networkOut = null;
    private BufferedReader networkIn = null;
    public static String SERVER_ADDRESS = "localhost";
    public static int SERVER_PORT = 16789;

    public void start(Stage primaryStage) {

        primaryStage.setTitle("Lab 10 Client");

        Button btnSend = new Button("Send");
        Button btnExit = new Button("Exit");
        TextField tfUser = new TextField();
        tfUser.setPromptText("Username");
        TextField tfMessage = new TextField();
        tfMessage.setPromptText("Message");
        Label lbUser = new Label("Username:");
        Label lbMessage = new Label("Message:");

        btnSend.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent e) {
                startSocket();

                networkOut.println("POST " + tfUser.getText() + ": " + tfMessage.getText());

                try {
                    socket.close();
                } catch (IOException er) {
                    er.printStackTrace();
                }
            }
        });

        btnExit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                primaryStage.close();
            }
        });


        GridPane gp = new GridPane();
        gp.setHgap(10);
        gp.setVgap(20);
        gp.setPadding(new Insets(10, 10, 10, 10));

        gp.add(lbUser, 0, 0);
        gp.add(tfUser, 1, 0);
        gp.add(lbMessage, 0, 1);
        gp.add(tfMessage, 1, 1);
        gp.add(btnSend, 0, 2);
        gp.add(btnExit, 0, 3);

        Scene scene = new Scene(gp, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

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

    public static void main(String[] args) {
        launch(args);
    }
}   

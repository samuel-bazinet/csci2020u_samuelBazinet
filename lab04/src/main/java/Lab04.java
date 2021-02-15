import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class Lab04 extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Lab04");

        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));
        
        
        Button btnReg = new Button("Register");

        Label labUser = new Label("Username:");
        Label labPass = new Label("Password:");
        Label labFN = new Label("Full Name:");
        Label labEM = new Label("E-Mail:");
        Label labPhone = new Label("Phone #:");
        Label labDOB = new Label("Date of Birth:");

        TextField txtUser = new TextField();
        PasswordField passPass = new PasswordField();
        TextField txtFN = new TextField();
        TextField txtEM = new TextField();
        TextField txtPhone = new TextField();
        DatePicker dateDOB = new DatePicker(LocalDate.now());

        grid.add(labUser, 0, 0);
        grid.add(labPass, 0, 1);
        grid.add(labFN, 0, 2);
        grid.add(labEM, 0, 3);
        grid.add(labPhone, 0, 4);
        grid.add(labDOB, 0, 5);

        grid.add(txtUser, 1, 0);
        grid.add(passPass, 1, 1);
        grid.add(txtFN, 1, 2);
        grid.add(txtEM, 1, 3);
        grid.add(txtPhone, 1, 4);
        grid.add(dateDOB, 1, 5);
        
        grid.add(btnReg, 1, 6);

        btnReg.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("UserName: " + txtUser.getText());
                System.out.println("PassWord: " + passPass.getText());
                System.out.println("Full Name: " + txtFN.getText());
                System.out.println("E-Mail: " + txtEM.getText());
                System.out.println("Phone #: " + txtPhone.getText());
                System.out.println("Date of Birth: " + dateDOB.getValue().format(DateTimeFormatter.ofPattern("MM-dd-yyyy")));
            }
        });



        primaryStage.setScene(new Scene(grid, 400, 300));

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }
}
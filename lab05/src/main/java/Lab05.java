import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.collections.*;

public class Lab05 extends Application {

    private TableView<StudentRecord> table = new TableView<StudentRecord>();
    private static ObservableList<StudentRecord> marks = DataSource.getAllMarks();

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Lab04");

        BorderPane border = new BorderPane();
        GridPane grid = new GridPane();

        grid.setAlignment(Pos.CENTER);
        grid.setHgap(20);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        table.setEditable(true);

        TableColumn<StudentRecord,String> studentIDCol = new TableColumn<>("Student ID");
        studentIDCol.setMinWidth(100);
        studentIDCol.setCellValueFactory(
                new PropertyValueFactory<>("studentID"));

        TableColumn<StudentRecord,Float> assignmentsCol = new TableColumn<>("Assignments");
        assignmentsCol.setMinWidth(100);
        assignmentsCol.setCellValueFactory(
                new PropertyValueFactory<>("assignments"));
        
        TableColumn<StudentRecord,Float> midtermCol = new TableColumn<>("Midterm");
        midtermCol.setMinWidth(100);
        midtermCol.setCellValueFactory(
                new PropertyValueFactory<>("midterm"));

        TableColumn<StudentRecord,Float> finalExamCol = new TableColumn<>("Final Exam");
        finalExamCol.setMinWidth(100);
        finalExamCol.setCellValueFactory(
                new PropertyValueFactory<>("finalExam"));

        TableColumn<StudentRecord,Float> finalMarkCol = new TableColumn<>("Final Mark");
        finalMarkCol.setMinWidth(100);
        finalMarkCol.setCellValueFactory(
                new PropertyValueFactory<>("finalMark"));

        TableColumn<StudentRecord,String> letterGradeCol = new TableColumn<>("Letter Grade");
        letterGradeCol.setMinWidth(100);
        letterGradeCol.setCellValueFactory(
                new PropertyValueFactory<>("letterGrade"));

        table.setItems(marks);

        table.getColumns().addAll(studentIDCol, assignmentsCol, midtermCol, finalExamCol, finalMarkCol, letterGradeCol);

        border.setTop(table);

        Label labSID = new Label("SID:");
        Label labAssig = new Label("Assingment:");
        Label labMid = new Label("Midterm:");
        Label labFE = new Label("Final Exam:");

        TextField textSID = new TextField();
        TextField textAssig = new TextField();
        TextField textMid = new TextField();
        TextField textFE = new TextField();

        textSID.setPromptText("SID");
        textAssig.setPromptText("Assignment");
        textMid.setPromptText("Midterm");
        textFE.setPromptText("Final Exam");

        Button btnAdd = new Button("Add");

        btnAdd.setOnAction(new EventHandler<ActionEvent>(){
                @Override
                public void handle(ActionEvent event) {
                        String newSID = textSID.getText();
                        float newAssig = Float.parseFloat(textAssig.getText());
                        float newMid = Float.parseFloat(textMid.getText());
                        float newFE = Float.parseFloat(textFE.getText());

                        marks.add(new StudentRecord(newSID, newAssig, newMid, newFE));
                }
        });
        
        grid.add(labSID, 0, 0);
        grid.add(labAssig, 2, 0);
        grid.add(labMid, 0, 1);
        grid.add(labFE, 2, 1);

        grid.add(textSID, 1, 0);
        grid.add(textAssig, 3, 0);
        grid.add(textMid, 1, 1);
        grid.add(textFE, 3, 1);
        grid.add(btnAdd, 1, 2);

        border.setCenter(grid);

        primaryStage.setScene(new Scene(border, 700, 600));

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
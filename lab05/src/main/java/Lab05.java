import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.collections.*;

public class Lab05 extends Application {

    private TableView<StudentRecord> table = new TableView<StudentRecord>();
    private static ObservableList<StudentRecord> marks = DataSource.getAllMarks();

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        primaryStage.setTitle("Lab04");

        BorderPane border = new BorderPane();

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

        primaryStage.setScene(new Scene(border, 700, 300));

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.FileChooser;
import javafx.collections.*;
import java.io.*;
import java.util.*;


public class Lab08 extends Application {

    private TableView<StudentRecord> table = new TableView<StudentRecord>();
    private DataStorage ds = new DataStorage();

    private void save() {
        File file = new File(ds.getCurrentFilename());
        
        try {
                FileWriter fw = new FileWriter(file);
                for (int i = 0 ; i < ds.getMarks().size(); i++) {
                        StudentRecord sr = ds.getMarks().get(i);
                        fw.write(sr.getStudentID() + "," + sr.getAssignments() + "," + sr.getMidterm() + "," + sr.getFinalExam());
                }
                fw.close();
                System.out.println("File saved successfully.");
                
        } catch (IOException e) {
        e.printStackTrace();
        }
    }

    private void load(String path) {
        ds = new DataStorage(path);
    }
        
    @Override
    public void start(Stage primaryStage) throws Exception {

        
        primaryStage.setTitle("Lab08");

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

        table.setItems(ds.getMarks());

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

                        ds.addStudentRecord(new StudentRecord(newSID, newAssig, newMid, newFE));
                }
        });

        MenuBar mb = new MenuBar();

        Menu menuFile = new Menu("File");

        MenuItem newFile = new MenuItem("New");
        newFile.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                        ds = new DataStorage();
                        table.setItems(ds.getMarks());
                }
        });

        MenuItem open = new MenuItem("Open");
        open.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                        FileChooser fc = new FileChooser();
                        fc.setTitle("Open");
                        fc.getExtensionFilters().addAll(new ExtensionFilter("Comma Separated Value", "*.csv"));
                        load(fc.showOpenDialog(primaryStage).getName());
                        table.setItems(ds.getMarks());
                }
        });

        MenuItem save = new MenuItem("Save");
        save.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                        save();
                }
        });

        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                        FileChooser fc = new FileChooser();
                        fc.setTitle("Save As");
                        fc.getExtensionFilters().addAll(new ExtensionFilter("Comma Separated Value", "*.csv"));
                        ds.setFilename(fc.showSaveDialog(primaryStage).getName());
                        save();
                }
        });

        MenuItem exit = new MenuItem("Exit");
        exit.setOnAction(new EventHandler<ActionEvent>(){
                public void handle(ActionEvent e) {
                        primaryStage.close();
                }
        });

        menuFile.getItems().addAll(newFile, open, save, saveAs, exit);
        mb.getMenus().add(menuFile);
        
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
        BorderPane topBorder = new BorderPane();
        topBorder.setTop(mb);
        topBorder.setCenter(border);
        Scene scene = new Scene(topBorder, 700, 600);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }

}
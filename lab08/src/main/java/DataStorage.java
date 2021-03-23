import javafx.collections.*;
import java.io.*;
import java.util.*;

public class DataStorage {

    private String currentFilename;
    private ObservableList<StudentRecord> marks;

    DataStorage() {
        this.currentFilename = "default.csv";
        this.marks = FXCollections.observableArrayList();
    }
    
    DataStorage(String fileLocation) {
        String row;
        File file = new File(fileLocation);

        this.marks = FXCollections.observableArrayList();

        this.currentFilename = file.getName();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            while(null != (row = br.readLine())) {
                String cells[] = row.split(",");
                this.marks.add(new StudentRecord(cells[0], Float.parseFloat(cells[1]), Float.parseFloat(cells[2]), Float.parseFloat(cells[3])));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }

    public void setFilename(String newName) {this.currentFilename = newName;}
    public void addStudentRecord(StudentRecord newStudentRecord) {
        this.marks.add(newStudentRecord);
    }

    public String getCurrentFilename() {return this.currentFilename;}
    public ObservableList<StudentRecord> getMarks() {return this.marks; }
}

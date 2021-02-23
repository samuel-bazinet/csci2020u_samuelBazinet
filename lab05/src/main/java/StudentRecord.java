import javafx.beans.property.*;

public class StudentRecord {
    private SimpleStringProperty studentID;
    private SimpleFloatProperty assignments;
    private SimpleFloatProperty midterm;
    private SimpleFloatProperty finalExam;
    private SimpleFloatProperty finalMark;
    private SimpleStringProperty letterGrade;

    public StudentRecord(String studentID, float assignments,
                         float midterm, float finalExam) {
        
        this.studentID = new SimpleStringProperty(this, "studentID", studentID);
        this.assignments = new SimpleFloatProperty(this, "assignments", assignments);
        this.midterm = new SimpleFloatProperty(this, "midterm", midterm);
        this.finalExam = new SimpleFloatProperty(this, "finalExam", finalExam);
        this.finalMark = new SimpleFloatProperty(this, "finalMark", assignments*0.2f + midterm*0.3f + finalExam * 0.5f);

        if (finalMark.get() < 50.0f) {
            this.letterGrade = new SimpleStringProperty(this, "letterGrade","F");
        } else if (finalMark.get() < 60.0f) {
            this.letterGrade = new SimpleStringProperty(this, "letterGrade","D");
        } else if (finalMark.get() < 70.0f) {
            this.letterGrade = new SimpleStringProperty(this, "letterGrade","C"); 
        } else if (finalMark.get() < 80.0f) {
            this.letterGrade = new SimpleStringProperty(this, "letterGrade","B");
        } else {
            this.letterGrade = new SimpleStringProperty(this, "letterGrade","A");
        }

    }

    public String getStudentID() {return this.studentID.get();}
    public float getMidterm() {return this.midterm.get();}
    public float getAssignments() {return this.assignments.get();}
    public float getFinalExam() {return this.finalExam.get();}
    public float getFinalMark() {return this.finalMark.get();}
    public String getLetterGrade() {return this.letterGrade.get();}

}

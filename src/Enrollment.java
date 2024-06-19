import java.util.ArrayList;
public class Enrollment {
	int id;
	Student student;
	ArrayList<Assessment>assessments=new ArrayList<>();
	public int getId() {return id;}
	public ArrayList<Assessment>getAssessment(){return assessments;}
	public void setId(int newId) {this.id=newId;}
	public void setStudent(Student newStudent) {this.student=newStudent;}
	public void addAssessment(Assessment assessment) {this.assessments.add(assessment);}}
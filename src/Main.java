import java.io.*;
import java.util.*;
public class Main {
	public static String read(String file) {
		String output="";
		try {
			File text=new File(file);
			Scanner reader=new Scanner(text);
			while (reader.hasNextLine()) {
				String data=reader.nextLine();
				output+=data+"\n";}
			reader.close();
		} catch (FileNotFoundException error1) {
			System.out.println("File" + file + "not found");
			error1.printStackTrace();}
		return output;}
	public static void write(String file,String input) {
		try {
			FileWriter writer=new FileWriter(file);
			writer.write(input);
			writer.close();
		} catch (IOException error2) {
			System.out.println("An error occured.");
			error2.printStackTrace();}}
	public static boolean isNumeric(String input) {
	    if (input == null) {return false;}
	    try {int number = Integer.parseInt(input);} 
	    catch (NumberFormatException error3) {return false;}
	    return true;}
	public static void trackStudents(ArrayList<Student>students) {
		String output="";
		Collections.sort(students,new NumberSort());
		for (int i=0;i<students.size();i++) {
			output+=students.get(i)+"\n";}
		write("student.txt",output);}
	public static ArrayList<Student>generateStudents(){
		ArrayList<Student>students=new ArrayList<>();
		String input=read("student.txt");
		String[]inputLine=input.split("\n");
		for(int i=0;i<inputLine.length;i++) {
			String[]studentArray=inputLine[i].split("\t");
			int id=Integer.parseInt(studentArray[0]);
			String[]nameSurname=studentArray[1].split(" ");
			String name=nameSurname[0];
			String surname=nameSurname[1];
			String phoneNumber=studentArray[2];
			String address=studentArray[3];
			Student student=new Student(id,name,surname,phoneNumber,address);
			students.add(student);}
		return students;}
	public static ArrayList<Enrollment>generateEnrollments(ArrayList<Student>students){
		ArrayList<Enrollment>enrollments=new ArrayList<>();
		Enrollment enrollment=new Enrollment();
		String input=read("courseEnrollment.txt");
		String[]inputLine=input.split("\n");
		for(int i=0;i<inputLine.length;i++) {
			String[]assessmentArray=inputLine[i].split("\t");
			if(isNumeric(assessmentArray[0])){
				enrollment=new Enrollment();
				int enrollmentId=Integer.parseInt(assessmentArray[0]);
				int studentId=Integer.parseInt(assessmentArray[1]);
				Student student=getStudent(students,studentId);
				enrollment.setId(enrollmentId);
				enrollment.setStudent(student);}
			else {
				String type=assessmentArray[0];
				String[]tasks=assessmentArray[1].split(" ");
				Assessment assessment=new Assessment();
				if(type.equals("Essaybased")) {assessment=new Essaybased();}
				if(type.equals("MultipleChoice")) {assessment=new MultipleChoice();}
				for(int j=0;j<tasks.length;j++) {
					if(tasks[j].equals("Analysis")) {assessment.addtask(new Analysis());}
					if(tasks[j].equals("QuestionSet")) {assessment.addtask(new QuestionSet());}
					if(tasks[j].equals("LiteratureReview")) {assessment.addtask(new LiteratureReview());}
					if(tasks[j].equals("AdditionalTasks")) {assessment.addtask(new AdditionalTasks());}
				}
				enrollment.addAssessment(assessment);
			}
			if(i+1==inputLine.length) {enrollments.add(enrollment);}
			else {
				if(isNumeric(inputLine[i+1].split("\t")[0])) {enrollments.add(enrollment);}}}
		return enrollments;}
	public static Student getStudent(ArrayList<Student>students,int id) {
		Student student= new Student(0,"","","","");
		for(int i=0;i<students.size();i++) {
			student=students.get(i);
			if(student.getId()==id) {break;}}
			return student;}
	public static Enrollment getEnrollment(ArrayList<Enrollment>enrollments,int id) {
		Enrollment enrollment=new Enrollment();
		for(int i=0;i<enrollments.size();i++) {
			enrollment=enrollments.get(i);
			if(enrollment.getId()==id) {break;}}
		return enrollment;}
	public static ArrayList<Student>addStudent(ArrayList<Student>students,String[]inputWord) {
		int id=Integer.parseInt(inputWord[1]);
		String name=inputWord[2];
		String surname=inputWord[3];
		String phoneNumber=inputWord[4];
		String address="Address: ";
		for(int i=5;i<inputWord.length;i++) {
			address+=inputWord[i];
			if (i!=inputWord.length-1) {address+=" ";}}
		Student student=new Student(id,name,surname,phoneNumber,address);
		students.add(student);
		trackStudents(students);
		return students;}
	public static ArrayList<Student>removeStudent(ArrayList<Student>students,int id){
		Student student=getStudent(students,id);
		students.remove(student);
		trackStudents(students);
		return students;}
	public static String listStudents(ArrayList<Student>students) {
		String output="Student List:\n";
		Collections.sort(students,new NameSort());
		for (int i=0;i<students.size();i++) {
			output+=students.get(i)+"\n";}
		return output;}
	public static ArrayList<Enrollment>createEnrollment(ArrayList<Enrollment>enrollments,ArrayList<Student>students,String[]inputWord){
		Enrollment enrollment=new Enrollment();
		int enrollmentId=Integer.parseInt(inputWord[1]);
		int studentId=Integer.parseInt(inputWord[2]);
		Student student=getStudent(students,studentId);
		enrollment.setId(enrollmentId);
		enrollment.setStudent(student);
		enrollments.add(enrollment);
		return enrollments;
	}
	public static ArrayList<Enrollment>addAssessment(ArrayList<Enrollment>enrollments,String[]inputWord){
		int id=Integer.parseInt(inputWord[1]);
		String type=inputWord[2];
		Assessment assessment=new Assessment();
		if(type.equals("Essaybased")) {assessment=new Essaybased();}
		if(type.equals("MultipleChoice")) {assessment=new MultipleChoice();}
		for (int i=3;i<inputWord.length;i++) {
			if(inputWord[i].equals("Analysis")) {assessment.addtask(new Analysis());}
			if(inputWord[i].equals("QuestionSet")) {assessment.addtask(new QuestionSet());}
			if(inputWord[i].equals("LiteratureReview")) {assessment.addtask(new LiteratureReview());}
			if(inputWord[i].equals("AdditionalTasks")) {assessment.addtask(new AdditionalTasks());}
		}
		Enrollment enrollment=getEnrollment(enrollments,id);
		enrollment.addAssessment(assessment);
		return enrollments;
	}
	public static String totalFee(ArrayList<Enrollment>enrollments,int id){
		String output="";
		int totalFee=0;
		Enrollment enrollment=getEnrollment(enrollments,id);
		ArrayList<Assessment>assessments=enrollment.getAssessment();
		for(int i=0;i<assessments.size();i++) {
			Assessment assessment=assessments.get(i);
			int fee=0;
			output+=assessment.toString()+" ";
			fee+=assessment.cost();
			ArrayList<Task>tasks=assessment.printtasks();
			for(int j=0;j<tasks.size();j++) {
				Task task=tasks.get(j);
				fee+=task.cost();
				output+=task.toString()+" ";}
			output+=fee+"$\n";
			totalFee+=fee;}
		output+="Total: "+totalFee+"$\n";
		return output;}
	public static String processInput(String input) {
		String output="";
		ArrayList<Student>students=generateStudents();
		ArrayList<Enrollment>enrollments=generateEnrollments(students);
		String[]inputLine=input.split("\n");
		for (int i=0;i<inputLine.length;i++) {
			String[]inputWord=inputLine[i].split(" ");
			String command=inputWord[0];
			if (command.equals("AddStudent")) {
				students=addStudent(students,inputWord);
				output+="Student "+inputWord[1]+" "+inputWord[2]+" added\n";}
			if (command.equals("RemoveStudent")) {
				int id=Integer.parseInt(inputWord[1]);
				String name=getStudent(students,id).getName();
				students=removeStudent(students,id);
				output+="Student "+inputWord[1]+" "+name+" removed\n";}
			if (command.equals("ListStudents")) {output+=listStudents(students);}
			if (command.equals("CreateEnrollment")) {
				enrollments=createEnrollment(enrollments,students,inputWord);
				output+="CourseEnrollment "+inputWord[1]+" created\n";}
			if (command.equals("AddAssessment")) {
				enrollments=addAssessment(enrollments,inputWord);
				output+=inputWord[2]+" assessment added to enrollment "+inputWord[1]+"\n";}
			if (command.equals("TotalFee")) {
				int id=Integer.parseInt(inputWord[1]);
				output+="Total fee for enrollment "+id+"\n";
				output+=totalFee(enrollments,id);}}
		return output;}
	public static void execute(String input) {
		String output=processInput(input);
		write("output.txt",output);}
	public static void main(String[] args) {execute(read(args[0]));}}

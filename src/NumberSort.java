import java.util.Comparator;

public class NumberSort implements Comparator<Student>{
	public int compare(Student a, Student b) {return a.id - b.id;}}

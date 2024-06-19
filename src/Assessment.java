import java.util.ArrayList;
public class Assessment {
	ArrayList<Task>tasks=new ArrayList<>();
	public ArrayList<Task>printtasks(){return tasks;}
	public int cost() {return 0;}
	public void addtask(Task task){this.tasks.add(task);}
	public String toString() {return getClass().getName();}}
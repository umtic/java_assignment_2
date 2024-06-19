public class Student {
	int id;
	String name;
	String surname;
	String phoneNumber;
	String address;
	public Student(int newId,String newName,String newSurname,String newPhoneNumber,String newAddress){
		this.id=newId;
		this.name=newName;
		this.surname=newSurname;
		this.phoneNumber=newPhoneNumber;
		this.address=newAddress;}
	public int getId() {return id;}
	public String getName() {return name;}
	public String getSurname() {return surname;}
	public String getPhoneNumber() {return phoneNumber;}
	public String getAddress() {return address;}
	public String toString() {return this.id + "\t" + this.name + " " + this.surname + "\t"  + this.phoneNumber + "\t" + this.address;}}
package notsodefault;

public class Person {
	
    public Gender gender;
    
    public Person(Gender gender) {
        this.gender = gender;
    }
    
    @Override
    public String toString() {
        return gender == Gender.BOY ? "B" : "G";
    }
}
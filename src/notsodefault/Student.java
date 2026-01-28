package notsodefault;

public class Student {

    private String name;
    private int id;
    private int yearOfStudy;
    
    public Student(String name, int id) {
        this.name = name;
        this.id = id;
        this.yearOfStudy = 1;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public void incrementYearOfStudy() {
        yearOfStudy++;
    }

    public int getYearOfStudy() {
        return yearOfStudy;
    }
}

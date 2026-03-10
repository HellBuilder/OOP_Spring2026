package notsodefault;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
    	
    	DragonLaunch dragon1 = new DragonLaunch();
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.printPrisoners();
        System.out.println(dragon1.willDragonEatOrNot());
        
        
        
        
        
        Scanner sc = new Scanner(System.in);

        Course course = new Course("OOP", "Object Oriented Programming", 5);
        GradeBook gb = new GradeBook(course);

        gb.displayMessage();
        
        for (int i = 0; i < 3; i++) {
            System.out.print("Enter student name: ");
            String name = sc.nextLine();

            System.out.print("Enter student id: ");
            int id = sc.nextInt();

            System.out.print("Enter grade: ");
            int grade = sc.nextInt();
            sc.nextLine();

            Student s = new Student(name, id);
            gb.addStudent(s, grade);
        }

        gb.displayGradeReport();

        sc.close();
        
    }
}
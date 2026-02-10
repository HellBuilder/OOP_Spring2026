package notsodefault;

public class Main {
    public static void main(String[] args) {

        Student student1 = new Student("Zhenis", 243031693);

        System.out.println(student1.getName());
        System.out.println(student1.getId());
        System.out.println(student1.getYearOfStudy());
        
        
        
        
        student1.incrementYearOfStudy();
        
        System.out.println(student1.getYearOfStudy());
        
        
        
        Triangle smollish = new Triangle(4);
        smollish.ToString();



        Time t = new Time(15, 45, 70);
        System.out.println(t.toUniversal());
        
        t.add(2, 17, 40);
        System.out.println(t.toUniversal());
        System.out.println(t.toStandard());
    }
}
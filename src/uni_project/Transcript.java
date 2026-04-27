package uni_project;

import java.util.Map;

public class Transcript {

    private final Student          student;
    private final Map<Course, Mark> records;
    private final double           gpa;

    public Transcript(Student student, Map<Course, Mark> records, double gpa) {
        this.student = student;
        this.records = records;
        this.gpa     = gpa;
    }

    public void print() {
        String line = "========================================";
        System.out.println(line);
        System.out.println("         OFFICIAL TRANSCRIPT           ");
        System.out.println(line);
        System.out.printf("  Student : %s %s%n",
                student.getFirstName(), student.getLastName());
        System.out.printf("  ID      : %s%n", student.getStudentId());
        System.out.printf("  Major   : %s%n", student.getMajor());
        System.out.printf("  GPA     : %.2f / 4.00%n", gpa);
        System.out.println(line);
        records.forEach((c, m) ->
                System.out.printf("  %-22s %6.1f  %s%n",
                        c.getName(), m.getTotalScore(),
                        m.isPassing() ? "PASS" : "FAIL"));
        System.out.println(line);
    }

    public Student          getStudent() { return student; }
    public Map<Course, Mark> getRecords() { return records; }
    public double           getGpa()     { return gpa; }
}

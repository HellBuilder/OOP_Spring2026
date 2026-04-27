package uni_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Course implements Serializable {

    private static final long serialVersionUID = 11L;

    private String         courseId;
    private String         name;
    private int            credits;
    private List<Teacher>  instructors;
    private List<Student>  enrolledStudents;
    private Major          major;
    private int            yearOfStudy;

    public Course(String courseId, String name, int credits,
                  Major major, int yearOfStudy) {
        this.courseId         = courseId;
        this.name             = name;
        this.credits          = credits;
        this.major            = major;
        this.yearOfStudy      = yearOfStudy;
        this.instructors      = new ArrayList<>();
        this.enrolledStudents = new ArrayList<>();
    }

    public void enrollStudent(Student student) {
        if (!enrolledStudents.contains(student)) {
            enrolledStudents.add(student);
        }
    }

    public void addInstructor(Teacher teacher) {
        if (!instructors.contains(teacher)) {
            instructors.add(teacher);
        }
    }

    // ---- Getters ----

    public String        getCourseId()          { return courseId; }
    public String        getName()              { return name; }
    public int           getCredits()           { return credits; }
    public List<Teacher> getInstructors()       { return instructors; }
    public List<Student> getEnrolledStudents()  { return enrolledStudents; }
    public Major         getMajor()             { return major; }
    public int           getYearOfStudy()       { return yearOfStudy; }

    @Override
    public String toString() {
        return "Course[" + courseId + ", \"" + name + "\", " + credits + " cr]";
    }
}

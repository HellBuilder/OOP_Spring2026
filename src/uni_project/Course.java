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
    private List<Lesson>   lessons;
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
        this.lessons          = new ArrayList<>();
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

    public void addLesson(Lesson lesson) {
        if (!lessons.contains(lesson)) {
            lessons.add(lesson);
        }
    }

    public void printSchedule() {
        System.out.println("=== Schedule: " + name + " ===");
        if (lessons.isEmpty()) {
            System.out.println("  (no lessons scheduled)");
        } else {
            lessons.forEach(l -> System.out.println("  " + l));
        }
    }

    // ---- Getters ----

    public String        getCourseId()          { return courseId; }
    public String        getName()              { return name; }
    public int           getCredits()           { return credits; }
    public List<Teacher> getInstructors()       { return instructors; }
    public List<Student> getEnrolledStudents()  { return enrolledStudents; }
    public List<Lesson>  getLessons()           { return lessons; }
    public Major         getMajor()             { return major; }
    public int           getYearOfStudy()       { return yearOfStudy; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        return courseId.equals(((Course) o).courseId);
    }

    @Override
    public int hashCode() {
        return courseId != null ? courseId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Course[" + courseId + ", \"" + name + "\", " + credits + " cr]";
    }
}

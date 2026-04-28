package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Teacher extends Employee implements Researcher {

    private static final long serialVersionUID = 5L;

    private TeacherTitle         title;
    private List<Course>         courses;
    private double               rating;
    private int                  ratingCount;
    private int                  hIndex;
    private List<ResearchPaper>  papers;
    private List<ResearchProject> projects;

    public Teacher(String userId, String firstName, String lastName,
                   String email, String password,
                   String employeeId, String department, double salary,
                   TeacherTitle title) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
        this.title       = title;
        this.courses     = new ArrayList<>();
        this.rating      = 0.0;
        this.ratingCount = 0;
        this.hIndex      = 0;
        this.papers      = new ArrayList<>();
        this.projects    = new ArrayList<>();
    }

    // ================================================================
    // Teaching
    // ================================================================

    public void putMark(Student student, Course course,
                        double firstAtt, double secondAtt, double finalExam) {
        Mark mark = new Mark(firstAtt, secondAtt, finalExam);
        student.addMark(course, mark);
        System.out.println("[MARK] " + course.getName() + " | "
                + student.getFirstName() + " → total " + mark.getTotalScore());
    }

    public void generateMarkReport(Course course) {
        System.out.println("=== Mark Report: " + course.getName() + " ===");
        course.getEnrolledStudents().forEach(s -> {
            Mark m = s.getMarks().get(course);
            if (m != null) {
                System.out.printf("  %-25s %6.1f  %s%n",
                        s.getFirstName() + " " + s.getLastName(),
                        m.getTotalScore(),
                        m.isPassing() ? "PASS" : "FAIL");
            }
        });
    }

    /** Called by Student.rateTeacher() — incremental average. */
    void addRating(double r) {
        rating = (rating * ratingCount + r) / ++ratingCount;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) courses.add(course);
    }

    public void addProject(ResearchProject project) {
        if (!projects.contains(project)) projects.add(project);
    }

    /**
     * PROFESSOR title requires prior research activity.
     * This rule is enforced as a runtime guard rather than a compile-time constraint
     * because TeacherTitle is assigned at runtime.
     */
    public void setTitle(TeacherTitle title) {
        if (title == TeacherTitle.PROFESSOR && papers.isEmpty()) {
            System.out.println("[WARN] Assigning PROFESSOR title but no papers on record for "
                    + getFirstName() + " " + getLastName() + ".");
        }
        this.title = title;
    }

    // ================================================================
    // Researcher
    // ================================================================

    @Override
    public int getHIndex() { return hIndex; }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public List<ResearchProject> getProjects() { return projects; }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        hIndex = ResearcherUtils.computeHIndex(papers);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(c)
              .forEach(p -> System.out.println("  • " + p));
    }

    // ================================================================
    // Getters
    // ================================================================

    public TeacherTitle          getTitle()       { return title; }
    public List<Course>          getCourses()     { return courses; }
    public double                getRating()      { return rating; }
    public int                   getRatingCount() { return ratingCount; }

    // ================================================================
    // Object
    // ================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Teacher)) return false;
        Teacher t = (Teacher) o;
        return getEmployeeId().equals(t.getEmployeeId());
    }

    @Override
    public int hashCode() {
        return getEmployeeId().hashCode();
    }

    @Override
    public String toString() {
        return "Teacher[" + getEmployeeId() + ", " + getFirstName() + " " + getLastName()
                + ", " + title + "]";
    }
}

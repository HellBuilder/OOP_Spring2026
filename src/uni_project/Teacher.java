package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Teacher extends Employee implements Researcher {

    private static final long serialVersionUID = 5L;

    private TeacherTitle          title;
    private List<Course>          courses;
    private double                rating;
    private int                   ratingCount;
    private int                   hIndex;
    private List<ResearchPaper>   papers;
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

    public void putMark(Student student, Course course,
                        double firstAtt, double secondAtt, double finalExam) {
        Mark mark = new Mark(firstAtt, secondAtt, finalExam);
        student.addMark(course, mark);
        System.out.println("[MARK] " + course.getName() + " | "
                + student.getFirstName() + " -> total " + mark.getTotalScore());
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

    void addRating(double r) {
        rating = (rating * ratingCount + r) / ++ratingCount;
    }

    public void addCourse(Course course) {
        if (!courses.contains(course)) courses.add(course);
    }

    public void addProject(ResearchProject project) {
        if (!projects.contains(project)) projects.add(project);
    }

    public void setTitle(TeacherTitle title) {
        if (title == TeacherTitle.PROFESSOR && papers.isEmpty()) {
            System.out.println("[WARN] Assigning PROFESSOR title but no papers on record for "
                    + getFirstName() + " " + getLastName() + ".");
        }
        this.title = title;
    }

    @Override
    public int getHIndex() { return hIndex; }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        hIndex = ResearcherUtils.computeHIndex(papers);
    }

    @Override
    public void printPapers() {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(ResearchPaper.BY_CITATIONS)
              .forEach(p -> System.out.println("  * " + p));
    }

    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(comparator)
              .forEach(p -> System.out.println("  * " + p));
    }

    public TeacherTitle          getTitle()       { return title; }
    public List<Course>          getCourses()     { return courses; }
    public double                getRating()      { return rating; }
    public int                   getRatingCount() { return ratingCount; }
    public List<ResearchProject> getProjects()    { return projects; }
}

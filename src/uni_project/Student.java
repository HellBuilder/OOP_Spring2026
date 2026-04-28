package uni_project;

import java.util.*;

public class Student extends User implements Observer {

    private static final long serialVersionUID = 3L;
    public  static final int  MAX_CREDITS = 21;
    public  static final int  MAX_FAILS   = 3;
    public  static final int  MIN_SUPERVISOR_H_INDEX = 3;

    private String             studentId;
    private int                year;
    private Major              major;
    private double             gpa;
    private int                credits;
    private int                failCount;
    private List<Course>       enrolledCourses;
    private Map<Course, Mark>  marks;
    private Researcher         supervisor;

    public Student(String userId, String firstName, String lastName,
                   String email, String password,
                   String studentId, int year, Major major) {
        super(userId, firstName, lastName, email, password);
        this.studentId       = studentId;
        this.year            = year;
        this.major           = major;
        this.gpa             = 0.0;
        this.credits         = 0;
        this.failCount       = 0;
        this.enrolledCourses = new ArrayList<>();
        this.marks           = new LinkedHashMap<>();
    }

    // ================================================================
    // Business Logic
    // ================================================================

    public void registerForCourse(Course course)
            throws MaxCreditsExceededException, MaxFailsExceededException {

        if (failCount >= MAX_FAILS) {
            throw new MaxFailsExceededException(
                    getStudentId() + " has already failed " + failCount
                    + " courses — maximum is " + MAX_FAILS + ".");
        }
        if (credits + course.getCredits() > MAX_CREDITS) {
            throw new MaxCreditsExceededException(
                    "Cannot add '" + course.getName() + "' ("
                    + course.getCredits() + " cr): would reach "
                    + (credits + course.getCredits()) + " / " + MAX_CREDITS + " credits.");
        }
        if (enrolledCourses.contains(course)) {
            System.out.println("[WARN] Already enrolled in " + course.getName());
            return;
        }

        enrolledCourses.add(course);
        course.enrollStudent(this);
        credits += course.getCredits();
        System.out.println("[ENROLL] " + getFirstName() + " → " + course.getName()
                + " (" + credits + "/" + MAX_CREDITS + " cr)");
    }

    public void viewMarks() {
        System.out.println("=== Marks: " + getFirstName() + " " + getLastName() + " ===");
        if (marks.isEmpty()) {
            System.out.println("  (no marks yet)");
            return;
        }
        marks.forEach((c, m) ->
                System.out.printf("  %-30s %6.1f  %s%n",
                        c.getName(), m.getTotalScore(),
                        m.isPassing() ? "PASS" : "FAIL"));
    }

    public Transcript getTranscript() {
        return new Transcript(this, Collections.unmodifiableMap(marks), gpa);
    }

    public void rateTeacher(Teacher teacher, double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be in [0, 5].");
        }
        teacher.addRating(rating);
        System.out.println("[RATE] " + getFirstName() + " rated "
                + teacher.getFirstName() + " → " + String.format("%.1f", rating));
    }

    public void printPapers(Comparator<ResearchPaper> c) {
        System.out.println(getFirstName() + " is not a researcher — no papers.");
    }

    public void setSupervisor(Researcher supervisor) throws LowHIndexException {
        if (supervisor.getHIndex() < MIN_SUPERVISOR_H_INDEX) {
            throw new LowHIndexException(
                    "Supervisor hIndex=" + supervisor.getHIndex()
                    + " is below required minimum=" + MIN_SUPERVISOR_H_INDEX + ".");
        }
        this.supervisor = supervisor;
        System.out.println("[SUPERVISOR] Assigned to " + getFirstName()
                + " (supervisor hIndex=" + supervisor.getHIndex() + ")");
    }

    /** Called by Teacher.putMark() — updates internal state. */
    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
        if (!mark.isPassing()) {
            failCount++;
        }
        recalculateGpa();
    }

    private void recalculateGpa() {
        if (marks.isEmpty()) { gpa = 0.0; return; }
        double sum = 0;
        for (Mark m : marks.values()) sum += m.getTotalScore();
        gpa = (sum / marks.size()) / 25.0; // 100-pt → 4.0 scale
    }

    // ================================================================
    // Observer
    // ================================================================

    @Override
    public void update(String news) {
        receiveMessage("[NEWS] " + news);
        System.out.println("[NOTIFY→" + getFirstName() + "] " + news);
    }

    // ================================================================
    // Getters
    // ================================================================

    public String            getStudentId()       { return studentId; }
    public int               getYear()            { return year; }
    public Major             getMajor()           { return major; }
    public double            getGpa()             { return gpa; }
    public int               getCredits()         { return credits; }
    public int               getFailCount()       { return failCount; }
    public List<Course>      getEnrolledCourses() { return enrolledCourses; }
    public Map<Course, Mark> getMarks()           { return marks; }
    public Researcher        getSupervisor()      { return supervisor; }

    // ================================================================
    // Object
    // ================================================================

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student s = (Student) o;
        return studentId.equals(s.studentId);
    }

    @Override
    public int hashCode() {
        return studentId.hashCode();
    }

    @Override
    public String toString() {
        return "Student[" + studentId + ", " + getFirstName() + " " + getLastName()
                + ", year=" + year + ", major=" + major + "]";
    }
}

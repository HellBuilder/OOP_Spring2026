package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents a teaching member of staff who also conducts research.
 *
 * <p>{@code Teacher} extends {@link Employee} with course-management and
 * assessment responsibilities, and additionally implements the
 * {@link Researcher} interface to track publications and the h-index.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li><b>Dual role</b> – a {@code Teacher} is simultaneously an employee
 *       (salary, department) and a researcher (papers, h-index).  The
 *       {@link Researcher} interface is implemented directly on this class
 *       rather than through composition, which keeps the type hierarchy
 *       simple while still satisfying the {@code instanceof Researcher}
 *       check used by {@link ResearchProject#addParticipant(Object)}.</li>
 *   <li><b>Incremental rating average</b> – {@link #addRating(double)} uses
 *       Welford-style incremental averaging so that the full rating history
 *       does not need to be stored.</li>
 *   <li><b>h-index</b> – recomputed after every
 *       {@link #addPaper(ResearchPaper)} call using
 *       {@link ResearcherUtils#computeHIndex(List)}.</li>
 *   <li><b>PROFESSOR guard</b> – {@link #setTitle(TeacherTitle)} emits a
 *       warning if the PROFESSOR title is assigned before any papers have
 *       been published, enforcing a soft domain rule.</li>
 * </ul>
 *
 * @see Employee
 * @see Researcher
 * @see ResearchPaper
 * @see TeacherTitle
 * @author OOP Team
 * @version 1.0
 */
public class Teacher extends Employee implements Researcher {

    private static final long serialVersionUID = 5L;

    private TeacherTitle         title;
    private List<Course>         courses;
    private double               rating;
    private int                  ratingCount;
    private int                  hIndex;
    private List<ResearchPaper>  papers;
    private List<ResearchProject> projects;

    /**
     * Constructs a new {@code Teacher} with the supplied employment and
     * academic-title details.
     *
     * @param userId     unique system-wide user identifier
     * @param firstName  teacher's first name
     * @param lastName   teacher's last name
     * @param email      login e-mail address
     * @param password   login password
     * @param employeeId institution-assigned employee ID
     * @param department the academic department (e.g., "Computer Science")
     * @param salary     monthly gross salary
     * @param title      academic title (see {@link TeacherTitle})
     */
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

    /**
     * Records component scores for a student in the given course and creates
     * the corresponding {@link Mark}.
     *
     * <p>The mark is immediately forwarded to
     * {@link Student#addMark(Course, Mark)}, which updates the student's GPA
     * and fail count.  All three score components are on a 0–100 partial scale
     * (first attestation 30 pts, second attestation 30 pts, final exam 40 pts,
     * totalling 100 pts).</p>
     *
     * @param student   the student to receive the mark (must not be {@code null})
     * @param course    the course being graded (must not be {@code null})
     * @param firstAtt  first attestation score (0–30 recommended range)
     * @param secondAtt second attestation score (0–30 recommended range)
     * @param finalExam final examination score (0–40 recommended range)
     */
    public void putMark(Student student, Course course,
                        double firstAtt, double secondAtt, double finalExam) {
        Mark mark = new Mark(firstAtt, secondAtt, finalExam);
        student.addMark(course, mark);
        System.out.println("[MARK] " + course.getName() + " | "
                + student.getFirstName() + " → total " + mark.getTotalScore());
    }

    /**
     * Prints a formatted mark report for all enrolled students in the given
     * course.
     *
     * <p>Only students who already have a recorded mark are listed.  Students
     * without a mark entry are silently skipped.</p>
     *
     * @param course the course whose mark report is to be generated
     */
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

    /**
     * Updates the teacher's running average rating with one new value.
     *
     * <p>This method has package-private visibility; it is called exclusively
     * by {@link Student#rateTeacher(Teacher, double)} to prevent direct
     * rating manipulation from arbitrary code.</p>
     *
     * @param r the new rating value in [0.0, 5.0]
     */
    void addRating(double r) {
        rating = (rating * ratingCount + r) / ++ratingCount;
    }

    /**
     * Associates a course with this teacher, if not already associated.
     *
     * @param course the course to add to this teacher's course list
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) courses.add(course);
    }

    /**
     * Associates a research project with this teacher, if not already present.
     *
     * @param project the {@link ResearchProject} to link to this teacher
     */
    public void addProject(ResearchProject project) {
        if (!projects.contains(project)) projects.add(project);
    }

    /**
     * Updates this teacher's academic title.
     *
     * <p>A soft business rule is enforced: assigning the
     * {@link TeacherTitle#PROFESSOR} title to a teacher who has no published
     * papers emits a warning to standard output, because the professor rank
     * conventionally requires prior research activity.  The title is set
     * regardless, as the rule is advisory rather than hard-blocking.</p>
     *
     * @param title the new {@link TeacherTitle} to assign
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

    /**
     * Returns this teacher's current h-index.
     *
     * <p>The h-index is recomputed each time a paper is added via
     * {@link #addPaper(ResearchPaper)}.</p>
     *
     * @return h-index value (0 if no papers have been published)
     */
    @Override
    public int getHIndex() { return hIndex; }

    /**
     * Returns the list of research papers published by this teacher.
     *
     * @return mutable list of {@link ResearchPaper} objects; never {@code null}
     */
    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    /**
     * Adds a research paper to this teacher's publication list and
     * recomputes the h-index.
     *
     * @param paper the {@link ResearchPaper} to add (must not be {@code null})
     */
    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        hIndex = ResearcherUtils.computeHIndex(papers);
    }

    /**
     * Prints all research papers published by this teacher to standard output,
     * sorted in descending order of citation count.
     *
     * <p>The output header includes the teacher's current h-index for quick
     * reference.</p>
     */
    @Override
    public void printPapers() {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(ResearchPaper.BY_CITATIONS)
              .forEach(p -> System.out.println("  • " + p));
    }

    /**
     * Prints all research papers sorted by the provided {@link Comparator}.
     *
     * <p>This overload allows callers to choose an alternative ordering, such
     * as {@link ResearchPaper#BY_DATE} or {@link ResearchPaper#BY_PAGES},
     * without modifying the default behaviour of {@link #printPapers()}.</p>
     *
     * @param comparator the comparator to use for ordering (must not be
     *                   {@code null})
     */
    public void printPapers(Comparator<ResearchPaper> comparator) {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(comparator)
              .forEach(p -> System.out.println("  • " + p));
    }

    // ================================================================
    // Getters
    // ================================================================

    /**
     * Returns this teacher's current academic title.
     *
     * @return the {@link TeacherTitle} enum constant
     */
    public TeacherTitle          getTitle()       { return title; }

    /**
     * Returns the list of courses assigned to this teacher.
     *
     * @return mutable list of {@link Course} objects
     */
    public List<Course>          getCourses()     { return courses; }

    /**
     * Returns the current average student rating for this teacher.
     *
     * @return average rating in [0.0, 5.0], or 0.0 if no ratings have been
     *         submitted yet
     */
    public double                getRating()      { return rating; }

    /**
     * Returns the number of individual ratings that have been submitted for
     * this teacher.
     *
     * @return rating submission count
     */
    public int                   getRatingCount() { return ratingCount; }

    /**
     * Returns the list of research projects this teacher participates in.
     *
     * @return mutable list of {@link ResearchProject} objects
     */
    public List<ResearchProject> getProjects()    { return projects; }
}

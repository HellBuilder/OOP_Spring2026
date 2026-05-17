package uni_project;

import java.util.*;

/**
 * Represents a university student enrolled in one or more courses.
 *
 * <p>{@code Student} extends {@link User} with academic-tracking attributes
 * (GPA, credit load, fail count, marks) and implements the {@link Observer}
 * interface so that it can receive news notifications posted by a
 * {@link Manager}.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li><b>Observer pattern</b> – {@code Student} is the concrete observer;
 *       {@link Manager} is the concrete observable.  When a manager calls
 *       {@link Manager#postNews(String)}, every subscribed student's
 *       {@link #update(String)} method is invoked automatically.</li>
 *   <li><b>Business rules</b> – course registration is guarded by two hard
 *       limits: {@link #MAX_CREDITS} and {@link #MAX_FAILS}.  Violating
 *       either throws a checked exception, forcing callers to handle the
 *       condition explicitly.</li>
 *   <li><b>GPA calculation</b> – recalculated incrementally each time a new
 *       {@link Mark} is recorded via {@link #addMark(Course, Mark)},
 *       converting the 100-point scale to a 4.0 GPA scale.</li>
 *   <li><b>Supervisor assignment</b> – a researcher assigned as supervisor
 *       must have an h-index of at least {@link #MIN_SUPERVISOR_H_INDEX};
 *       this is enforced by {@link #setSupervisor(Researcher)} with a
 *       {@link LowHIndexException}.</li>
 * </ul>
 *
 * @see User
 * @see Observer
 * @see Manager
 * @see Transcript
 * @author OOP Team
 * @version 1.0
 */
public class Student extends User implements Observer {

    private static final long serialVersionUID = 3L;

    /** Maximum credit load a student may carry in a single semester. */
    public  static final int  MAX_CREDITS = 21;

    /** Maximum number of failed courses before further registration is blocked. */
    public  static final int  MAX_FAILS   = 3;

    /** Minimum h-index required for a researcher to be assigned as supervisor. */
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

    /**
     * Constructs a new {@code Student} with the given personal and academic
     * details.
     *
     * @param userId      unique system-wide user identifier
     * @param firstName   student's first name
     * @param lastName    student's last name
     * @param email       login e-mail address
     * @param password    login password
     * @param studentId   institution-assigned student ID (e.g., "SE-2301")
     * @param year        current academic year (1–4 for most programmes)
     * @param major       degree programme / major (see {@link Major})
     */
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

    /**
     * Attempts to enroll this student in the specified course.
     *
     * <p>The method enforces two pre-conditions before enrollment:</p>
     * <ol>
     *   <li>The student's accumulated fail count must be strictly below
     *       {@link #MAX_FAILS}.</li>
     *   <li>Adding the course's credits must not push the total above
     *       {@link #MAX_CREDITS}.</li>
     * </ol>
     * <p>If the student is already enrolled in the course, a warning is
     * printed and the method returns silently without double-counting.</p>
     *
     * @param course the course to register for (must not be {@code null})
     * @throws MaxFailsExceededException   if the student has already reached
     *                                     the maximum allowed number of
     *                                     course failures
     * @throws MaxCreditsExceededException if enrolling would exceed the
     *                                     per-semester credit cap
     */
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

    /**
     * Prints a formatted table of all marks recorded for this student to
     * standard output.
     *
     * <p>Each row shows the course name, total score (out of 100), and
     * pass/fail status.  If no marks have been recorded yet, a placeholder
     * line is printed instead.</p>
     */
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

    /**
     * Creates and returns an immutable {@link Transcript} snapshot for this
     * student.
     *
     * <p>The transcript captures the current marks map and GPA at the time of
     * the call.  Subsequent mark additions do not affect the returned
     * transcript.</p>
     *
     * @return a new {@link Transcript} containing this student's academic record
     */
    public Transcript getTranscript() {
        return new Transcript(this, Collections.unmodifiableMap(marks), gpa);
    }

    /**
     * Submits a numerical rating for the given teacher.
     *
     * <p>The rating must be in the closed interval [0, 5].  The teacher's
     * running average is updated via {@link Teacher#addRating(double)}.</p>
     *
     * @param teacher the teacher to rate (must not be {@code null})
     * @param rating  a value in [0.0, 5.0]
     * @throws IllegalArgumentException if {@code rating} is outside [0, 5]
     */
    public void rateTeacher(Teacher teacher, double rating) {
        if (rating < 0 || rating > 5) {
            throw new IllegalArgumentException("Rating must be in [0, 5].");
        }
        teacher.addRating(rating);
        System.out.println("[RATE] " + getFirstName() + " rated "
                + teacher.getFirstName() + " → " + String.format("%.1f", rating));
    }

    /**
     * Prints a notice that this student has no research papers.
     *
     * <p>Provided for API symmetry with {@link Teacher#printPapers()} and
     * {@link ResearchStudent} overrides.  A plain {@code Student} is not a
     * {@link Researcher}, so no paper list is maintained here.</p>
     */
    public void printPapers() {
        System.out.println(getFirstName() + " is not a researcher — no papers.");
    }

    /**
     * Assigns a research supervisor to this student.
     *
     * <p>The supervisor must have an h-index of at least
     * {@link #MIN_SUPERVISOR_H_INDEX}.  This requirement ensures that
     * supervisors have a minimum proven research track record.</p>
     *
     * @param supervisor the {@link Researcher} to assign as supervisor
     *                   (must not be {@code null})
     * @throws LowHIndexException if the candidate supervisor's h-index is
     *                            below {@link #MIN_SUPERVISOR_H_INDEX}
     */
    public void setSupervisor(Researcher supervisor) throws LowHIndexException {
        if (year != 4) {
            throw new IllegalStateException(
                    "Only 4th year students can be assigned a research supervisor. "
                    + getFirstName() + " is in year " + year + ".");
        }
        if (supervisor.getHIndex() < MIN_SUPERVISOR_H_INDEX) {
            throw new LowHIndexException(
                    "Supervisor hIndex=" + supervisor.getHIndex()
                    + " is below required minimum=" + MIN_SUPERVISOR_H_INDEX + ".");
        }
        this.supervisor = supervisor;
        System.out.println("[SUPERVISOR] Assigned to " + getFirstName()
                + " (supervisor hIndex=" + supervisor.getHIndex() + ")");
    }

    /**
     * Records a {@link Mark} for the given course and updates internal
     * academic state.
     *
     * <p>This method is called by {@link Teacher#putMark} rather than
     * directly by client code.  If the mark represents a failing grade,
     * the student's fail counter is incremented.  The GPA is recalculated
     * after every invocation.</p>
     *
     * @param course the course for which the mark is being recorded
     * @param mark   the {@link Mark} object containing component scores
     */
    public void addMark(Course course, Mark mark) {
        marks.put(course, mark);
        if (!mark.isPassing()) {
            failCount++;
        }
        recalculateGpa();
    }

    /**
     * Recomputes the student's GPA from the full marks map.
     *
     * <p>The internal computation averages all total scores (0–100 scale) and
     * converts the result to a 4.0 GPA scale by dividing by 25.</p>
     */
    private void recalculateGpa() {
        if (marks.isEmpty()) { gpa = 0.0; return; }
        double sum = 0;
        for (Mark m : marks.values()) sum += m.getTotalScore();
        gpa = (sum / marks.size()) / 25.0; // 100-pt → 4.0 scale
    }

    // ================================================================
    // Observer
    // ================================================================

    /**
     * Receives a news notification from the observed {@link Manager}.
     *
     * <p>The message is stored in the user's inbox (via
     * {@link User#receiveMessage(String)}) and also echoed to standard output
     * so that live demonstrations clearly show the notification flow.</p>
     *
     * @param news the news text broadcast by the manager
     */
    @Override
    public void update(String news) {
        receiveMessage("[NEWS] " + news);
        System.out.println("[NOTIFY→" + getFirstName() + "] " + news);
    }

    // ================================================================
    // Getters
    // ================================================================

    /**
     * Returns the institution-assigned student identifier.
     *
     * @return student ID string
     */
    public String            getStudentId()       { return studentId; }

    /**
     * Returns the student's current academic year.
     *
     * @return year number (typically 1–4)
     */
    public int               getYear()            { return year; }

    /**
     * Returns the student's declared major / degree programme.
     *
     * @return the {@link Major} enum constant
     */
    public Major             getMajor()           { return major; }

    /**
     * Returns the student's current GPA on a 4.0 scale.
     *
     * @return GPA value in [0.0, 4.0]
     */
    public double            getGpa()             { return gpa; }

    /**
     * Returns the total number of credits the student is currently carrying.
     *
     * @return credit count
     */
    public int               getCredits()         { return credits; }

    /**
     * Returns the number of courses the student has failed to date.
     *
     * @return fail count
     */
    public int               getFailCount()       { return failCount; }

    /**
     * Returns the live list of courses in which this student is currently
     * enrolled.
     *
     * @return mutable list of enrolled {@link Course} objects
     */
    public List<Course>      getEnrolledCourses() { return enrolledCourses; }

    /**
     * Returns the live map from courses to recorded marks.
     *
     * @return mutable map of {@link Course} to {@link Mark}
     */
    public Map<Course, Mark> getMarks()           { return marks; }

    /**
     * Returns the assigned research supervisor, or {@code null} if none has
     * been set.
     *
     * @return the supervisor {@link Researcher}, or {@code null}
     */
    public Researcher        getSupervisor()      { return supervisor; }
}

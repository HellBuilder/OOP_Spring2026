package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents an administrative manager responsible for course registration
 * oversight, staff assignment, and university-wide news broadcasting.
 *
 * <p>{@code Manager} extends {@link Employee} and implements the
 * {@link Observable} interface, making it the concrete subject in the
 * Observer pattern used for news notifications.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li><b>Observer pattern (subject side)</b> – managers maintain a list of
 *       {@link Observer} subscribers (typically {@link Student} objects).
 *       Calling {@link #postNews(String)} broadcasts the news item to all
 *       registered observers via {@link #notifyObservers(String)}.</li>
 *   <li><b>Course registration workflow</b> – a {@link CourseRegistrationRequest}
 *       can be created in advance and later approved or rejected, or it can be
 *       created and immediately approved in one step through
 *       {@link #approveRegistration(Student, Course)}.</li>
 *   <li><b>Manager type</b> – the {@link ManagerType} enum distinguishes
 *       different management roles (e.g., dean, registrar) and is included in
 *       news broadcasts for auditing purposes.</li>
 * </ul>
 *
 * @see Employee
 * @see Observable
 * @see Observer
 * @see Student
 * @see CourseRegistrationRequest
 * @author OOP Team
 * @version 1.0
 */
public class Manager extends Employee implements Observable {

    private static final long serialVersionUID = 6L;

    private ManagerType                     managerType;
    private List<Observer>                  observers;
    private List<CourseRegistrationRequest> requests;

    /**
     * Constructs a new {@code Manager} with the given employment and
     * managerial-role details.
     *
     * @param userId      unique system-wide user identifier
     * @param firstName   manager's first name
     * @param lastName    manager's last name
     * @param email       login e-mail address
     * @param password    login password
     * @param employeeId  institution-assigned employee ID
     * @param department  the department this manager belongs to
     * @param salary      monthly gross salary
     * @param managerType the specific managerial role (see {@link ManagerType})
     */
    public Manager(String userId, String firstName, String lastName,
                   String email, String password,
                   String employeeId, String department, double salary,
                   ManagerType managerType) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
        this.managerType = managerType;
        this.observers   = new ArrayList<>();
        this.requests    = new ArrayList<>();
    }

    // ================================================================
    // Management Actions
    // ================================================================

    /**
     * Creates a new {@link CourseRegistrationRequest} for the given student
     * and course, adds it to the request queue, and returns it.
     *
     * <p>The request is created with {@link RequestStatus#PENDING} status and
     * must be explicitly approved or rejected afterwards.</p>
     *
     * @param student the student requesting registration (must not be
     *                {@code null})
     * @param course  the course the student wishes to register for (must not
     *                be {@code null})
     * @return the newly created {@link CourseRegistrationRequest}
     */
    public CourseRegistrationRequest createRequest(Student student, Course course) {
        CourseRegistrationRequest req = new CourseRegistrationRequest(student, course);
        requests.add(req);
        System.out.println("[REQUEST CREATED] " + req);
        return req;
    }

    /**
     * Approves the pending registration request for the given student–course
     * pair.
     *
     * <p>If a matching pending request already exists in the queue, that
     * request is approved in place.  If no such request is found, a new one
     * is created and immediately approved, supporting the common fast-path
     * where a manager directly enrolls a student without a prior request.</p>
     *
     * @param student the student whose registration is to be approved
     * @param course  the course to approve the registration for
     */
    public void approveRegistration(Student student, Course course) {
        for (CourseRegistrationRequest req : requests) {
            if (req.getStudent().equals(student)
                    && req.getCourse().equals(course)
                    && req.getStatus() == RequestStatus.PENDING) {
                req.approve();
                return;
            }
        }
        // No existing request — create and immediately approve
        CourseRegistrationRequest req = new CourseRegistrationRequest(student, course);
        requests.add(req);
        req.approve();
    }

    /**
     * Rejects the pending registration request for the given student–course
     * pair with a stated reason.
     *
     * <p>If no matching pending request is found a warning is printed to
     * standard output and the method returns without effect.</p>
     *
     * @param student the student whose registration is to be rejected
     * @param course  the course whose pending registration should be rejected
     * @param reason  a human-readable explanation for the rejection
     */
    public void rejectRegistration(Student student, Course course, String reason) {
        for (CourseRegistrationRequest req : requests) {
            if (req.getStudent().equals(student)
                    && req.getCourse().equals(course)
                    && req.getStatus() == RequestStatus.PENDING) {
                req.reject(reason);
                return;
            }
        }
        System.out.println("[WARN] No pending request found for "
                + student.getFirstName() + " → " + course.getName());
    }

    /**
     * Prints all registration requests managed by this manager to standard
     * output, regardless of their current status.
     *
     * <p>If no requests have been created yet, a placeholder {@code "(none)"}
     * line is printed.</p>
     */
    public void viewRequests() {
        System.out.println("=== Registration Requests (Manager: "
                + getFirstName() + ") ===");
        if (requests.isEmpty()) {
            System.out.println("  (none)");
        } else {
            requests.forEach(r -> System.out.println("  " + r));
        }
    }

    /**
     * Assigns a teacher to a course, updating both the course's instructor
     * list and the teacher's course list.
     *
     * @param teacher the {@link Teacher} to assign (must not be {@code null})
     * @param course  the {@link Course} to assign the teacher to (must not be
     *                {@code null})
     */
    public void assignTeacher(Teacher teacher, Course course) {
        course.addInstructor(teacher);
        teacher.addCourse(course);
        System.out.println("[ASSIGN] " + teacher.getFirstName()
                + " (" + teacher.getTitle() + ") → " + course.getName());
    }

    /**
     * Broadcasts a news item to all registered observers.
     *
     * <p>The news is printed to standard output (prefixed with the manager
     * type) and then forwarded to every subscribed {@link Observer} by
     * calling {@link #notifyObservers(String)}.</p>
     *
     * @param news the news text to broadcast (must not be {@code null})
     */
    public void postNews(String news) {
        System.out.println("[NEWS/" + managerType + "] " + news);
        notifyObservers(news);
    }

    // ================================================================
    // Observable
    // ================================================================

    /**
     * Registers an observer to receive future news notifications.
     *
     * <p>Each observer is added at most once; duplicate registrations are
     * silently ignored.</p>
     *
     * @param observer the {@link Observer} to subscribe (must not be
     *                 {@code null})
     */
    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    /**
     * Removes a previously registered observer so that it no longer receives
     * news notifications.
     *
     * <p>If the observer is not currently registered, this method has no
     * effect.</p>
     *
     * @param observer the {@link Observer} to unsubscribe
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Delivers a news notification to all currently registered observers.
     *
     * <p>Each observer's {@link Observer#update(String)} method is called
     * in list order.  This method is called internally by
     * {@link #postNews(String)} and may also be called directly if a
     * notification must be sent without the standard prefix formatting.</p>
     *
     * @param news the news text to deliver to every observer
     */
    @Override
    public void notifyObservers(String news) {
        for (Observer o : observers) {
            o.update(news);
        }
    }

    // ================================================================
    // Getters
    // ================================================================

    /**
     * Returns the managerial role of this manager.
     *
     * @return the {@link ManagerType} enum constant
     */
    public ManagerType                     getManagerType() { return managerType; }

    /**
     * Returns the live list of registered observers.
     *
     * @return mutable list of {@link Observer} objects; never {@code null}
     */
    public List<Observer>                  getObservers()   { return observers; }

    /**
     * Returns the live list of all course registration requests tracked by
     * this manager.
     *
     * @return mutable list of {@link CourseRegistrationRequest} objects;
     *         never {@code null}
     */
    public List<CourseRegistrationRequest> getRequests()    { return requests; }

    // ================================================================
    // Analytics & Reporting
    // ================================================================

    /** Prints academic performance statistics for all courses in the university. */
    public void generateStatisticalReport() {
        System.out.println("=== Academic Performance Report (by " + getFirstName() + ") ===");
        for (Course course : University.getInstance().getCourses()) {
            List<Student> students = course.getEnrolledStudents();
            if (students.isEmpty()) continue;
            long pass = students.stream()
                    .filter(s -> s.getMarks().containsKey(course)
                              && s.getMarks().get(course).isPassing())
                    .count();
            long fail = students.stream()
                    .filter(s -> s.getMarks().containsKey(course)
                              && !s.getMarks().get(course).isPassing())
                    .count();
            double avg = students.stream()
                    .filter(s -> s.getMarks().containsKey(course))
                    .mapToDouble(s -> s.getMarks().get(course).getTotalScore())
                    .average().orElse(0);
            System.out.printf("  %-25s | enrolled=%d | pass=%d | fail=%d | avg=%.1f%n",
                    course.getName(), students.size(), pass, fail, avg);
        }
    }

    /** Prints all students sorted by GPA descending. */
    public void viewStudentsByGpa() {
        System.out.println("=== Students sorted by GPA (desc) ===");
        University.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .forEach(s -> System.out.printf("  %-25s GPA: %.2f%n",
                        s.getFirstName() + " " + s.getLastName(), s.getGpa()));
    }

    /** Prints all students sorted alphabetically by last name, then first name. */
    public void viewStudentsAlphabetically() {
        System.out.println("=== Students sorted alphabetically ===");
        University.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparing(Student::getLastName)
                        .thenComparing(Student::getFirstName))
                .forEach(s -> System.out.println("  " + s.getLastName()
                        + ", " + s.getFirstName() + " [" + s.getMajor() + "]"));
    }

    /** Prints all teachers sorted alphabetically by last name. */
    public void viewTeachersAlphabetically() {
        System.out.println("=== Teachers sorted alphabetically ===");
        University.getInstance().getUsers().stream()
                .filter(u -> u instanceof Teacher)
                .map(u -> (Teacher) u)
                .sorted(Comparator.comparing(Teacher::getLastName))
                .forEach(t -> System.out.printf("  %-25s | %s | rating: %.2f%n",
                        t.getLastName() + ", " + t.getFirstName(),
                        t.getTitle(), t.getRating()));
    }
}

package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager extends Employee implements Observable {

    private static final long serialVersionUID = 6L;

    private ManagerType                     managerType;
    private List<Observer>                  observers;
    private List<CourseRegistrationRequest> requests;

    public Manager(String userId, String firstName, String lastName,
                   String email, String password,
                   String employeeId, String department, double salary,
                   ManagerType managerType) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
        this.managerType = managerType;
        this.observers   = new ArrayList<>();
        this.requests    = new ArrayList<>();
    }

    public CourseRegistrationRequest createRequest(Student student, Course course) {
        CourseRegistrationRequest req = new CourseRegistrationRequest(student, course);
        requests.add(req);
        System.out.println("[REQUEST CREATED] " + req);
        return req;
    }

    public void approveRegistration(Student student, Course course) {
        for (CourseRegistrationRequest req : requests) {
            if (req.getStudent().equals(student)
                    && req.getCourse().equals(course)
                    && req.getStatus() == RequestStatus.PENDING) {
                req.approve();
                return;
            }
        }
        CourseRegistrationRequest req = new CourseRegistrationRequest(student, course);
        requests.add(req);
        req.approve();
    }

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
                + student.getFirstName() + " -> " + course.getName());
    }

    public void viewRequests() {
        System.out.println("=== Registration Requests (Manager: "
                + getFirstName() + ") ===");
        if (requests.isEmpty()) {
            System.out.println("  (none)");
        } else {
            requests.forEach(r -> System.out.println("  " + r));
        }
    }

    public void assignTeacher(Teacher teacher, Course course) {
        course.addInstructor(teacher);
        teacher.addCourse(course);
        System.out.println("[ASSIGN] " + teacher.getFirstName()
                + " (" + teacher.getTitle() + ") -> " + course.getName());
    }

    public void postNews(String news) {
        System.out.println("[NEWS/" + managerType + "] " + news);
        notifyObservers(news);
    }

    @Override
    public void addObserver(Observer observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String news) {
        for (Observer o : observers) {
            o.update(news);
        }
    }

    public ManagerType                     getManagerType() { return managerType; }
    public List<Observer>                  getObservers()   { return observers; }
    public List<CourseRegistrationRequest> getRequests()    { return requests; }

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

    public void viewStudentsByGpa() {
        System.out.println("=== Students sorted by GPA (desc) ===");
        University.getInstance().getUsers().stream()
                .filter(u -> u instanceof Student)
                .map(u -> (Student) u)
                .sorted(Comparator.comparingDouble(Student::getGpa).reversed())
                .forEach(s -> System.out.printf("  %-25s GPA: %.2f%n",
                        s.getFirstName() + " " + s.getLastName(), s.getGpa()));
    }

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

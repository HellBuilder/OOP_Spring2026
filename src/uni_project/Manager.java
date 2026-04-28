package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Manager extends Employee implements Observable {

    private static final long serialVersionUID = 6L;

    private ManagerType    managerType;
    private List<Observer> observers;

    public Manager(String userId, String firstName, String lastName,
                   String email, String password,
                   String employeeId, String department, double salary,
                   ManagerType managerType) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
        this.managerType = managerType;
        this.observers   = new ArrayList<>();
    }

    // ================================================================
    // Management Actions
    // ================================================================

    public void approveRegistration(Student student, Course course) {
        System.out.println("[APPROVE] " + getFirstName() + " approved "
                + student.getFirstName() + " for " + course.getName());
    }

    public void assignTeacher(Teacher teacher, Course course) {
        course.addInstructor(teacher);
        teacher.addCourse(course);
        System.out.println("[ASSIGN] " + teacher.getFirstName()
                + " (" + teacher.getTitle() + ") → " + course.getName());
    }

    public void postNews(String news) {
        System.out.println("[NEWS/" + managerType + "] " + news);
        notifyObservers(news);
    }

    public String createReport() {
        StringBuilder sb = new StringBuilder();
        sb.append("=== Academic Performance Report ===\n");
        University uni = University.getInstance();
        for (User u : uni.getUsers()) {
            if (u instanceof Student) {
                Student s = (Student) u;
                sb.append(String.format("  %-25s GPA: %.2f  Credits: %d  Fails: %d%n",
                        s.getFirstName() + " " + s.getLastName(),
                        s.getGpa(), s.getCredits(), s.getFailCount()));
            }
        }
        System.out.print(sb);
        return sb.toString();
    }

    public List<Student> viewStudents(Comparator<Student> sort) {
        List<Student> result = new ArrayList<>();
        for (User u : University.getInstance().getUsers()) {
            if (u instanceof Student) result.add((Student) u);
        }
        result.sort(sort);
        result.forEach(s -> System.out.println("  " + s));
        return result;
    }

    public List<String> viewRequests() {
        System.out.println("[REQUESTS] No pending requests.");
        return new ArrayList<>();
    }

    // ================================================================
    // Observable
    // ================================================================

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

    // ================================================================
    // Getters
    // ================================================================

    public ManagerType    getManagerType() { return managerType; }
    public List<Observer> getObservers()   { return observers; }
}

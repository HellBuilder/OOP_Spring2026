package uni_project;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class Admin extends Employee {

    private static final long serialVersionUID = 7L;

    private String       logFile;
    private List<String> logs;

    public Admin(String userId, String firstName, String lastName,
                 String email, String password,
                 String employeeId, String department, double salary,
                 String logFile) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
        this.logFile = logFile;
        this.logs    = new ArrayList<>();
    }

    // ================================================================
    // CRUD on Users (delegates to the Singleton)
    // ================================================================

    public void addUser(User user) {
        University.getInstance().addUser(user);
        log("ADD  | " + user.getClass().getSimpleName() + " id=" + user.getUserId());
    }

    public void removeUser(String userId) {
        University.getInstance().removeUser(userId);
        log("DEL  | userId=" + userId);
    }

    public void updateUser(User user, String field, String newValue) {
        switch (field.toLowerCase()) {
            case "email":    user.setEmail(newValue);    break;
            case "password": user.setPassword(newValue); break;
            case "firstname": user.setFirstName(newValue); break;
            case "lastname":  user.setLastName(newValue);  break;
            default:
                System.out.println("[WARN] Unknown field: " + field);
                return;
        }
        log("UPD  | " + user.getUserId() + " field=" + field);
    }

    public void viewLogs() {
        System.out.println("=== System Logs (" + logFile + ") ===");
        logs.forEach(l -> System.out.println("  " + l));
    }

    // ================================================================

    private void log(String action) {
        String entry = "[" + LocalDateTime.now() + "] " + action;
        logs.add(entry);
    }

    public String getLogFile() { return logFile; }
}

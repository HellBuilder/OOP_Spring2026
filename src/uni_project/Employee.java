package uni_project;

public abstract class Employee extends User {

    private static final long serialVersionUID = 2L;

    private String employeeId;
    private String department;
    private double salary;

    public Employee(String userId, String firstName, String lastName,
                    String email, String password,
                    String employeeId, String department, double salary) {
        super(userId, firstName, lastName, email, password);
        this.employeeId = employeeId;
        this.department = department;
        this.salary     = salary;
    }

    public void sendMessage(User to, String msg) {
        String formatted = "From " + getFirstName() + " " + getLastName() + ": " + msg;
        to.receiveMessage(formatted);
        System.out.println("[MSG] " + getFirstName() + " → " + to.getFirstName() + ": " + msg);
    }

    // ---- Getters ----

    public String getEmployeeId() { return employeeId; }
    public String getDepartment() { return department; }
    public double getSalary()     { return salary; }

    // ---- Setters ----

    public void setDepartment(String department) { this.department = department; }
    public void setSalary(double salary)         { this.salary     = salary; }
}

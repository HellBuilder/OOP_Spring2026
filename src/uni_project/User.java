package uni_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String       userId;
    private String       firstName;
    private String       lastName;
    private String       email;
    private String       password;
    private List<String> inbox;

    public User(String userId, String firstName, String lastName,
                String email, String password) {
        this.userId     = userId;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.email      = email;
        this.password   = password;
        this.inbox      = new ArrayList<>();
    }

    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    public void viewInbox() {
        System.out.println("=== Inbox: " + firstName + " " + lastName + " ===");
        if (inbox.isEmpty()) {
            System.out.println("  (empty)");
        } else {
            for (String msg : inbox) {
                System.out.println("  • " + msg);
            }
        }
    }

    public void receiveMessage(String message) {
        inbox.add(message);
    }

    // ---- Getters ----

    public String       getUserId()    { return userId; }
    public String       getFirstName() { return firstName; }
    public String       getLastName()  { return lastName; }
    public String       getEmail()     { return email; }
    public String       getPassword()  { return password; }
    public List<String> getInbox()     { return inbox; }

    // ---- Setters ----

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setLastName(String lastName)   { this.lastName  = lastName; }
    public void setEmail(String email)         { this.email     = email; }
    public void setPassword(String password)   { this.password  = password; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User u = (User) o;
        return userId.equals(u.userId);
    }

    @Override
    public int hashCode() {
        return userId.hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + userId + ", " + firstName + " " + lastName + "]";
    }
}

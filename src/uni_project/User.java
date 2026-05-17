package uni_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Abstract base class for every participant in the university system.
 *
 * <p>{@code User} captures the common identity and communication attributes
 * shared by all concrete user types (students, teachers, managers, admins,
 * research employees).  It is declared {@code abstract} because a raw
 * "user" without a role has no meaningful behaviour in this domain model.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li>Implements {@link Serializable} so that the entire user graph can be
 *       persisted via {@link University#saveData(String)}.</li>
 *   <li>A simple credential check ({@link #login}) is provided here; more
 *       sophisticated authentication can be layered on top without changing
 *       this class.</li>
 *   <li>The inbox is a plain {@code List<String>} — sufficient for the
 *       Observer-notification use-case; it is not a persistent messaging
 *       queue.</li>
 * </ul>
 *
 * @see Student
 * @see Employee
 * @see University
 * @author OOP Team
 * @version 1.0
 */
public abstract class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String       userId;
    private String       firstName;
    private String       lastName;
    private String       email;
    private String       password;
    private List<String> inbox;

    /**
     * Constructs a {@code User} with the supplied identity credentials.
     *
     * @param userId    unique identifier for this user (never {@code null})
     * @param firstName user's first (given) name
     * @param lastName  user's last (family) name
     * @param email     login e-mail address; must be unique across the system
     * @param password  plain-text password (storage and hashing are outside
     *                  the scope of this prototype)
     */
    public User(String userId, String firstName, String lastName,
                String email, String password) {
        this.userId     = userId;
        this.firstName  = firstName;
        this.lastName   = lastName;
        this.email      = email;
        this.password   = password;
        this.inbox      = new ArrayList<>();
    }

    /**
     * Validates the supplied credentials against this user's stored values.
     *
     * <p>Both the e-mail address and the password must match exactly
     * (case-sensitive comparison).</p>
     *
     * @param email    the e-mail address to check
     * @param password the password to check
     * @return {@code true} if both values match; {@code false} otherwise
     */
    public boolean login(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }

    /**
     * Prints all messages currently stored in this user's inbox to standard
     * output.
     *
     * <p>If the inbox is empty a placeholder line {@code "(empty)"} is
     * printed.  Messages are printed in the order they were received.</p>
     */
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

    /**
     * Appends a message to this user's inbox.
     *
     * <p>This method is the receiving end of the Observer pattern: when a
     * {@link Manager} calls {@link Manager#postNews(String)}, the notification
     * eventually reaches every subscribed {@link Student} via
     * {@link Student#update(String)}, which in turn calls this method.</p>
     *
     * @param message the message text to add (must not be {@code null})
     */
    public void receiveMessage(String message) {
        inbox.add(message);
    }

    // ---- Getters ----

    /**
     * Returns the unique identifier assigned to this user.
     *
     * @return the user's ID string
     */
    public String       getUserId()    { return userId; }

    /**
     * Returns the user's first (given) name.
     *
     * @return first name
     */
    public String       getFirstName() { return firstName; }

    /**
     * Returns the user's last (family) name.
     *
     * @return last name
     */
    public String       getLastName()  { return lastName; }

    /**
     * Returns the e-mail address used for login.
     *
     * @return e-mail address
     */
    public String       getEmail()     { return email; }

    /**
     * Returns the stored password (plain text in this prototype).
     *
     * @return password string
     */
    public String       getPassword()  { return password; }

    /**
     * Returns a live reference to this user's inbox list.
     *
     * @return mutable list of inbox messages; never {@code null}
     */
    public List<String> getInbox()     { return inbox; }

    // ---- Setters ----

    /**
     * Updates the user's first name.
     *
     * @param firstName new first name
     */
    public void setFirstName(String firstName) { this.firstName = firstName; }

    /**
     * Updates the user's last name.
     *
     * @param lastName new last name
     */
    public void setLastName(String lastName)   { this.lastName  = lastName; }

    /**
     * Updates the user's e-mail address.
     *
     * @param email new e-mail address
     */
    public void setEmail(String email)         { this.email     = email; }

    /**
     * Updates the user's password.
     *
     * @param password new password
     */
    public void setPassword(String password)   { this.password  = password; }

    /**
     * Returns a concise string representation of this user showing the
     * concrete class name, the user ID, and the full name.
     *
     * @return string in the form {@code ClassName[id=..., FirstName LastName]}
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        return userId.equals(((User) o).userId);
    }

    @Override
    public int hashCode() {
        return userId != null ? userId.hashCode() : 0;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName()
                + "[id=" + userId + ", " + firstName + " " + lastName + "]";
    }
}

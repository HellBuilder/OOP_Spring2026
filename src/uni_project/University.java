package uni_project;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Central registry and entry point for the entire university information
 * system.
 *
 * <p>{@code University} follows the <b>Singleton</b> design pattern: exactly
 * one instance exists for the lifetime of the application, accessible via
 * {@link #getInstance()}.  All users, courses, and researcher records are
 * owned by this single instance, ensuring a consistent, authoritative view
 * of the system state.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li><b>Thread safety</b> – lazy initialisation uses <em>double-checked
 *       locking</em> on a {@code volatile} field so that the singleton is
 *       both safely published and only initialised once, even in concurrent
 *       environments.</li>
 *   <li><b>Researcher registry</b> – whenever a {@link User} that also
 *       implements {@link Researcher} is added, they are automatically
 *       tracked in the separate {@code researchers} list, enabling efficient
 *       research analytics without iterating the full user list.</li>
 *   <li><b>Persistence</b> – the entire object graph is serialised via
 *       standard Java Object Serialization.  {@link #saveData(String)}
 *       writes the singleton to a file; {@link #loadData(String)} deserialises
 *       it and replaces the singleton reference, effectively restoring
 *       the full application state.</li>
 * </ul>
 *
 * @see User
 * @see Course
 * @see Researcher
 * @author OOP Team
 * @version 1.0
 */
public class University implements Serializable {

    private static final long serialVersionUID = 9L;

    /** The single, lazily initialised instance. */
    private static volatile University instance;

    private List<User>       users;
    private List<Course>     courses;
    private List<Researcher> researchers;

    /**
     * Private constructor — prevents direct instantiation from outside this
     * class.  Only {@link #getInstance()} should create the singleton.
     */
    private University() {
        users       = new ArrayList<>();
        courses     = new ArrayList<>();
        researchers = new ArrayList<>();
    }

    // ================================================================
    // Singleton access
    // ================================================================

    /**
     * Returns the singleton {@code University} instance, creating it lazily
     * on the first call.
     *
     * <p>Uses double-checked locking for thread-safe lazy initialisation:</p>
     * <pre>{@code
     * if (instance == null) {
     *     synchronized (University.class) {
     *         if (instance == null) {
     *             instance = new University();
     *         }
     *     }
     * }
     * }</pre>
     *
     * @return the single {@code University} instance; never {@code null}
     */
    public static University getInstance() {
        if (instance == null) {
            synchronized (University.class) {
                if (instance == null) {
                    instance = new University();
                }
            }
        }
        return instance;
    }

    // ================================================================
    // User management
    // ================================================================

    /**
     * Adds a user to the university registry.
     *
     * <p>If the user already exists in the registry (by object identity),
     * this method has no effect.  If the user also implements
     * {@link Researcher}, they are added to the researcher list as well.</p>
     *
     * @param user the {@link User} to register (must not be {@code null})
     */
    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            if (user instanceof Researcher) {
                researchers.add((Researcher) user);
            }
            System.out.println("[UNIVERSITY] Added " + user);
        }
    }

    /**
     * Removes a user from the university registry by their unique ID.
     *
     * <p>If the removed user is also a {@link Researcher}, they are removed
     * from the researcher list at the same time.  If no user with the given
     * ID exists, this method has no effect.</p>
     *
     * @param userId the unique ID of the user to remove
     */
    public void removeUser(String userId) {
        users.removeIf(u -> {
            if (u.getUserId().equals(userId)) {
                if (u instanceof Researcher) researchers.remove(u);
                System.out.println("[UNIVERSITY] Removed userId=" + userId);
                return true;
            }
            return false;
        });
    }

    /**
     * Searches the registry for a user with the specified ID.
     *
     * @param userId the unique ID to search for
     * @return the matching {@link User}, or {@code null} if no user with that
     *         ID exists
     */
    public User findUser(String userId) {
        return users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);
    }

    // ================================================================
    // Course management
    // ================================================================

    /**
     * Adds a course to the university's course catalogue.
     *
     * <p>Duplicate courses (by object identity) are silently ignored.</p>
     *
     * @param course the {@link Course} to add (must not be {@code null})
     */
    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            System.out.println("[UNIVERSITY] Course added: " + course);
        }
    }

    // ================================================================
    // Research analytics
    // ================================================================

    /**
     * Prints every unique research paper in the system, sorted by descending
     * citation count, to standard output.
     *
     * <p>Papers that appear in multiple researchers' publication lists are
     * deduplicated using {@link java.util.stream.Stream#distinct()} before
     * sorting.</p>
     */
    public void printAllPapers() {
        System.out.println("=== All Research Papers (by citations) ===");
        List<ResearchPaper> all = new ArrayList<>();
        for (Researcher r : researchers) all.addAll(r.getPapers());
        all.stream()
           .distinct()
           .sorted(ResearchPaper.BY_CITATIONS)
           .forEach(p -> System.out.println("  " + p));
    }

    /**
     * Finds and returns the researcher with the highest total citation count
     * across all their papers.
     *
     * <p>If two researchers have the same total citations, the one encountered
     * first in the researchers list is returned.  If the registry contains no
     * researchers, {@code null} is returned.</p>
     *
     * <p>The result is also printed to standard output with the winner's name,
     * total citations, and h-index.</p>
     *
     * @return the top-cited {@link Researcher}, or {@code null} if none exist
     */
    public Researcher getTopCitedResearcher() {
        Researcher top = null;
        int        max = -1;
        for (Researcher r : researchers) {
            int total = r.getPapers().stream()
                         .mapToInt(ResearchPaper::getCitations).sum();
            if (total > max) { max = total; top = r; }
        }
        if (top != null) {
            System.out.println("[TOP] " + ((User) top).getFirstName()
                    + " " + ((User) top).getLastName()
                    + " | total citations=" + max
                    + " | hIndex=" + top.getHIndex());
        }
        return top;
    }

    // ================================================================
    // Persistence (Java serialization)
    // ================================================================

    /**
     * Serialises the current singleton state to the given file.
     *
     * <p>The entire object graph reachable from this {@code University}
     * instance — including all users, courses, marks, papers, and projects —
     * is written as a single serialised object.  Any {@link java.io.IOException}
     * is caught and reported to standard error rather than propagated, to
     * keep the persistence layer non-fatal for demonstration purposes.</p>
     *
     * @param filename path to the output file (created or overwritten)
     */
    public void saveData(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("[SAVE] University state → " + filename);
        } catch (IOException e) {
            System.err.println("[SAVE ERROR] " + e.getMessage());
        }
    }

    /**
     * Deserialises university state from the given file and replaces the
     * current singleton instance.
     *
     * <p>After a successful load, all subsequent calls to {@link #getInstance()}
     * return the restored instance.  Errors (I/O failures or class-version
     * mismatches) are reported to standard error and the existing singleton
     * is left unchanged.</p>
     *
     * @param filename path to the previously saved data file
     */
    public static void loadData(String filename) {
        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(filename))) {
            instance = (University) ois.readObject();
            System.out.println("[LOAD] University state ← " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("[LOAD ERROR] " + e.getMessage());
        }
    }

    // ================================================================
    // Getters
    // ================================================================

    /**
     * Returns the live list of all registered users.
     *
     * @return mutable list of {@link User} objects; never {@code null}
     */
    public List<User>       getUsers()       { return users; }

    /**
     * Returns the live list of all courses in the catalogue.
     *
     * @return mutable list of {@link Course} objects; never {@code null}
     */
    public List<Course>     getCourses()     { return courses; }

    /**
     * Returns the live list of all users who are also researchers.
     *
     * @return mutable list of {@link Researcher} objects; never {@code null}
     */
    public List<Researcher> getResearchers() { return researchers; }
}

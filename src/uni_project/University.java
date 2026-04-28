package uni_project;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Singleton — one University object governs the entire system.
 * Double-checked locking for thread safety.
 */
public class University implements Serializable {

    private static final long serialVersionUID = 9L;

    private static volatile University instance;

    private List<User>       users;
    private List<Course>     courses;
    private List<Researcher> researchers;

    private University() {
        users       = new ArrayList<>();
        courses     = new ArrayList<>();
        researchers = new ArrayList<>();
    }

    // ================================================================
    // Singleton access
    // ================================================================

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

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
            if (user instanceof Researcher) {
                researchers.add((Researcher) user);
            }
            System.out.println("[UNIVERSITY] Added " + user);
        }
    }

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

    public User findUser(String userId) {
        return users.stream()
                    .filter(u -> u.getUserId().equals(userId))
                    .findFirst()
                    .orElse(null);
    }

    // ================================================================
    // Course management
    // ================================================================

    public void addCourse(Course course) {
        if (!courses.contains(course)) {
            courses.add(course);
            System.out.println("[UNIVERSITY] Course added: " + course);
        }
    }

    // ================================================================
    // Research analytics
    // ================================================================

    public void printAllPapers(Comparator<ResearchPaper> c) {
        System.out.println("=== All Research Papers ===");
        List<ResearchPaper> all = new ArrayList<>();
        for (Researcher r : researchers) all.addAll(r.getPapers());
        all.stream()
           .distinct()
           .sorted(c)
           .forEach(p -> System.out.println("  " + p));
    }

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

    public void saveData(String filename) {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(filename))) {
            oos.writeObject(this);
            System.out.println("[SAVE] University state → " + filename);
        } catch (IOException e) {
            System.err.println("[SAVE ERROR] " + e.getMessage());
        }
    }

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

    public List<User>       getUsers()       { return users; }
    public List<Course>     getCourses()     { return courses; }
    public List<Researcher> getResearchers() { return researchers; }
}

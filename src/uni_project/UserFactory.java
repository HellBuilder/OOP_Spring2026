package uni_project;

/**
 * Factory — centralizes object creation so callers never invoke constructors directly.
 * New roles only require a new case here, not changes to calling code.
 */
public class UserFactory {

    private UserFactory() {}

    /**
     * Creates a User by role name.
     *
     * Role       | Required extra args (in order)
     * -----------|-----------------------------------------------
     * STUDENT    | (String) studentId, (int) year, (Major) major
     * TEACHER    | (String) employeeId, (String) dept, (double) salary, (TeacherTitle) title
     * MANAGER    | (String) employeeId, (String) dept, (double) salary, (ManagerType) type
     * ADMIN      | (String) employeeId, (String) dept, (double) salary, (String) logFile
     * RESEARCHER | (String) employeeId, (String) dept, (double) salary
     */
    public static User createUser(String role,
                                  String userId,
                                  String firstName,
                                  String lastName,
                                  String email,
                                  String password,
                                  Object... extra) {

        switch (role.toUpperCase()) {

            case "STUDENT":
                return new Student(
                        userId, firstName, lastName, email, password,
                        (String) extra[0],
                        (int)    extra[1],
                        (Major)  extra[2]);

            case "TEACHER":
                return new Teacher(
                        userId, firstName, lastName, email, password,
                        (String)       extra[0],
                        (String)       extra[1],
                        (double)       extra[2],
                        (TeacherTitle) extra[3]);

            case "MANAGER":
                return new Manager(
                        userId, firstName, lastName, email, password,
                        (String)      extra[0],
                        (String)      extra[1],
                        (double)      extra[2],
                        (ManagerType) extra[3]);

            case "ADMIN":
                return new Admin(
                        userId, firstName, lastName, email, password,
                        (String) extra[0],
                        (String) extra[1],
                        (double) extra[2],
                        (String) extra[3]);

            case "RESEARCHER":
                return new ResearchEmployee(
                        userId, firstName, lastName, email, password,
                        (String) extra[0],
                        (String) extra[1],
                        (double) extra[2]);

            default:
                throw new IllegalArgumentException("Unknown role: " + role);
        }
    }
}

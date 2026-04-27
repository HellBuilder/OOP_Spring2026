package uni_project;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        University uni = University.getInstance();

        // ============================================================
        // 1. FACTORY — create all users
        // ============================================================
        System.out.println("\n====== 1. USER FACTORY ======");

        Teacher drSmith = (Teacher) UserFactory.createUser(
                "TEACHER", "T001", "John", "Smith", "smith@uni.edu", "pass1",
                "E001", "Computer Science", 90_000.0, TeacherTitle.PROFESSOR);

        Teacher msBrown = (Teacher) UserFactory.createUser(
                "TEACHER", "T002", "Alice", "Brown", "brown@uni.edu", "pass2",
                "E002", "Computer Science", 75_000.0, TeacherTitle.ASSOCIATE_PROFESSOR);

        ResearchEmployee drKhan = (ResearchEmployee) UserFactory.createUser(
                "RESEARCHER", "R001", "Amir", "Khan", "khan@uni.edu", "pass3",
                "E003", "Research Lab", 80_000.0);

        Student alice = (Student) UserFactory.createUser(
                "STUDENT", "S001", "Alice", "Johnson", "alice@uni.edu", "pass4",
                "20-001", 2, Major.CS);

        Student bob = (Student) UserFactory.createUser(
                "STUDENT", "S002", "Bob", "Lee", "bob@uni.edu", "pass5",
                "20-002", 2, Major.CS);

        ResearchStudent carol = new ResearchStudent(
                "S003", "Carol", "Zhao", "carol@uni.edu", "pass6",
                "20-003", 3, Major.SE);

        Manager dean = (Manager) UserFactory.createUser(
                "MANAGER", "M001", "Dean", "Wilson", "dean@uni.edu", "pass7",
                "E004", "Administration", 100_000.0, ManagerType.DEAN);

        Admin sysAdmin = (Admin) UserFactory.createUser(
                "ADMIN", "A001", "Eve", "Admin", "admin@uni.edu", "pass8",
                "E005", "IT", 70_000.0, "system.log");

        // ============================================================
        // 2. UNIVERSITY REGISTRATION (via Admin)
        // ============================================================
        System.out.println("\n====== 2. ADMIN ADDS USERS ======");
        sysAdmin.addUser(drSmith);
        sysAdmin.addUser(msBrown);
        sysAdmin.addUser(drKhan);
        sysAdmin.addUser(alice);
        sysAdmin.addUser(bob);
        sysAdmin.addUser(carol);
        sysAdmin.addUser(dean);

        // ============================================================
        // 3. COURSES
        // ============================================================
        System.out.println("\n====== 3. COURSES ======");
        Course oop     = new Course("CS101", "OOP",          6, Major.CS, 2);
        Course algo    = new Course("CS102", "Algorithms",   5, Major.CS, 2);
        Course thesis  = new Course("SE401", "Thesis",       3, Major.SE, 4);

        uni.addCourse(oop);
        uni.addCourse(algo);
        uni.addCourse(thesis);

        // ============================================================
        // 4. MANAGER — assign teachers & approve registrations
        // ============================================================
        System.out.println("\n====== 4. MANAGER ASSIGNS TEACHERS ======");
        dean.assignTeacher(drSmith, oop);
        dean.assignTeacher(msBrown, algo);

        // ============================================================
        // 5. OBSERVER PATTERN — students subscribe to dean's news feed
        // ============================================================
        System.out.println("\n====== 5. OBSERVER PATTERN ======");
        dean.addObserver(alice);
        dean.addObserver(bob);
        dean.addObserver(carol);
        dean.postNews("Midterm exams begin on May 5th.");
        dean.postNews("Library will be closed on Monday.");

        // ============================================================
        // 6. STUDENT REGISTRATION
        // ============================================================
        System.out.println("\n====== 6. STUDENT REGISTRATION ======");
        try {
            dean.approveRegistration(alice, oop);
            alice.registerForCourse(oop);
            alice.registerForCourse(algo);

            dean.approveRegistration(bob, oop);
            bob.registerForCourse(oop);

            carol.registerForCourse(thesis);
        } catch (MaxCreditsExceededException | MaxFailsExceededException e) {
            System.out.println("[EXCEPTION] " + e.getMessage());
        }

        // Test credit overflow
        System.out.println("-- Testing credit overflow --");
        try {
            Course extra1 = new Course("CS200", "Networks",     5, Major.CS, 2);
            Course extra2 = new Course("CS201", "OS",           5, Major.CS, 2);
            Course extra3 = new Course("CS202", "Compilers",    5, Major.CS, 3);
            alice.registerForCourse(extra1); // total = 16
            alice.registerForCourse(extra2); // total = 21
            alice.registerForCourse(extra3); // 21+5=26 > 21 → throws
        } catch (MaxCreditsExceededException e) {
            System.out.println("[CAUGHT MaxCreditsExceededException] " + e.getMessage());
        } catch (MaxFailsExceededException e) {
            System.out.println("[EXCEPTION] " + e.getMessage());
        }

        // ============================================================
        // 7. GRADING
        // ============================================================
        System.out.println("\n====== 7. GRADING ======");
        drSmith.putMark(alice, oop, 25.0, 24.0, 35.0);  // 84 PASS
        drSmith.putMark(bob,   oop, 10.0,  8.0, 15.0);  // 33 FAIL
        msBrown.putMark(alice, algo, 28.0, 27.0, 38.0); // 93 PASS

        alice.viewMarks();
        bob.viewMarks();

        // ============================================================
        // 8. TRANSCRIPT
        // ============================================================
        System.out.println("\n====== 8. TRANSCRIPT ======");
        alice.getTranscript().print();

        // ============================================================
        // 9. TEACHER RATING
        // ============================================================
        System.out.println("\n====== 9. TEACHER RATING ======");
        alice.rateTeacher(drSmith, 4.5);
        alice.rateTeacher(msBrown, 3.8);
        bob.rateTeacher(drSmith, 5.0);
        System.out.printf("  drSmith avg rating: %.2f%n", drSmith.getRating());

        // ============================================================
        // 10. RESEARCH PAPERS — h-index calculation
        // ============================================================
        System.out.println("\n====== 10. RESEARCH PAPERS ======");

        ResearchPaper p1 = new ResearchPaper(
                "Deep Learning in NLP",
                Arrays.asList(drSmith, drKhan),
                "IEEE Transactions", LocalDate.of(2020, 3, 15),
                "10.1109/TNN.2020.001", 120, 14);

        ResearchPaper p2 = new ResearchPaper(
                "Graph Algorithms Revisited",
                List.of(drSmith),
                "ACM SIGALG", LocalDate.of(2021, 7, 22),
                "10.1145/ACM.2021.002", 85, 10);

        ResearchPaper p3 = new ResearchPaper(
                "Quantum Computing Primer",
                List.of(drKhan),
                "Nature CS", LocalDate.of(2019, 11, 1),
                "10.1038/NCS.2019.003", 200, 22);

        ResearchPaper p4 = new ResearchPaper(
                "OOP Patterns in Modern Java",
                Arrays.asList(drSmith, carol),
                "J. Software Eng.", LocalDate.of(2022, 1, 10),
                "10.1234/JSE.2022.004", 45, 8);

        ResearchPaper p5 = new ResearchPaper(
                "Security in Distributed Systems",
                List.of(drSmith),
                "IEEE S&P", LocalDate.of(2023, 5, 5),
                "10.1109/SP.2023.005", 30, 12);

        drSmith.addPaper(p1);
        drSmith.addPaper(p2);
        drSmith.addPaper(p4);
        drSmith.addPaper(p5);
        drKhan.addPaper(p1);
        drKhan.addPaper(p3);
        carol.addPaper(p4);

        drSmith.printPapers();
        drKhan.printPapers();
        carol.printPapers();

        System.out.println("drSmith hIndex = " + drSmith.getHIndex());
        System.out.println("drKhan  hIndex = " + drKhan.getHIndex());
        System.out.println("carol   hIndex = " + carol.getHIndex());

        // ============================================================
        // 11. SUPERVISOR ASSIGNMENT
        // ============================================================
        System.out.println("\n====== 11. SUPERVISOR ASSIGNMENT ======");
        try {
            // carol has hIndex=1 — too low
            alice.setSupervisor(carol);
        } catch (LowHIndexException e) {
            System.out.println("[CAUGHT LowHIndexException] " + e.getMessage());
        }
        try {
            // drSmith has hIndex >= 3 — OK
            alice.setSupervisor(drSmith);
            carol.setSupervisor(drKhan);
        } catch (LowHIndexException e) {
            System.out.println("[EXCEPTION] " + e.getMessage());
        }

        // ============================================================
        // 12. RESEARCH PROJECT
        // ============================================================
        System.out.println("\n====== 12. RESEARCH PROJECT ======");
        ResearchProject project = new ResearchProject("PROJ-01", "AI for Education");

        try {
            project.addParticipant(drSmith);   // Teacher implements Researcher — OK
            project.addParticipant(drKhan);    // ResearchEmployee — OK
            project.addParticipant(carol);     // ResearchStudent — OK
            project.addParticipant(bob);       // plain Student — THROWS
        } catch (NotResearcherException e) {
            System.out.println("[CAUGHT NotResearcherException] " + e.getMessage());
        }

        project.publishPaper(p1);
        project.publishPaper(p4);
        project.printSummary();

        // ============================================================
        // 13. COMPARATOR SORTING
        // ============================================================
        System.out.println("\n====== 13. COMPARATOR DEMOS ======");
        List<ResearchPaper> allPapers = Arrays.asList(p1, p2, p3, p4, p5);

        System.out.println("-- Sorted BY_DATE (oldest first) --");
        allPapers.stream().sorted(ResearchPaper.BY_DATE)
                 .forEach(p -> System.out.printf("  %d  %s%n", p.getDate().getYear(), p.getTitle()));

        System.out.println("-- Sorted BY_CITATIONS (most cited first) --");
        allPapers.stream().sorted(ResearchPaper.BY_CITATIONS)
                 .forEach(p -> System.out.printf("  %3d  %s%n", p.getCitations(), p.getTitle()));

        System.out.println("-- Sorted BY_PAGES (fewest pages first) --");
        allPapers.stream().sorted(ResearchPaper.BY_PAGES)
                 .forEach(p -> System.out.printf("  %2d p  %s%n", p.getPages(), p.getTitle()));

        System.out.println("-- Natural order via Comparable (most cited first) --");
        allPapers.stream().sorted()
                 .forEach(p -> System.out.printf("  %3d  %s%n", p.getCitations(), p.getTitle()));

        // ============================================================
        // 14. UNIVERSITY ANALYTICS
        // ============================================================
        System.out.println("\n====== 14. UNIVERSITY ANALYTICS ======");
        uni.printAllPapers();
        System.out.println("Top cited researcher:");
        uni.getTopCitedResearcher();

        // ============================================================
        // 15. INBOX & LOGIN
        // ============================================================
        System.out.println("\n====== 15. INBOX & LOGIN ======");
        alice.viewInbox();
        System.out.println("alice login OK? " + alice.login("alice@uni.edu", "pass4"));
        System.out.println("alice login OK? " + alice.login("alice@uni.edu", "wrong"));

        // ============================================================
        // 16. ADMIN LOGS
        // ============================================================
        System.out.println("\n====== 16. ADMIN LOGS ======");
        sysAdmin.updateUser(alice, "email", "alice.new@uni.edu");
        sysAdmin.viewLogs();

        // ============================================================
        // 17. MAX FAILS TEST
        // ============================================================
        System.out.println("\n====== 17. MAX FAILS TEST ======");
        Student failStudent = new Student(
                "S999", "Fail", "Test", "fail@uni.edu", "pw",
                "99-001", 1, Major.IT);
        try {
            Course c1 = new Course("F1","Fail1",3,Major.IT,1);
            Course c2 = new Course("F2","Fail2",3,Major.IT,1);
            Course c3 = new Course("F3","Fail3",3,Major.IT,1);
            Course c4 = new Course("F4","Fail4",3,Major.IT,1);

            failStudent.registerForCourse(c1);
            failStudent.registerForCourse(c2);
            failStudent.registerForCourse(c3);

            // Simulate 3 failures
            failStudent.addMark(c1, new Mark(5, 5, 5));
            failStudent.addMark(c2, new Mark(5, 5, 5));
            failStudent.addMark(c3, new Mark(5, 5, 5));

            failStudent.registerForCourse(c4); // failCount == 3 → throws
        } catch (MaxFailsExceededException e) {
            System.out.println("[CAUGHT MaxFailsExceededException] " + e.getMessage());
        } catch (MaxCreditsExceededException e) {
            System.out.println("[EXCEPTION] " + e.getMessage());
        }

        System.out.println("\n====== DONE ======");
    }
}

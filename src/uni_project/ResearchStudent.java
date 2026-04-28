package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A Student who also conducts research.
 * Models the "Student (optional Researcher)" requirement.
 */
public class ResearchStudent extends Student implements Researcher {

    private static final long serialVersionUID = 4L;

    private int                  hIndex;
    private List<ResearchPaper>  papers;
    private List<ResearchProject> projects;

    public ResearchStudent(String userId, String firstName, String lastName,
                           String email, String password,
                           String studentId, int year, Major major) {
        super(userId, firstName, lastName, email, password, studentId, year, major);
        this.hIndex   = 0;
        this.papers   = new ArrayList<>();
        this.projects = new ArrayList<>();
    }

    // ================================================================
    // Researcher
    // ================================================================

    @Override
    public int getHIndex() { return hIndex; }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public List<ResearchProject> getProjects() { return projects; }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        hIndex = ResearcherUtils.computeHIndex(papers);
    }

    @Override
    public void printPapers(Comparator<ResearchPaper> c) {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName() + " ===");
        papers.stream()
              .sorted(c)
              .forEach(p -> System.out.println("  • " + p));
    }
}

package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ResearchEmployee extends Employee implements Researcher {

    private static final long serialVersionUID = 8L;

    private int                  hIndex;
    private List<ResearchPaper>  papers;
    private List<ResearchProject> projects;

    public ResearchEmployee(String userId, String firstName, String lastName,
                            String email, String password,
                            String employeeId, String department, double salary) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
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
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(c)
              .forEach(p -> System.out.println("  • " + p));
    }
}

package uni_project;

import java.util.ArrayList;
import java.util.List;

public class ResearchEmployee extends Employee implements Researcher {

    private static final long serialVersionUID = 8L;

    private int                  hIndex;
    private List<ResearchPaper>  papers;

    public ResearchEmployee(String userId, String firstName, String lastName,
                            String email, String password,
                            String employeeId, String department, double salary) {
        super(userId, firstName, lastName, email, password, employeeId, department, salary);
        this.hIndex = 0;
        this.papers = new ArrayList<>();
    }

    @Override
    public int getHIndex() { return hIndex; }

    @Override
    public List<ResearchPaper> getPapers() { return papers; }

    @Override
    public void addPaper(ResearchPaper paper) {
        papers.add(paper);
        hIndex = ResearcherUtils.computeHIndex(papers);
    }

    @Override
    public void printPapers() {
        System.out.println("=== Papers: " + getFirstName() + " " + getLastName()
                + " (hIndex=" + hIndex + ") ===");
        papers.stream()
              .sorted(ResearchPaper.BY_CITATIONS)
              .forEach(p -> System.out.println("  * " + p));
    }
}

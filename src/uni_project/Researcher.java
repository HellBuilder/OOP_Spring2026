package uni_project;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int getHIndex();
    List<ResearchPaper> getPapers();
    List<ResearchProject> getProjects();
    void addPaper(ResearchPaper paper);
    void printPapers(Comparator<ResearchPaper> c);
}

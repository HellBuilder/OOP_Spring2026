package uni_project;

import java.util.List;

public interface Researcher {
    int getHIndex();
    List<ResearchPaper> getPapers();
    void addPaper(ResearchPaper paper);
    void printPapers();
}

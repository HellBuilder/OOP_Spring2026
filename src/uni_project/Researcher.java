package uni_project;

import java.util.Comparator;
import java.util.List;

public interface Researcher {
    int getHIndex();
    List<ResearchPaper> getPapers();
    void addPaper(ResearchPaper paper);
    void printPapers();

    /** Prints papers sorted by the given comparator. */
    default void printPapers(Comparator<ResearchPaper> c) {
        getPapers().stream().sorted(c)
                   .forEach(p -> System.out.println("  • " + p));
    }
}

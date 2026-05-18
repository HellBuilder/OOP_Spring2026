package uni_project;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

final class ResearcherUtils {

    private ResearcherUtils() {}

    static int computeHIndex(List<ResearchPaper> papers) {
        List<Integer> citations = new ArrayList<>(papers.size());
        for (ResearchPaper p : papers) {
            citations.add(p.getCitations());
        }
        citations.sort(Comparator.reverseOrder());
        int h = 0;
        for (int i = 0; i < citations.size(); i++) {
            if (citations.get(i) >= i + 1) {
                h = i + 1;
            } else {
                break;
            }
        }
        return h;
    }
}

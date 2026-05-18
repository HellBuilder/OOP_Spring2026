package uni_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {

    private static final long serialVersionUID = 13L;

    private String              projectId;
    private String              title;
    private List<Researcher>    participants;
    private List<ResearchPaper> publishedPapers;

    public ResearchProject(String projectId, String title) {
        this.projectId       = projectId;
        this.title           = title;
        this.participants    = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    public void addParticipant(Object person) throws NotResearcherException {
        if (!(person instanceof Researcher)) {
            throw new NotResearcherException(
                    person.getClass().getSimpleName()
                    + " does not implement Researcher and cannot join project '"
                    + title + "'.");
        }
        Researcher r = (Researcher) person;
        if (!participants.contains(r)) {
            participants.add(r);
            System.out.println("[PROJECT] '" + title + "' << "
                    + person.getClass().getSimpleName());
        }
    }

    public void publishPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
        for (Researcher r : participants) {
            if (!r.getPapers().contains(paper)) {
                r.addPaper(paper);
            }
        }
        System.out.println("[PUBLISH] '" + paper.getTitle()
                + "' added to project '" + title + "'.");
    }

    public void printSummary() {
        System.out.println("=== Research Project: " + title + " ===");
        System.out.println("  Participants : " + participants.size());
        System.out.println("  Papers       : " + publishedPapers.size());
        publishedPapers.stream()
                       .sorted(ResearchPaper.BY_CITATIONS)
                       .forEach(p -> System.out.println("    * " + p));
    }

    public String              getProjectId()       { return projectId; }
    public String              getTitle()           { return title; }
    public List<Researcher>    getParticipants()    { return participants; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }
}

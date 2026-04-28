package uni_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ResearchProject implements Serializable {

    private static final long serialVersionUID = 13L;

    private String             projectId;
    private String             topic;
    private List<Researcher>   participants;
    private List<ResearchPaper> publishedPapers;

    public ResearchProject(String projectId, String topic) {
        this.projectId      = projectId;
        this.topic          = topic;
        this.participants   = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    /**
     * Only objects implementing Researcher may join.
     * Throws NotResearcherException otherwise — enforcing the contract at runtime.
     */
    public void addParticipant(Object person) throws NotResearcherException {
        if (!(person instanceof Researcher)) {
            throw new NotResearcherException(
                    person.getClass().getSimpleName()
                    + " does not implement Researcher and cannot join project '"
                    + topic + "'.");
        }
        Researcher r = (Researcher) person;
        if (!participants.contains(r)) {
            participants.add(r);
            System.out.println("[PROJECT] '" + topic + "' << "
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
                + "' added to project '" + topic + "'.");
    }

    public void printSummary() {
        System.out.println("=== Research Project: " + topic + " ===");
        System.out.println("  Participants : " + participants.size());
        System.out.println("  Papers       : " + publishedPapers.size());
        publishedPapers.stream()
                       .sorted(ResearchPaper.BY_CITATIONS)
                       .forEach(p -> System.out.println("    • " + p));
    }

    // ---- Getters ----

    public String              getProjectId()      { return projectId; }
    public String              getTopic()          { return topic; }
    public List<Researcher>    getParticipants()   { return participants; }
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }
}

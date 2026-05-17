package uni_project;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a collaborative research project that groups researchers and
 * the papers they collectively produce.
 *
 * <p>A {@code ResearchProject} acts as the central coordination point for
 * multi-author publications: researchers are added as participants, and any
 * paper published through the project is automatically credited to every
 * participant who does not already list it.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li><b>Runtime type safety</b> – {@link #addParticipant(Object)} accepts
 *       {@code Object} rather than {@link Researcher} to demonstrate a
 *       runtime {@code instanceof} guard.  Passing a non-researcher (e.g., a
 *       plain {@link Student}) throws {@link NotResearcherException}, clearly
 *       communicating the contract violation.</li>
 *   <li><b>Credit distribution</b> – {@link #publishPaper(ResearchPaper)}
 *       iterates over all participants and calls
 *       {@link Researcher#addPaper(ResearchPaper)} on each, which in turn
 *       triggers h-index recalculation for every involved researcher.</li>
 *   <li><b>Serializable</b> – consistent with the rest of the domain model,
 *       allowing projects to be persisted as part of the
 *       {@link University} state.</li>
 * </ul>
 *
 * @see Researcher
 * @see ResearchPaper
 * @see NotResearcherException
 * @author OOP Team
 * @version 1.0
 */
public class ResearchProject implements Serializable {

    private static final long serialVersionUID = 13L;

    private String             projectId;
    private String             title;
    private List<Researcher>   participants;
    private List<ResearchPaper> publishedPapers;

    /**
     * Constructs a new, empty {@code ResearchProject} with the given
     * identifier and title.
     *
     * @param projectId unique identifier for this project (e.g., "RP-2026-01")
     * @param title     human-readable project title (must not be {@code null})
     */
    public ResearchProject(String projectId, String title) {
        this.projectId      = projectId;
        this.title          = title;
        this.participants   = new ArrayList<>();
        this.publishedPapers = new ArrayList<>();
    }

    /**
     * Adds a person to this project's participant list after verifying that
     * the person implements the {@link Researcher} interface.
     *
     * <p>Only objects implementing {@link Researcher} may join.  This rule is
     * enforced at runtime rather than via the parameter type so that the
     * method can be used in generic "add any user" call-sites while still
     * rejecting non-researchers with a descriptive exception message.
     * Duplicate participants are silently ignored.</p>
     *
     * @param person any object that is expected to be a {@link Researcher}
     * @throws NotResearcherException if {@code person} does not implement
     *                                {@link Researcher}
     */
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

    /**
     * Publishes a {@link ResearchPaper} under this project and credits it to
     * every current participant.
     *
     * <p>The paper is added to this project's {@link #getPublishedPapers()}
     * list.  For each participant who does not already have the paper in their
     * own publication list, {@link Researcher#addPaper(ResearchPaper)} is
     * called, which also updates the participant's h-index.</p>
     *
     * @param paper the {@link ResearchPaper} to publish (must not be
     *              {@code null})
     */
    public void publishPaper(ResearchPaper paper) {
        publishedPapers.add(paper);
        // Credit the paper to all participant researchers
        for (Researcher r : participants) {
            if (!r.getPapers().contains(paper)) {
                r.addPaper(paper);
            }
        }
        System.out.println("[PUBLISH] '" + paper.getTitle()
                + "' added to project '" + title + "'.");
    }

    /**
     * Prints a summary of this project to standard output.
     *
     * <p>The summary includes the project title, the number of participants,
     * the total number of published papers, and a list of papers sorted by
     * descending citation count.</p>
     */
    public void printSummary() {
        System.out.println("=== Research Project: " + title + " ===");
        System.out.println("  Participants : " + participants.size());
        System.out.println("  Papers       : " + publishedPapers.size());
        publishedPapers.stream()
                       .sorted(ResearchPaper.BY_CITATIONS)
                       .forEach(p -> System.out.println("    • " + p));
    }

    // ---- Getters ----

    /**
     * Returns the unique identifier of this research project.
     *
     * @return project ID string
     */
    public String              getProjectId()       { return projectId; }

    /**
     * Returns the title of this research project.
     *
     * @return project title string
     */
    public String              getTitle()           { return title; }

    /**
     * Returns the live list of researchers participating in this project.
     *
     * @return mutable list of {@link Researcher} objects; never {@code null}
     */
    public List<Researcher>    getParticipants()    { return participants; }

    /**
     * Returns the live list of papers that have been published through this
     * project.
     *
     * @return mutable list of {@link ResearchPaper} objects; never
     *         {@code null}
     */
    public List<ResearchPaper> getPublishedPapers() { return publishedPapers; }
}

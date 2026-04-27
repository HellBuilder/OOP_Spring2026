package uni_project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {

    private static final long serialVersionUID = 12L;

    private String           title;
    private List<Researcher> authors;
    private String           journal;
    private LocalDate        date;
    private String           doi;
    private int              citations;
    private int              pages;

    public ResearchPaper(String title, List<Researcher> authors, String journal,
                         LocalDate date, String doi, int citations, int pages) {
        this.title     = title;
        this.authors   = authors;
        this.journal   = journal;
        this.date      = date;
        this.doi       = doi;
        this.citations = citations;
        this.pages     = pages;
    }

    // ---- Natural ordering: most cited first ----

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    // ---- Named Comparators ----

    /** Ascending by publication date (oldest first). */
    public static final Comparator<ResearchPaper> BY_DATE =
            Comparator.comparing(ResearchPaper::getDate);

    /** Descending by citation count (most cited first). */
    public static final Comparator<ResearchPaper> BY_CITATIONS =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    /** Ascending by page count (shortest first). */
    public static final Comparator<ResearchPaper> BY_PAGES =
            Comparator.comparingInt(ResearchPaper::getPages);

    // ---- Getters ----

    public String           getTitle()     { return title; }
    public List<Researcher> getAuthors()   { return authors; }
    public String           getJournal()   { return journal; }
    public LocalDate        getDate()      { return date; }
    public String           getDoi()       { return doi; }
    public int              getCitations() { return citations; }
    public int              getPages()     { return pages; }

    public void setCitations(int citations) { this.citations = citations; }

    @Override
    public String toString() {
        return "\"" + title + "\" [" + journal + ", " + date.getYear()
                + ", citations=" + citations + ", pages=" + pages + "]";
    }
}

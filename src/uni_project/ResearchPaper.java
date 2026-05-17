package uni_project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Represents an academic research paper published by one or more researchers.
 *
 * <p>A {@code ResearchPaper} records bibliographic metadata (title, journal,
 * publication date, DOI, page count) together with a citation count used to
 * compute h-indices and rank papers.</p>
 *
 * <p><b>Design notes:</b></p>
 * <ul>
 *   <li><b>Builder pattern</b> – the public constructor accepts seven
 *       arguments which, while complete, are tedious to use at call sites.
 *       The inner {@link Builder} class allows incremental, readable
 *       construction with fluent method chaining and validates that the three
 *       mandatory fields ({@code title}, {@code journal}, {@code date}) are
 *       present before building.</li>
 *   <li><b>Natural ordering</b> – {@link #compareTo(ResearchPaper)} orders
 *       papers by descending citation count (most-cited first), which is the
 *       most common ranking used in research analytics.</li>
 *   <li><b>Named comparators</b> – {@link #BY_DATE}, {@link #BY_CITATIONS},
 *       and {@link #BY_PAGES} are pre-built {@link Comparator} constants for
 *       common alternative orderings, avoiding repeated lambda definitions at
 *       call sites.</li>
 *   <li><b>Serializable</b> – required so that the paper list survives a
 *       {@link University#saveData(String)} / {@link University#loadData(String)}
 *       round-trip.</li>
 * </ul>
 *
 * @see Researcher
 * @see ResearchProject
 * @see University#printAllPapers()
 * @author OOP Team
 * @version 1.0
 */
public class ResearchPaper implements Comparable<ResearchPaper>, Serializable {

    private static final long serialVersionUID = 12L;

    private String           title;
    private List<Researcher> authors;
    private String           journal;
    private LocalDate        date;
    private String           doi;
    private int              citations;
    private int              pages;

    /**
     * Constructs a fully specified {@code ResearchPaper}.
     *
     * <p>Prefer using the {@link Builder} for readable, self-documenting
     * construction at call sites.</p>
     *
     * @param title     paper title (must not be {@code null})
     * @param authors   list of contributing {@link Researcher} objects
     * @param journal   name of the publishing journal or conference
     * @param date      publication date
     * @param doi       Digital Object Identifier string (may be empty but not
     *                  {@code null})
     * @param citations number of citations at the time of creation
     * @param pages     total page count of the paper
     */
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

    /**
     * Compares this paper to another by citation count in descending order.
     *
     * <p>This natural ordering means that a paper with more citations is
     * considered "less than" (i.e., comes before) a paper with fewer
     * citations when sorted, placing the most-cited papers at the front of
     * a sorted collection.</p>
     *
     * @param other the paper to compare against (must not be {@code null})
     * @return a negative integer if this paper has more citations than
     *         {@code other}, zero if equal, positive if fewer
     */
    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    // ---- Named Comparators ----

    /**
     * Comparator that orders papers ascending by publication date
     * (oldest paper first).
     */
    public static final Comparator<ResearchPaper> BY_DATE =
            Comparator.comparing(ResearchPaper::getDate);

    /**
     * Comparator that orders papers descending by citation count
     * (most-cited paper first).
     */
    public static final Comparator<ResearchPaper> BY_CITATIONS =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    /**
     * Comparator that orders papers ascending by page count
     * (shortest paper first).
     */
    public static final Comparator<ResearchPaper> BY_PAGES =
            Comparator.comparingInt(ResearchPaper::getPages);

    // ---- Getters ----

    /**
     * Returns the title of this research paper.
     *
     * @return title string
     */
    public String           getTitle()     { return title; }

    /**
     * Returns the list of researchers who authored this paper.
     *
     * @return mutable list of {@link Researcher} objects; never {@code null}
     */
    public List<Researcher> getAuthors()   { return authors; }

    /**
     * Returns the name of the journal or conference in which this paper was
     * published.
     *
     * @return journal name string
     */
    public String           getJournal()   { return journal; }

    /**
     * Returns the publication date of this paper.
     *
     * @return {@link LocalDate} of publication
     */
    public LocalDate        getDate()      { return date; }

    /**
     * Returns the Digital Object Identifier for this paper.
     *
     * @return DOI string, or an empty string if not set
     */
    public String           getDoi()       { return doi; }

    /**
     * Returns the number of citations this paper has received.
     *
     * @return citation count (non-negative)
     */
    public int              getCitations() { return citations; }

    /**
     * Returns the page count of this paper.
     *
     * @return page count (positive)
     */
    public int              getPages()     { return pages; }

    /**
     * Updates the citation count for this paper.
     *
     * <p>Called when citation data is refreshed from an external source.
     * Note that updating citations here does <em>not</em> automatically
     * recompute the h-index of any author; the relevant
     * {@link Researcher#addPaper(ResearchPaper)} must be re-invoked if
     * h-index recalculation is desired.</p>
     *
     * @param citations new citation count (must be non-negative)
     */
    public void setCitations(int citations) { this.citations = citations; }

    /**
     * Returns a compact string representation suitable for console output.
     *
     * @return string in the form
     *         {@code "Title" [Journal, YYYY, citations=N, pages=N]}
     */
    @Override
    public String toString() {
        return "\"" + title + "\" [" + journal + ", " + date.getYear()
                + ", citations=" + citations + ", pages=" + pages + "]";
    }

    // ================================================================
    // Builder Pattern — avoids 7-argument constructor call sites
    // ================================================================

    /**
     * Fluent builder for {@link ResearchPaper}.
     *
     * <p>Usage example:</p>
     * <pre>{@code
     * ResearchPaper paper = new ResearchPaper.Builder()
     *         .title("Deep Learning Survey")
     *         .journal("IEEE Transactions")
     *         .date(LocalDate.of(2023, 6, 15))
     *         .authors(List.of(prof1, prof2))
     *         .citations(120)
     *         .pages(14)
     *         .doi("10.1109/xyz")
     *         .build();
     * }</pre>
     *
     * <p>The fields {@code title}, {@code journal}, and {@code date} are
     * mandatory; calling {@link #build()} without them throws
     * {@link IllegalStateException}.  All other fields have sensible
     * defaults ({@code doi = ""}, {@code citations = 0},
     * {@code pages = 0}, {@code authors = []} ).</p>
     */
    public static class Builder {
        private String           title;
        private List<Researcher> authors   = new ArrayList<>();
        private String           journal;
        private LocalDate        date;
        private String           doi       = "";
        private int              citations = 0;
        private int              pages     = 0;

        /**
         * Sets the paper title.
         *
         * @param title paper title (must not be {@code null})
         * @return this builder
         */
        public Builder title(String title)              { this.title     = title;     return this; }

        /**
         * Sets the author list.
         *
         * @param authors list of {@link Researcher} contributors
         * @return this builder
         */
        public Builder authors(List<Researcher> authors){ this.authors   = authors;   return this; }

        /**
         * Sets the journal or conference name.
         *
         * @param journal publishing venue name (must not be {@code null})
         * @return this builder
         */
        public Builder journal(String journal)          { this.journal   = journal;   return this; }

        /**
         * Sets the publication date.
         *
         * @param date publication date (must not be {@code null})
         * @return this builder
         */
        public Builder date(LocalDate date)             { this.date      = date;      return this; }

        /**
         * Sets the Digital Object Identifier.
         *
         * @param doi DOI string (empty string if unavailable)
         * @return this builder
         */
        public Builder doi(String doi)                  { this.doi       = doi;       return this; }

        /**
         * Sets the initial citation count.
         *
         * @param citations citation count (non-negative)
         * @return this builder
         */
        public Builder citations(int citations)         { this.citations = citations; return this; }

        /**
         * Sets the page count.
         *
         * @param pages page count (positive)
         * @return this builder
         */
        public Builder pages(int pages)                 { this.pages     = pages;     return this; }

        /**
         * Validates the accumulated state and constructs a new
         * {@link ResearchPaper}.
         *
         * @return a fully initialised {@link ResearchPaper}
         * @throws IllegalStateException if any of the mandatory fields
         *                               ({@code title}, {@code journal},
         *                               {@code date}) are {@code null}
         */
        public ResearchPaper build() {
            if (title == null || journal == null || date == null) {
                throw new IllegalStateException("title, journal, and date are required.");
            }
            return new ResearchPaper(title, authors, journal, date, doi, citations, pages);
        }
    }
}

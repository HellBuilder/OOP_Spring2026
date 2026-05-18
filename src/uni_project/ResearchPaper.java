package uni_project;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
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

    public ResearchPaper(String paperId, String title, List<Researcher> authors, String journal,
                         LocalDate date, String doi, int citations, int pages) {
        this.title     = title;
        this.authors   = authors;
        this.journal   = journal;
        this.date      = date;
        this.doi       = doi;
        this.citations = citations;
        this.pages     = pages;
    }

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

    @Override
    public int compareTo(ResearchPaper other) {
        return Integer.compare(other.citations, this.citations);
    }

    public static final Comparator<ResearchPaper> BY_DATE =
            Comparator.comparing(ResearchPaper::getDate);

    public static final Comparator<ResearchPaper> BY_CITATIONS =
            Comparator.comparingInt(ResearchPaper::getCitations).reversed();

    public static final Comparator<ResearchPaper> BY_PAGES =
            Comparator.comparingInt(ResearchPaper::getPages);

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

    public static class Builder {
        private String           paperId;
        private String           title;
        private List<Researcher> authors   = new ArrayList<>();
        private String           journal;
        private LocalDate        date;
        private String           doi       = "";
        private int              citations = 0;
        private int              pages     = 0;

        public Builder paperId(String paperId)              { this.paperId   = paperId;   return this; }
        public Builder title(String title)                  { this.title     = title;     return this; }
        public Builder authors(List<Researcher> authors)    { this.authors   = authors;   return this; }
        public Builder journal(String journal)              { this.journal   = journal;   return this; }
        public Builder date(LocalDate date)                 { this.date      = date;      return this; }
        public Builder doi(String doi)                      { this.doi       = doi;       return this; }
        public Builder citations(int citations)             { this.citations = citations; return this; }
        public Builder pages(int pages)                     { this.pages     = pages;     return this; }

        public ResearchPaper build() {
            if (title == null || journal == null || date == null) {
                throw new IllegalStateException("title, journal, and date are required.");
            }
            return new ResearchPaper(title, authors, journal, date, doi, citations, pages);
        }
    }
}

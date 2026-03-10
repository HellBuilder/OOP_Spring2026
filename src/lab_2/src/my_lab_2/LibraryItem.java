package my_lab_2;

abstract class LibraryItem {
	private String title;
	private String author;
	private int publicationYear;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public int getPublicationYear() {
		return publicationYear;
	}
	public void setPublicationYear(int publicationYear) {
		this.publicationYear = publicationYear;
	}
	
	public LibraryItem(String title, String author, int year) {
		this.setTitle(title);
		this.setAuthor(author);
		this.setPublicationYear(year);
	}
	
	@Override
	public String toString() {
		return "Library item{title=" + title + ", author=" + author + ", publication year=" + publicationYear + "}";
	}
	
}
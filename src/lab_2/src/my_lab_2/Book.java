package my_lab_2;

public class Book extends LibraryItem {
	
	private int numberOfPages;
	
	public Book(String title, String author, int year, int pages) {
		super(title, author, year);
		this.setNumberOfPages(pages);
	}

	public int getNumberOfPages() {
		return numberOfPages;
	}

	public void setNumberOfPages(int numberOfPages) {
		this.numberOfPages = numberOfPages;
	}
	
	@Override
	public String toString() {
		return "Book{" + super.toString() + ", number of pages=" + numberOfPages + "}";
	}
	
	
	
	
	
	
	
	
	
	public static void main(String args[]) {
		Book Percy = new Book("Percy Jackson and The Lightning Thief", "Rick Riordan", 2005, 400);
		System.out.println(Percy.toString());
	}
}


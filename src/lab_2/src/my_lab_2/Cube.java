package my_lab_2;
import java.util.Scanner;

public class Cube extends Shape3D {
	private double dimension;
	public Cube(double d) {
		this.setDimension(d);
	}
	
	@Override
	public double volume() {
		return dimension * dimension * dimension;
	}
	
	public void setDimension(double d) {
		dimension = d;
	}
	
	public double getDimension() {
		return dimension;
	}
	
	
	
	
	
	public static void main(String[] args) {
		Cube rubiccube = new Cube(10);
		System.out.println("This Rubik's Cube diagonal is: " + rubiccube.getDimension() * Math.sqrt(3));
		System.out.println("This Rubik's Cube volume, on the other hand, is - " + rubiccube.volume());
		
		Scanner scanner = new Scanner(System.in);
		System.out.println("Want to create your on cube?");
		Cube custom = new Cube(scanner.nextInt());
		System.out.println("Awesome, your cube's volume is - " + custom.volume());
		scanner.close();
	}
}
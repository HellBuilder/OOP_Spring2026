package my_lab_2;

public class Cylinder extends Shape3D {
	private double radius, height;
	public Cylinder(double r, double h) {
		this.radius = r;
		this.height = h;
	}
	
	@Override
	public double volume() {
		return Math.PI * radius * radius * height;
	}
	
	public void setRadius(double r) {
		radius = r;
	}
	
	public void setHeight(double h) {
		height = h;
	}
	
	public double getHeight() {
		return height;
	}
	
	public double getRadius() {
		return radius;
	}
	
	
	
	public static void main(String[] args) {
		Cylinder hat = new Cylinder(12, 20);
		System.out.println("This hat's height is: " + hat.getHeight());
		System.out.println("This hat's volume, on the other hand, is - " + hat.volume());
	}
}
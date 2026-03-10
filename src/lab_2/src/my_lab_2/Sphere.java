package my_lab_2;

public class Sphere extends Shape3D {
	private double radius;
	public Sphere(double r) {
		this.radius = r;
	}
	
	@Override
	public double volume() {
		return Math.PI * radius * radius * radius * 4 / 3;
	}
	
	public void setRadius(double r) {
		radius = r;
	}
	
	public double getRadius() {
		return radius;
	}
	
	
	
	
	
	public static void main(String[] args) {
		Sphere ball = new Sphere(15);
		System.out.println("This ball's diameter is: " + ball.getRadius() * 2);
		System.out.println("This ball's volume, on the other hand, is - " + ball.volume());
	}
}
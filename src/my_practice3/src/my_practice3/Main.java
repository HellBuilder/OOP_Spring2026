package my_practice3;

public class Main {
    public static void main(String[] args) {
    	Crocodile g = new Crocodile( "Gena", Type.Sea);
    	System.out.println(g.getType());
    	g.makeSound();
    	g.eat("Human flesh");
    	g.eat();
    	
    	
    	
    	Staff teach = new Staff("George", "Planet Earth", "SITE", 1300000);
    	double a = teach.getPay();
    	System.out.println(a);
    	System.out.println(teach.toString());
    	
    	Student good = new Student("Jeff", "Planet Earth", "Informational Systems", 2, 2000);
    	double b = good.getFee();
    	System.out.println(b);
    	System.out.println(good.toString());
    }
}
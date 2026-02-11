package notsodefault;

public class Main {
    public static void main(String[] args) {
    	
    	DragonLaunch dragon1 = new DragonLaunch();
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.BOY));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.kidnap(new Person(Gender.GIRL));
        dragon1.printPrisoners();
        System.out.println(dragon1.willDragonEatOrNot());
        
    }
}
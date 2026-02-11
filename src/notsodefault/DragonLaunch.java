package notsodefault;

import java.util.Vector;

public class DragonLaunch {
    private Vector<Person> prisoners = new Vector<>();
    
    public void kidnap(Person p) {
        prisoners.add(p);
    }
    
    public boolean willDragonEatOrNot() {
        int boyCount = 0;
        
        for (Person p : prisoners) {
            if (p.gender == Gender.BOY) {
                boyCount++;
            } else {

                if (boyCount > 0) {
                    boyCount--;
                }
            }
        }
        
        return boyCount > 0;
    }
    
    public void printPrisoners() {
        System.out.print("Current prisoners: ");
        for (Person p : prisoners) {
            System.out.print(p);
        }
        System.out.println();
    }
    
  
}
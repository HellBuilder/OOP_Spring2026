package pets;
import java.util.ArrayList;

public class PersonRegistry {

    private ArrayList<Person> people = new ArrayList<>();

    public void addPerson(Person p) {
        people.add(p);
    }

    public void removePerson(Person p) {
        people.remove(p);
    }

    public void printPeopleWithPets() {

        for (Person p : people)
            if (p.hasPet())
                System.out.println(p);
    }

    public void printPeopleWithoutPets() {

        for (Person p : people)
            if (!p.hasPet())
                System.out.println(p);
    }

    @Override
    public String toString() {

    	String result = "";

        for (Person p : people)
            result += p + "\n";

        return result;
    }
    
    
    
    public static void main(String[] args) {

        Person john = new Employee("John", 30, "Engineer");
        Person alice = new PhDStudent("Alice", 26, "CS", "AI");

        Animal rex = new Dog("Rex", 4);

        john.assignPet(rex);

        PersonRegistry registry = new PersonRegistry();

        registry.addPerson(john);
        registry.addPerson(alice);

        john.leavePetWith(alice);

        System.out.println(registry);

        john.retrievePetFrom(alice);

        System.out.println(registry);
    }
    
}
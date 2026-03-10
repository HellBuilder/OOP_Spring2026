package pets;

public abstract class Person {

    protected String name;
    protected int age;
    protected Animal pet;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public void assignPet(Animal pet) {
        this.pet = pet;
    }

    public void removePet() {
        pet = null;
    }

    
    public boolean hasPet() {
    	if (pet == null) {
    		return false;
    	}
    	else {
    		return true;
    	}
    }

    public abstract String getOccupation();

    public void leavePetWith(Person caretaker) {

        if (pet == null) {
        	System.out.println("No pet to leave.");
        }
        else {
        	if (caretaker.hasPet()) {
        		System.out.println("Caretaker already has a pet");
        	}
        	else {
        		caretaker.assignPet(pet);
        		pet = null;
        	}
        }
    }

    public void retrievePetFrom(Person caretaker) {

        if (!caretaker.hasPet()) {
        	System.out.println("Caretaker has no pet.");
        }
        else {
        	pet = caretaker.pet;
        caretaker.removePet();
        }
    }

    @Override
    public String toString() {
    	if (pet == null) {
    		return "Person{ name=" + name + ", Occupation: (" + getOccupation() + ")" + ", has no pet}";
    	}
    	else {
    		return "Person{ name=" + name + ", Occupation: (" + getOccupation() + ")" + ", owns => " + pet.toString() + " }";
    	}
    }
}
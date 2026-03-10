package pets;

public class Dog extends Animal {

    public Dog(String name, int age) {
        super(name, age);
    }

    @Override
    public String getSound() {
        return "Bark";
    }
    
    
    @Override
    public String toString() {
    	return "Dog{ " + super.toString() + "}";
    }
}
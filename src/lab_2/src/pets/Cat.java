package pets;

public class Cat extends Animal {

    public Cat(String name, int age) {
        super(name, age);
    }

    @Override
    public String getSound() {
        return "Meow";
    }
    
    @Override
    public String toString() {
    	return "Cat{ " + super.toString() + "}";
    }
}
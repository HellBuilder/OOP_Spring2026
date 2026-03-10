package my_practice3;

public class Crocodile extends Animal {

    private Type type;
    
    public Crocodile(String name, Type type) {
        super(name);
        this.setType(type);
    }

    public Crocodile() {
        super();
    }

    @Override
    public void makeSound() {
        System.out.println("Crocodile snaps his jaws");
    }

    public void eat(String food) {
        System.out.println("Crocodile eats " + food);
    }

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}
}

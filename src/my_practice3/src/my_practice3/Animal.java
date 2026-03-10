package my_practice3;

public class Animal {

    private String name;

    public Animal() {
        this.setName("Unknown animal");
    }

    public Animal(String name) {
        this.setName(name);
    }

    public void makeSound() {
        System.out.println("Animal makes a sound");
    }

    public void eat() {
        System.out.println("Animal eats food");
    }

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}

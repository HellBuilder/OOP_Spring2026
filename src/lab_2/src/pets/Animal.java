package pets;

public abstract class Animal {

    protected String name;
    protected int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract String getSound();

    @Override
    public String toString() {
        return "Animal{ anme=" + name + ", age=" + age + "}";
    }
}
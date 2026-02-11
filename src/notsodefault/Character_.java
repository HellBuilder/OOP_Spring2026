package notsodefault;

public class Character_ {

    public enum ClassType {
        WARRIOR, MAGE, ARCHER
    }

    public static int totalCharacters;

    private final int id;

    private String name;
    public ClassType type;

    {
        totalCharacters++;
    }

    public Character_(int id, String name, ClassType type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Character_(int id, String name) {
        this(id, name, ClassType.WARRIOR);
    }

    public int getId() {
        return id;
    }

    public void attack() {
        System.out.println(name + " attacks normally.");
    }

    public void attack(String skill) {
        System.out.println(name + " attacks using " + skill);
    }
}

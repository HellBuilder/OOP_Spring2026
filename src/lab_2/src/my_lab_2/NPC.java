package my_lab_2;
import java.util.Objects;

public class NPC {

    private String name;
    private int level;

    public NPC(String name, int level) {
        this.name = name;
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public int getLevel() {
        return level;
    }
    
    public void LevelUp() {
    	this.level++;
    }

    @Override
    public String toString() {
        return "NPC{name='" + name + "', level=" + level + "}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NPC)) return false;

        NPC npc = (NPC) o;
        return level == npc.level && name.equals(npc.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, level);
    }
}
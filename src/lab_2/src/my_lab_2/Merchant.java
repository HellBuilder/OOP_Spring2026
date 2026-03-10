package my_lab_2;
import java.util.Objects;
import java.util.HashSet;

public class Merchant extends NPC {

    private String shopName;

    public Merchant(String name, int level, String shopName) {
        super(name, level);
        this.shopName = shopName;
    }

    public String getShopName() {
        return shopName;
    }

    @Override
    public String toString() {
        return "Merchant{" + super.toString() + ", shopName='" + shopName + "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Merchant)) return false;

        if (!super.equals(o)) return false;

        Merchant m = (Merchant) o;

        return shopName.equals(m.shopName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), shopName);
    }
    
    
    
    
    
    public static void main(String[] args) {

        HashSet<NPC> npcs = new HashSet<>();

        npcs.add(new Merchant("Bob", 10, "Potion Shop"));
        npcs.add(new Merchant("Bob", 10, "Potion Shop"));
        npcs.add(new Merchant("Alice", 5, "Weapon Shop"));

        for (NPC npc : npcs) {
            System.out.println(npc);
        }

        System.out.println("Total NPCs: " + npcs.size());
    }
}
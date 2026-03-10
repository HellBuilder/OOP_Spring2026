package pets;

public class PhDStudent extends Student {

    private String researchArea;

    public PhDStudent(String name, int age, String major, String researchArea) {
        super(name, age, major);
        this.researchArea = researchArea;
    }

    @Override
    public void assignPet(Animal pet) {

        if (pet instanceof Dog) {
        	System.out.println("PhD students cannot own dogs.");
        	return;
        }

        super.assignPet(pet);
    }

    @Override
    public String getOccupation() {
        return "PhD Student (" + researchArea + ")";
    }
}
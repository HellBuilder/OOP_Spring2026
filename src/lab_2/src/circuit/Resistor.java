package circuit;

public class Resistor extends Circuit {

    private double resistance;
    private double potentialDifference;

    public Resistor(double r) {
        resistance = r;
        potentialDifference = 0;
    }

    public double getResistance() {
        return resistance;
    }

    public double getPotentialDiff() {
        return potentialDifference;
    }

    public void applyPotentialDiff(double V) {
        potentialDifference = V;
    }
}
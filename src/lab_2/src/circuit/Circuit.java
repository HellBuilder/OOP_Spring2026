package circuit;

public abstract class Circuit {

    public abstract double getResistance();
    public abstract double getPotentialDiff();
    public abstract void applyPotentialDiff(double V);

    public double getPower() {
        double V = getPotentialDiff();
        double R = getResistance();
        return V * V / R;
    }

    public double getCurrent() {
        return getPotentialDiff() / getResistance();
    }
    
    
    
    
    
    
    public static void main(String[] args) {

        Circuit a = new Resistor(3.0);
        Circuit b = new Resistor(3.0);
        Circuit c = new Resistor(6.0);
        Circuit d = new Resistor(3.0);
        Circuit e = new Resistor(2.0);

        Circuit f = new Series(a, b);
        Circuit g = new Parallel(c, d);
        Circuit h = new Series(g, e);

        Circuit circuit = new Parallel(h, f);

        System.out.println("Resistance = " + circuit.getResistance());

        circuit.applyPotentialDiff(12);

        System.out.println("Current = " + circuit.getCurrent());
        System.out.println("Power = " + circuit.getPower());
    }
}
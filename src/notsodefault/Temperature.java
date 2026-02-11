package notsodefault;

public class Temperature {

    private double value;
    private char scale; // 'C' or 'F'

    public Temperature() {
        this.value = 0.0;
        this.scale = 'C';
    }

    public Temperature(char scale) {
        this.value = 0.0;
        this.scale = Character.toUpperCase(scale);
    }

    public Temperature(double value, char scale) {
        this.value = value;
        this.scale = Character.toUpperCase(scale);
    }

    public double getCelsius() {
        if (scale == 'C') {
            return value;
        } else {
            return (value - 32) * 5 / 9;
        }
    }

    public double getFahrenheit() {
        if (scale == 'F') {
            return value;
        } else {
            return (9.0 / 5.0) * value + 32;
        }
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setScale(char scale) {
        this.scale = Character.toUpperCase(scale);
    }

    public void setTemperature(double value, char scale) {
        this.value = value;
        this.scale = Character.toUpperCase(scale);
    }

    public char getScale() {
        return scale;
    }
}

package uni_project;

import java.io.Serializable;

public class Mark implements Serializable {

    private static final long   serialVersionUID = 10L;
    private static final double PASS_THRESHOLD   = 50.0;

    // Standard Kazakh university grading breakdown: 30 + 30 + 40 = 100
    private double firstAttestation;   // max 30
    private double secondAttestation;  // max 30
    private double finalExam;          // max 40

    public Mark(double firstAttestation, double secondAttestation, double finalExam) {
        this.firstAttestation  = clamp(firstAttestation, 0, 30);
        this.secondAttestation = clamp(secondAttestation, 0, 30);
        this.finalExam         = clamp(finalExam, 0, 40);
    }

    public double getTotalScore() {
        return firstAttestation + secondAttestation + finalExam;
    }

    public boolean isPassing() {
        return getTotalScore() >= PASS_THRESHOLD;
    }

    private static double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(max, value));
    }

    public double getFirstAttestation()  { return firstAttestation; }
    public double getSecondAttestation() { return secondAttestation; }
    public double getFinalExam()         { return finalExam; }

    @Override
    public String toString() {
        return String.format("Mark[%.1f + %.1f + %.1f = %.1f (%s)]",
                firstAttestation, secondAttestation, finalExam,
                getTotalScore(), isPassing() ? "PASS" : "FAIL");
    }
}

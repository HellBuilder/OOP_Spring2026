package notsodefault;

import java.util.ArrayList;

public class GradeBook {

    private Course course;
    private ArrayList<Student> students;
    private ArrayList<Integer> grades;

    public GradeBook(Course course) {
        this.course = course;
        students = new ArrayList<>();
        grades = new ArrayList<>();
    }

    public void addStudent(Student student, int grade) {
        students.add(student);
        grades.add(grade);
    }

    public void displayMessage() {
        System.out.println("Welcome to the grade book for:");
        System.out.println(course);
    }

    public double determineClassAverage() {
        int total = 0;
        for (int grade : grades) {
            total += grade;
        }
        return grades.size() == 0 ? 0 : (double) total / grades.size();
    }

    public int determineMaxGrade() {
        int max = 0;
        for (int grade : grades) {
            if (grade > max) {
                max = grade;
            }
        }
        return max;
    }

    public int determineMinGrade() {
        int min = 100;
        for (int grade : grades) {
            if (grade < min) {
                min = grade;
            }
        }
        return min;
    }

    public void outputBarChart() {
        System.out.println("Grade distribution:");

        int[] frequency = new int[11];

        for (int grade : grades) {
            frequency[grade / 10]++;
        }

        for (int i = 0; i < frequency.length; i++) {
            if (i == 10)
                System.out.print("100: ");
            else
                System.out.print(i * 10 + "-" + (i * 10 + 9) + ": ");

            for (int stars = 0; stars < frequency[i]; stars++)
                System.out.print("*");

            System.out.println();
        }
    }

    public void displayGradeReport() {
        System.out.println("Class average: " + determineClassAverage());
        System.out.println("Highest grade: " + determineMaxGrade());
        System.out.println("Lowest grade: " + determineMinGrade());
        outputBarChart();
    }

    public String toString() {
        return "GradeBook for " + course;
    }
}

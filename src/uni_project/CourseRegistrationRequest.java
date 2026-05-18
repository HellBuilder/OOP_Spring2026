package uni_project;

import java.io.Serializable;
import java.time.LocalDateTime;

public class CourseRegistrationRequest implements Serializable {

    private static final long serialVersionUID = 15L;

    private final Student       student;
    private final Course        course;
    private       RequestStatus status;
    private final LocalDateTime createdAt;

    public CourseRegistrationRequest(Student student, Course course) {
        this.student   = student;
        this.course    = course;
        this.status    = RequestStatus.PENDING;
        this.createdAt = LocalDateTime.now();
    }

    public void approve() {
        this.status = RequestStatus.APPROVED;
        System.out.println("[REQUEST] APPROVED: " + student.getFirstName()
                + " " + student.getLastName() + " -> " + course.getName());
    }

    public void reject(String reason) {
        this.status = RequestStatus.REJECTED;
        System.out.println("[REQUEST] REJECTED: " + student.getFirstName()
                + " " + student.getLastName() + " -> " + course.getName()
                + " (reason: " + reason + ")");
    }

    public Student       getStudent()   { return student; }
    public Course        getCourse()    { return course; }
    public RequestStatus getStatus()    { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public String toString() {
        return "Request[" + student.getFirstName() + " " + student.getLastName()
                + " -> " + course.getName() + " | " + status
                + " | " + createdAt.toLocalDate() + "]";
    }
}

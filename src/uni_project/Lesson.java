package uni_project;

import java.io.Serializable;

public class Lesson implements Serializable {

    private static final long serialVersionUID = 14L;

    private String     lessonId;
    private Course     course;
    private LessonType type;
    private String     room;
    private int        durationMinutes;

    public Lesson(String lessonId, Course course, LessonType type,
                  String room, int durationMinutes) {
        this.lessonId        = lessonId;
        this.course          = course;
        this.type            = type;
        this.room            = room;
        this.durationMinutes = durationMinutes;
    }

    public String     getLessonId()        { return lessonId; }
    public Course     getCourse()          { return course; }
    public LessonType getType()            { return type; }
    public String     getRoom()            { return room; }
    public int        getDurationMinutes() { return durationMinutes; }

    @Override
    public String toString() {
        return "Lesson[" + lessonId + ", " + type + ", " + course.getName()
                + ", room=" + room + "]";
    }
}

package uni_project;

import java.io.Serializable;

public class Lesson implements Serializable {

    private static final long serialVersionUID = 14L;

    private String     lessonId;
    private String     name;
    private LessonType type;
    private Teacher    instructor;
    private String     room;
    private String     dayOfWeek;
    private String     startTime;

    public Lesson(String lessonId, String name, LessonType type,
                  Teacher instructor, String room, String dayOfWeek, String startTime) {
        this.lessonId   = lessonId;
        this.name       = name;
        this.type       = type;
        this.instructor = instructor;
        this.room       = room;
        this.dayOfWeek  = dayOfWeek;
        this.startTime  = startTime;
    }

    public String     getLessonId()   { return lessonId; }
    public String     getName()       { return name; }
    public LessonType getType()       { return type; }
    public Teacher    getInstructor() { return instructor; }
    public String     getRoom()       { return room; }
    public String     getDayOfWeek()  { return dayOfWeek; }
    public String     getStartTime()  { return startTime; }

    @Override
    public String toString() {
        return String.format("Lesson[%s | %-20s | %-8s | Room:%-6s | %s %s]",
                lessonId, name, type, room, dayOfWeek, startTime);
    }
}

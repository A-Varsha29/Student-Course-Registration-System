import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Student {
    private final String studentId;
    private final String name;
    private final List<String> registeredCourseCodes;

    public Student(String studentId, String name) {
        this.studentId = studentId.trim().toUpperCase();
        this.name = name.trim();
        this.registeredCourseCodes = new ArrayList<>();
    }

    public String getStudentId() {
        return studentId;
    }

    public String getName() {
        return name;
    }

    public List<String> getRegisteredCourseCodes() {
        return Collections.unmodifiableList(registeredCourseCodes);
    }

    public boolean isRegistered(String courseCode) {
        String normalized = courseCode.trim().toUpperCase();
        return registeredCourseCodes.contains(normalized);
    }

    public boolean addCourse(String courseCode) {
        String normalized = courseCode.trim().toUpperCase();
        if (registeredCourseCodes.contains(normalized)) {
            return false;
        }
        registeredCourseCodes.add(normalized);
        return true;
    }

    public boolean removeCourse(String courseCode) {
        String normalized = courseCode.trim().toUpperCase();
        return registeredCourseCodes.remove(normalized);
    }
}
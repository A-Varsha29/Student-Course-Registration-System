public class Course {
    private final String courseCode;
    private final String title;
    private final String description;
    private final int capacity;
    private final String schedule;
    private int enrolledCount;

    public Course(String courseCode, String title, String description, int capacity, String schedule) {
        this.courseCode = courseCode.trim().toUpperCase();
        this.title = title.trim();
        this.description = description.trim();
        this.capacity = Math.max(1, capacity);
        this.schedule = schedule.trim();
        this.enrolledCount = 0;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public int getCapacity() {
        return capacity;
    }

    public String getSchedule() {
        return schedule;
    }

    public int getEnrolledCount() {
        return enrolledCount;
    }

    public int getAvailableSlots() {
        return capacity - enrolledCount;
    }

    public boolean hasAvailableSlot() {
        return enrolledCount < capacity;
    }

    public boolean enrollStudent() {
        if (!hasAvailableSlot()) {
            return false;
        }
        enrolledCount++;
        return true;
    }

    public boolean dropStudent() {
        if (enrolledCount <= 0) {
            return false;
        }
        enrolledCount--;
        return true;
    }
}
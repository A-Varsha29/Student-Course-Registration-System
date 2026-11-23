import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class CourseRegistrationSystem {

    private final Map<String, Course> courses = new LinkedHashMap<>();
    private final Map<String, Student> students = new LinkedHashMap<>();
    private final Scanner scanner;

    public CourseRegistrationSystem(Scanner scanner) {
        this.scanner = scanner;
        seedSampleData();
    }

    private void seedSampleData() {
        addCourseInternal(new Course(
                "CS101",
                "Java Programming",
                "OOP, collections, exception handling, and core Java fundamentals",
                3,
                "Mon/Wed/Fri 09:00 - 10:30"
        ));
        addCourseInternal(new Course(
                "DS201",
                "Data Structures & Algorithms",
                "Arrays, linked lists, trees, graphs, sorting, and complexity analysis",
                2,
                "Tue/Thu 11:00 - 12:30"
        ));
        addCourseInternal(new Course(
                "DB204",
                "Database Management Systems",
                "Relational modeling, SQL queries, normalization, and transactions",
                3,
                "Mon/Wed 14:00 - 15:30"
        ));
        addCourseInternal(new Course(
                "WD105",
                "Web Development Basics",
                "Semantic HTML, responsive CSS, and interactive JavaScript",
                2,
                "Tue/Thu 15:00 - 16:30"
        ));

        students.put("S101", new Student("S101", "Aarav Sharma"));
        students.put("S102", new Student("S102", "Diya Nair"));
    }

    private void addCourseInternal(Course course) {
        courses.put(course.getCourseCode(), course);
    }

    public void displayCourses() {
        System.out.println("\n==================== AVAILABLE COURSES ====================");
        if (courses.isEmpty()) {
            System.out.println("No courses available.");
            return;
        }

        for (Course c : courses.values()) {
            System.out.printf("[%s] %s%n", c.getCourseCode(), c.getTitle());
            System.out.println("   Description : " + c.getDescription());
            System.out.println("   Schedule    : " + c.getSchedule());
            System.out.printf("   Slots       : %d available out of %d (%d enrolled)%n",
                    c.getAvailableSlots(), c.getCapacity(), c.getEnrolledCount());
        }
        System.out.println("===========================================================");
    }

    private Student findOrRegisterStudent(String studentId) {
        String normalizedId = studentId.trim().toUpperCase();
        Student existing = students.get(normalizedId);
        if (existing != null) {
            return existing;
        }

        System.out.print("Student ID not found. Enter student name to register: ");
        String name = scanner.nextLine().trim();
        if (name.isEmpty()) {
            name = "Student " + normalizedId;
        }
        Student created = new Student(normalizedId, name);
        students.put(normalizedId, created);
        System.out.println("New student profile created for " + created.getName() + " (" + created.getStudentId() + ").");
        return created;
    }

    public void registerStudentForCourse() {
        displayCourses();
        System.out.print("\nEnter Student ID (e.g., S101): ");
        String studentId = scanner.nextLine().trim();
        if (studentId.isEmpty()) {
            System.out.println("Registration failed: Student ID cannot be empty.");
            return;
        }

        Student student = findOrRegisterStudent(studentId);

        System.out.print("Enter Course Code to register (e.g., CS101): ");
        String courseCode = scanner.nextLine().trim().toUpperCase();
        Course course = courses.get(courseCode);

        if (course == null) {
            System.out.println("Registration failed: Course '" + courseCode + "' does not exist.");
            return;
        }

        if (student.isRegistered(courseCode)) {
            System.out.println("Registration failed: " + student.getName() +
                    " is already registered for " + courseCode + ".");
            return;
        }

        if (!course.hasAvailableSlot()) {
            System.out.println("Registration failed: Course '" + courseCode +
                    "' (" + course.getTitle() + ") is full (" + course.getCapacity() + "/" + course.getCapacity() + ").");
            return;
        }

        if (course.enrollStudent() && student.addCourse(courseCode)) {
            System.out.printf("Success! %s (%s) registered for [%s] %s. Remaining slots: %d%n",
                    student.getName(), student.getStudentId(), course.getCourseCode(),
                    course.getTitle(), course.getAvailableSlots());
        } else {
            System.out.println("Registration failed: Unable to complete enrollment.");
        }
    }

    public void removeCourseForStudent() {
        System.out.print("\nEnter Student ID (e.g., S101): ");
        String studentId = scanner.nextLine().trim().toUpperCase();
        Student student = students.get(studentId);

        if (student == null) {
            System.out.println("Drop failed: No student found with ID '" + studentId + "'.");
            return;
        }

        List<String> enrolled = student.getRegisteredCourseCodes();
        if (enrolled.isEmpty()) {
            System.out.println(student.getName() + " (" + student.getStudentId() +
                    ") is not currently registered for any courses.");
            return;
        }

        System.out.println("Registered courses for " + student.getName() + " (" + student.getStudentId() + "):");
        for (String code : enrolled) {
            Course c = courses.get(code);
            String title = (c != null) ? c.getTitle() : "Course";
            System.out.println("  - [" + code + "] " + title);
        }

        System.out.print("Enter Course Code to drop: ");
        String courseCode = scanner.nextLine().trim().toUpperCase();

        if (!student.isRegistered(courseCode)) {
            System.out.println("Drop failed: " + student.getName() +
                    " is not registered for '" + courseCode + "'.");
            return;
        }

        Course course = courses.get(courseCode);
        if (student.removeCourse(courseCode)) {
            if (course != null) {
                course.dropStudent();
            }
            System.out.printf("Success! Dropped [%s] for %s (%s).%n",
                    courseCode, student.getName(), student.getStudentId());
        } else {
            System.out.println("Drop failed: Unable to remove course.");
        }
    }

    public void viewStudentRegistration() {
        System.out.print("\nEnter Student ID: ");
        String studentId = scanner.nextLine().trim().toUpperCase();
        Student student = students.get(studentId);

        if (student == null) {
            System.out.println("No student found with ID '" + studentId + "'.");
            return;
        }

        System.out.println("\n--- Student Record ---");
        System.out.println("Student ID : " + student.getStudentId());
        System.out.println("Name       : " + student.getName());
        List<String> enrolled = student.getRegisteredCourseCodes();
        if (enrolled.isEmpty()) {
            System.out.println("Courses    : None registered");
        } else {
            System.out.println("Courses    :");
            for (String code : enrolled) {
                Course c = courses.get(code);
                if (c != null) {
                    System.out.printf("  * [%s] %s (%s)%n", c.getCourseCode(), c.getTitle(), c.getSchedule());
                } else {
                    System.out.println("  * [" + code + "]");
                }
            }
        }
    }

    public void start() {
        boolean running = true;

        System.out.println("============================================================");
        System.out.println("            STUDENT COURSE REGISTRATION SYSTEM              ");
        System.out.println("============================================================");

        while (running) {
            System.out.println("\n--- Main Menu ---");
            System.out.println("1. View Available Courses & Slots");
            System.out.println("2. Register Student for a Course");
            System.out.println("3. Drop a Registered Course");
            System.out.println("4. View Student Registered Courses");
            System.out.println("5. Exit");
            System.out.print("Choose an option (1-5): ");

            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    displayCourses();
                    break;
                case "2":
                    registerStudentForCourse();
                    break;
                case "3":
                    removeCourseForStudent();
                    break;
                case "4":
                    viewStudentRegistration();
                    break;
                case "5":
                    System.out.println("Exiting Student Course Registration System. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number from 1 to 5.");
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseRegistrationSystem system = new CourseRegistrationSystem(scanner);
        system.start();
        scanner.close();
    }
}
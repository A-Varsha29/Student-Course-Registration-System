## Student Course Registration System

A console-based Java application built. It manages a course catalog, student records, slot availability, course enrollment, and course removal through an interactive command-line menu.

## Project Overview

The Student Course Registration System models how students browse available courses, check remaining seats, register for classes, and drop courses they no longer wish to take. All course and student states stay synchronized so enrolling or dropping a course updates available slots immediately.

## Features

- **Course Database (`Course.java`):** Stores each course's code, title, description, maximum capacity, schedule, and current enrollment count, while computing available slots dynamically.
- **Student Database (`Student.java`):** Stores each student's ID, name, and list of registered course codes, with helpers to check, add, and drop enrollments.
- **Course Listing with Slot Details:** Displays all courses along with their descriptions, schedules, capacities, enrolled counts, and open slots.
- **Student Registration:** Allows existing or newly entered students to register for a course while blocking invalid course codes, duplicate registrations, and full courses.
- **Course Removal:** Lets students view and drop courses they are registered for, freeing up the seat in that course.
- **Student Record Lookup:** Displays a student's profile along with their registered courses and schedules.

## Code Structure

- `Course.java`
  - `Course(...)`: Initializes course details (`courseCode`, `title`, `description`, `capacity`, `schedule`) and sets initial enrollment to `0`.
  - `getAvailableSlots()`, `hasAvailableSlot()`: Checks remaining capacity for enrollment.
  - `enrollStudent()`, `dropStudent()`: Safely increments or decrements enrolled count within bounds.
- `Student.java`
  - `Student(String studentId, String name)`: Creates a student record with an empty list of registered courses.
  - `isRegistered(String courseCode)`, `addCourse(String courseCode)`, `removeCourse(String courseCode)`: Manages course enrollments for the student.
- `CourseRegistrationSystem.java`
  - `displayCourses()`: Prints all courses with descriptions, schedules, and slot availability.
  - `registerStudentForCourse()`: Handles student lookup/creation, course lookup, capacity/duplicate checks, and enrollment.
  - `removeCourseForStudent()`: Lists a student's active courses and drops the selected course while freeing a slot.
  - `viewStudentRegistration()`: Prints a student's enrolled courses and schedules.
  - `start()` & `main(...)`: Runs the main menu loop and initializes the system.

## Tech Stack & Tools

- **Language:** Java (JDK 25)
- **Standard Libraries:** `java.util.Scanner`, `java.util.Map` (`LinkedHashMap`), `java.util.List` (`ArrayList`), `java.util.Collections`
- **Editor & Version Control:** Visual Studio Code, GitHub Desktop / GitHub

## How to Compile and Run

1. Open a terminal inside the project folder containing `Course.java`, `Student.java`, and `CourseRegistrationSystem.java`.
2. Compile all Java source files:
   ```bash
   javac Course.java Student.java CourseRegistrationSystem.java

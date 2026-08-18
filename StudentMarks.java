public class StudentMarks {

    Student[] students = new Student[70];

    int studentCount = 0;

    // Add student
    void addStudent(Student student) {

        if (studentCount < 70) {

            students[studentCount] = student;

            studentCount++;

            System.out.println("Student added successfully!");

        }
        else {

            System.out.println("Class is full!");
        }
    }

    // Display all students
    void displayAllStudents() {

        System.out.println("\n========== ALL STUDENTS ==========");

        for (int i = 0; i < studentCount; i++) {

            System.out.println("\n----- Student " + (i + 1) + " -----");

            students[i].display();
        }
    }

    // Find topper
    void findTopper() {

        if (studentCount == 0) {

            System.out.println("No students available.");

            return;
        }

        int topperIndex = 0;

        for (int i = 1; i < studentCount; i++) {

            if (students[i].total > students[topperIndex].total) {

                topperIndex = i;
            }
        }

        System.out.println("\n========== CLASS TOPPER ==========");

        System.out.println("Name        : " + students[topperIndex].name);
        System.out.println("Roll Number : " + students[topperIndex].rollNo);
        System.out.println("Total Marks : " + students[topperIndex].total);
        System.out.println("Average     : " + students[topperIndex].average);
        System.out.println("Grade       : " + students[topperIndex].grade);
    }

    // Calculate class average
    void calculateClassAverage() {

        if (studentCount == 0) {

            System.out.println("No students available.");

            return;
        }

        int totalMarks = 0;

        for (int i = 0; i < studentCount; i++) {

            totalMarks = totalMarks + students[i].total;
        }

        double classAverage = totalMarks / (studentCount * 5.0);

        System.out.println("\n========== CLASS AVERAGE ==========");

        System.out.println("Class Average : " + classAverage);
    }
}
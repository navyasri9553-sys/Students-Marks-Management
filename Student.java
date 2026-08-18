public class Student {

    int rollNo;
    String name;

    int javaMarks;
    int pythonMarks;
    int mathsMarks;
    int englishMarks;
    int dbmsMarks;

    int total;
    double average;
    char grade;


    // Constructor
    Student(
        int rollNo,
        String name,
        int javaMarks,
        int pythonMarks,
        int mathsMarks,
        int englishMarks,
        int dbmsMarks
    ) {

        this.rollNo = rollNo;
        this.name = name;

        this.javaMarks = javaMarks;
        this.pythonMarks = pythonMarks;
        this.mathsMarks = mathsMarks;
        this.englishMarks = englishMarks;
        this.dbmsMarks = dbmsMarks;
    }


    // Calculate total marks
    void calculateTotal() {

        total =
            javaMarks
            + pythonMarks
            + mathsMarks
            + englishMarks
            + dbmsMarks;
    }


    // Calculate average
    void calculateAverage() {

        average = total / 5.0;
    }


    // Calculate grade
    void calculateGrade() {

        if (average >= 90) {

            grade = 'A';

        } else if (average >= 80) {

            grade = 'B';

        } else if (average >= 70) {

            grade = 'C';

        } else if (average >= 60) {

            grade = 'D';

        } else if (average >= 50) {

            grade = 'E';

        } else {

            grade = 'F';
        }
    }


    // Calculate complete result
    void calculateResult() {

        calculateTotal();

        calculateAverage();

        calculateGrade();
    }
    // Display student details
    void display() {

        System.out.println("Roll Number : " + rollNo);
        System.out.println("Name        : " + name);
        System.out.println("Java        : " + javaMarks);
        System.out.println("Python      : " + pythonMarks);
        System.out.println("Maths       : " + mathsMarks);
        System.out.println("English     : " + englishMarks);
        System.out.println("DBMS        : " + dbmsMarks);
        System.out.println("Total       : " + total);
        System.out.println("Average     : " + average);
        System.out.println("Grade       : " + grade);
}
}

import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        StudentDAO studentDAO = new StudentDAO();
        CourseDAO courseDAO = new CourseDAO();
        EnrollmentDAO enrollmentDAO = new EnrollmentDAO();
        AttendanceDAO attendanceDAO = new AttendanceDAO();
        MarksDAO marksDAO = new MarksDAO();
        ResultDAO resultDAO = new ResultDAO();

        while (true) {
            System.out.println("\n===== KEC R2024 ACADEMIC SYSTEM =====");
            System.out.println("1. Register Student");
            System.out.println("2. Add Course");
            System.out.println("3. Enroll Student");
            System.out.println("4. Update Attendance");
            System.out.println("5. Enter Assessment Marks");
            System.out.println("6. Generate Marksheet");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");

            String choice = sc.nextLine();

            switch (choice) {

                case "1":
                    System.out.print("Roll Number: ");
                    String roll = sc.nextLine();

                    System.out.print("Student Name: ");
                    String name = sc.nextLine();

                    System.out.print("Department: ");
                    String dept = sc.nextLine();

                    System.out.print("Semester: ");
                    int sem = Integer.parseInt(sc.nextLine());

                    studentDAO.addStudent(roll, name, dept, sem);
                    break;

                case "2":
                    System.out.print("Course Code: ");
                    String code = sc.nextLine();

                    System.out.print("Course Title: ");
                    String title = sc.nextLine();

                    System.out.print("Credits: ");
                    int credits = Integer.parseInt(sc.nextLine());

                    System.out.print("Course Type (THEORY/PRACTICAL/EMBEDDED): ");
                    String type = sc.nextLine();

                    courseDAO.addCourse(code, title, credits, type);
                    break;

                case "3":
                    System.out.print("Student Roll Number: ");
                    String r = sc.nextLine();

                    System.out.print("Course Code: ");
                    String c = sc.nextLine();

                    enrollmentDAO.enrollStudent(r, c);
                    break;

                case "4":
                    System.out.print("Student Roll Number: ");
                    String ar = sc.nextLine();

                    System.out.print("Course Code: ");
                    String ac = sc.nextLine();

                    System.out.print("Classes Held: ");
                    int held = Integer.parseInt(sc.nextLine());

                    System.out.print("Classes Attended: ");
                    int attended = Integer.parseInt(sc.nextLine());

                    attendanceDAO.updateAttendance(ar, ac, held, attended);
                    break;

                case "5":
                    System.out.print("Student Roll Number: ");
                    String mr = sc.nextLine();

                    System.out.print("Course Code: ");
                    String mc = sc.nextLine();

                    System.out.print("CIA1: ");
                    double cia1 = Double.parseDouble(sc.nextLine());

                    System.out.print("CIA2: ");
                    double cia2 = Double.parseDouble(sc.nextLine());

                    System.out.print("Assignment: ");
                    double assignment = Double.parseDouble(sc.nextLine());

                    System.out.print("Quiz: ");
                    double quiz = Double.parseDouble(sc.nextLine());

                    System.out.print("Lab (enter 0 if not applicable): ");
                    double lab = Double.parseDouble(sc.nextLine());

                    System.out.print("ESE: ");
                    double ese = Double.parseDouble(sc.nextLine());

                    marksDAO.enterMarks(mr, mc, cia1, cia2,
                                        assignment, quiz, lab, ese);
                    break;

                case "6":
                    System.out.print("Student Roll Number: ");
                    String sr = sc.nextLine();

                    resultDAO.generateResult(sr);
                    break;

                case "7":
                    System.out.println("Thank you for using KEC Academic System!");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}

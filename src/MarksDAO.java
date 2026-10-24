
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class MarksDAO {

    public void enterMarks(String rollNo, String courseCode,
                           double cia1, double cia2,
                           double assignment, double quiz,
                           double lab, double ese) {

        String sql = "INSERT INTO assessment_marks " +
                "(roll_no, course_code, cia1, cia2, assignment_marks, " +
                "quiz_marks, lab_marks, ese_marks) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE " +
                "cia1=VALUES(cia1), cia2=VALUES(cia2), " +
                "assignment_marks=VALUES(assignment_marks), " +
                "quiz_marks=VALUES(quiz_marks), lab_marks=VALUES(lab_marks), " +
                "ese_marks=VALUES(ese_marks)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNo);
            ps.setString(2, courseCode);
            ps.setDouble(3, cia1);
            ps.setDouble(4, cia2);
            ps.setDouble(5, assignment);
            ps.setDouble(6, quiz);
            ps.setDouble(7, lab);
            ps.setDouble(8, ese);

            ps.executeUpdate();
            System.out.println("Assessment marks saved successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        MarksDAO dao = new MarksDAO();

        System.out.print("Enter Student Roll Number: ");
        String rollNo = sc.nextLine();

        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine();

        System.out.print("Enter CIA1 marks: ");
        double cia1 = Double.parseDouble(sc.nextLine());

        System.out.print("Enter CIA2 marks: ");
        double cia2 = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Assignment marks: ");
        double assignment = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Quiz marks: ");
        double quiz = Double.parseDouble(sc.nextLine());

        System.out.print("Enter Lab marks (enter 0 if not applicable): ");
        double lab = Double.parseDouble(sc.nextLine());

        System.out.print("Enter ESE marks: ");
        double ese = Double.parseDouble(sc.nextLine());

        dao.enterMarks(rollNo, courseCode, cia1, cia2,
                       assignment, quiz, lab, ese);

        sc.close();
    }
}
        
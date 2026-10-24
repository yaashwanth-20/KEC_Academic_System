
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class EnrollmentDAO {

    public void enrollStudent(String rollNo, String courseCode) {

        String sql = "INSERT INTO enrollments " +
                     "(roll_no, course_code, classes_held, classes_attended) " +
                     "VALUES (?, ?, 0, 0)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNo);
            ps.setString(2, courseCode);

            ps.executeUpdate();
            System.out.println("Student enrolled successfully!");

        } catch (SQLException e) {
            System.out.println("Enrollment failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        EnrollmentDAO dao = new EnrollmentDAO();

        System.out.print("Enter Student Roll Number: ");
        String rollNo = sc.nextLine();

        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine();

        dao.enrollStudent(rollNo, courseCode);
        sc.close();
    }
}

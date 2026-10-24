
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class AttendanceDAO {

    public void updateAttendance(String rollNo, String courseCode,
                                 int held, int attended) {

        if (held < 0 || attended < 0 || attended > held) {
            System.out.println("Invalid attendance values!");
            return;
        }

        String sql = "UPDATE enrollments SET classes_held = ?, " +
                     "classes_attended = ? WHERE roll_no = ? " +
                     "AND course_code = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, held);
            ps.setInt(2, attended);
            ps.setString(3, rollNo);
            ps.setString(4, courseCode);

            int rows = ps.executeUpdate();

            if (rows == 0) {
                System.out.println("Enrollment not found!");
                return;
            }

            double percentage = held == 0 ? 0 : attended * 100.0 / held;

            System.out.printf("Attendance: %.2f%%%n", percentage);

            if (percentage >= 75) {
                System.out.println("Status: ELIGIBLE");
            } else if (percentage >= 65) {
                System.out.println("Status: CONDITIONAL - Condonation pending");
            } else {
                System.out.println("Status: SA - Barred from ESE");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        AttendanceDAO dao = new AttendanceDAO();

        System.out.print("Enter Roll Number: ");
        String rollNo = sc.nextLine();

        System.out.print("Enter Course Code: ");
        String courseCode = sc.nextLine();

        System.out.print("Enter Total Classes Held: ");
        int held = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Classes Attended: ");
        int attended = Integer.parseInt(sc.nextLine());

        dao.updateAttendance(rollNo, courseCode, held, attended);
        sc.close();
    }
}

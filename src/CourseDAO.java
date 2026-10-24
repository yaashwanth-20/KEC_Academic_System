
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

public class CourseDAO {

    public void addCourse(String code, String title,
                          int credits, String type) {

        String sql = "INSERT INTO courses " +
                     "(course_code, course_title, credits, course_type) " +
                     "VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, code);
            ps.setString(2, title);
            ps.setInt(3, credits);
            ps.setString(4, type.toUpperCase());

            ps.executeUpdate();
            System.out.println("Course added successfully!");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CourseDAO dao = new CourseDAO();

        System.out.print("Enter Course Code: ");
        String code = sc.nextLine();

        System.out.print("Enter Course Title: ");
        String title = sc.nextLine();

        System.out.print("Enter Credits: ");
        int credits = Integer.parseInt(sc.nextLine());

        System.out.print("Enter Course Type (THEORY/PRACTICAL/EMBEDDED): ");
        String type = sc.nextLine();

        dao.addCourse(code, title, credits, type);
        sc.close();
    }
}

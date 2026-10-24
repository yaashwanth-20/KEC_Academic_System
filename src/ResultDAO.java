
import java.sql.*;
import java.util.Scanner;

public class ResultDAO {

    public void generateResult(String rollNo) {

        String sql = "SELECT s.student_name, c.course_code, " +
                "c.course_title, c.credits, c.course_type, " +
                "e.classes_held, e.classes_attended, e.condonation_approved, " +
                "m.cia1, m.cia2, m.assignment_marks, m.quiz_marks, " +
                "m.lab_marks, m.ese_marks, m.ese_absent " +
                "FROM students s " +
                "JOIN enrollments e ON s.roll_no=e.roll_no " +
                "JOIN courses c ON e.course_code=c.course_code " +
                "LEFT JOIN assessment_marks m " +
                "ON e.roll_no=m.roll_no AND e.course_code=m.course_code " +
                "WHERE s.roll_no=?";

        double totalCredits = 0;
        double weightedPoints = 0;

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rollNo);

            try (ResultSet rs = ps.executeQuery()) {

                System.out.println("\nSTUDENT ACADEMIC MARKSHEET");
                System.out.println("==============================================");

                boolean found = false;

                while (rs.next()) {
                    found = true;

                    String name = rs.getString("student_name");
                    String code = rs.getString("course_code");
                    String title = rs.getString("course_title");
                    int credits = rs.getInt("credits");
                    String type = rs.getString("course_type");

                    int held = rs.getInt("classes_held");
                    int attended = rs.getInt("classes_attended");
                    boolean condonation = rs.getBoolean("condonation_approved");

                    double attendance = held == 0 ? 0 :
                            attended * 100.0 / held;

                    double cia1 = rs.getDouble("cia1");
                    double cia2 = rs.getDouble("cia2");
                    double assignment = rs.getDouble("assignment_marks");
                    double quiz = rs.getDouble("quiz_marks");
                    double lab = rs.getDouble("lab_marks");
                    double ese = rs.getDouble("ese_marks");

                    boolean absent = rs.getBoolean("ese_absent");
                    boolean missing = rs.wasNull();

                    System.out.println("\nStudent: " + name);
                    System.out.println("Roll Number: " + rollNo);
                    System.out.println("Course: " + code + " - " + title);
                    System.out.printf("Attendance: %.2f%%%n", attendance);

                    String result;
                    double finalMark = 0;
                    int gradePoint = 0;

                    if (absent) {
                        result = "AB";
                    } else if (held > 0 && attendance < 65) {
                        result = "SA";
                    } else if (attendance < 75 && !condonation) {
                        result = "CONDITIONAL";
                    } else if (missing) {
                        result = "MARKS PENDING";
                    } else {
                        double cia;

                        if (type.equals("THEORY")) {
                            cia = (cia1 + cia2 + assignment + quiz) / 4.0;
                            finalMark = cia * 0.40 + ese * 0.60;
                        } else if (type.equals("PRACTICAL")) {
                            cia = (cia1 + cia2 + assignment + quiz + lab) / 5.0;
                            finalMark = cia * 0.60 + ese * 0.40;
                        } else {
                            cia = (cia1 + cia2 + assignment + quiz + lab) / 5.0;
                            finalMark = cia * 0.50 + ese * 0.50;
                        }

                        double eseMinimum = type.equals("THEORY") ? 27 :
                                type.equals("PRACTICAL") ? 20 : 22.5;

                        if (ese < eseMinimum || finalMark < 50) {
                            result = "RA";
                        } else {
                            result = "PASS";

                            if (finalMark >= 91) gradePoint = 10;
                            else if (finalMark >= 81) gradePoint = 9;
                            else if (finalMark >= 71) gradePoint = 8;
                            else if (finalMark >= 61) gradePoint = 7;
                            else if (finalMark >= 50) gradePoint = 6;
                        }
                    }

                    if (result.equals("AB") || result.equals("SA") ||
                        result.equals("RA") || result.equals("CONDITIONAL") ||
                        result.equals("MARKS PENDING")) {
                        gradePoint = 0;
                    }

                    System.out.printf("Final Mark: %.2f%n", finalMark);
                    System.out.println("Result: " + result);
                    System.out.println("Grade Point: " + gradePoint);

                    totalCredits += credits;
                    weightedPoints += credits * gradePoint;
                }

                if (!found) {
                    System.out.println("Student or enrollment not found.");
                } else if (totalCredits > 0) {
                    double sgpa = weightedPoints / totalCredits;
                    System.out.printf("%nSGPA: %.2f%n", sgpa);
                    System.out.printf("Percentage equivalent: %.2f%%%n",
                                      sgpa * 10);
                }
            }

        } catch (SQLException e) {
            System.out.println("Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter Student Roll Number: ");
        String rollNo = sc.nextLine();

        new ResultDAO().generateResult(rollNo);
        sc.close();
    }
}

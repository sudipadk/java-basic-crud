package com.studentApp.dao;

import com.studentApp.model.Student;
import com.studentApp.utils.DBUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    /**
     * Inserts a new student record into the database.
     *
     * @param student the Student object to be inserted
     */
    public void addStudent(Student student) {
        String sql = "INSERT INTO students " +
                "(full_name, email, phone, date_of_birth, gender, address, course, enrollment_number, admission_date) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setDate(4, java.sql.Date.valueOf(student.getDateOfBirth())); // LocalDate to SQL Date
            stmt.setString(5, student.getGender());
            stmt.setString(6, student.getAddress());
            stmt.setString(7, student.getCourse());
            stmt.setString(8, student.getEnrollmentNumber());
            stmt.setDate(9, java.sql.Date.valueOf(student.getAdmissionDate())); // LocalDate to SQL Date

            int rowsInserted = stmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println(" Student added successfully!");
            }

        } catch (SQLException e) {
            System.err.println(" Error inserting student: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * Updates a student record into the database.
     *
     * @param student the Student object to be inserted
     */


    public void updateStudent(Student student) {
        String sql = "UPDATE students SET full_name = ?, email = ?, phone = ?, date_of_birth = ?, gender = ?, " +
                "address = ?, course = ?, admission_date = ? WHERE enrollment_number = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, student.getFullName());
            stmt.setString(2, student.getEmail());
            stmt.setString(3, student.getPhone());
            stmt.setDate(4, java.sql.Date.valueOf(student.getDateOfBirth()));
            stmt.setString(5, student.getGender());
            stmt.setString(6, student.getAddress());
            stmt.setString(7, student.getCourse());
            stmt.setDate(8, java.sql.Date.valueOf(student.getAdmissionDate()));
            stmt.setString(9, student.getEnrollmentNumber());

            int rowsUpdated = stmt.executeUpdate();
            System.out.println(student.getEnrollmentNumber());
            if (rowsUpdated > 0) {
                System.out.println(" Student updated successfully!");
            }

        } catch (SQLException e) {
            System.err.println(" Error updating student: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * delete a student record from the database.
     *
     * @param enrollmentNumber the Student object to be inserted
     */


    public void deleteStudent(String enrollmentNumber) {
        String sql = "DELETE FROM students WHERE enrollment_number = ?";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, enrollmentNumber);

            int rowsDeleted = stmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println(" Student deleted successfully!");
            }

        } catch (SQLException e) {
            System.err.println(" Error deleting student: " + e.getMessage());
            e.printStackTrace();
        }
    }


    /**
     * Retrieve a student record from the database.
     *
     * @param enrollmentNumber the Student object to be inserted
     */


    public Student getStudentByEnrollment(String enrollmentNumber) {
        String sql = "SELECT * FROM students WHERE enrollment_number = ?";
        Student student = null;

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, enrollmentNumber);
            var rs = stmt.executeQuery();

            if (rs.next()) {
                student = new Student();
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                student.setGender(rs.getString("gender"));
                student.setAddress(rs.getString("address"));
                student.setCourse(rs.getString("course"));
                student.setEnrollmentNumber(rs.getString("enrollment_number"));
                student.setAdmissionDate(rs.getDate("admission_date").toLocalDate());
            }

        } catch (SQLException e) {
            System.err.println(" Error retrieving student: " + e.getMessage());
            e.printStackTrace();
        }

        return student;
    }


    /**
     * Fetch all students record from the database.
     *
     *
     */


    public List<Student> getAllStudents() {
        List<Student> students = new ArrayList<>();
        String sql = "SELECT * FROM students";

        try (Connection conn = DBUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Student student = new Student();
                student.setFullName(rs.getString("full_name"));
                student.setEmail(rs.getString("email"));
                student.setPhone(rs.getString("phone"));
                student.setDateOfBirth(rs.getDate("date_of_birth").toLocalDate());
                student.setGender(rs.getString("gender"));
                student.setAddress(rs.getString("address"));
                student.setCourse(rs.getString("course"));
                student.setEnrollmentNumber(rs.getString("enrollment_number"));
                student.setAdmissionDate(rs.getDate("admission_date").toLocalDate());

                students.add(student);
            }

        } catch (SQLException e) {
            System.err.println(" Error retrieving all students: " + e.getMessage());
            e.printStackTrace();
        }

        return students;
    }



}

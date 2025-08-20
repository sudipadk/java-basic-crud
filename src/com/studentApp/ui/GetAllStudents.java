package com.studentApp.ui;

import com.studentApp.dao.StudentDAO;
import com.studentApp.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class GetAllStudents extends JPanel {

    private JTable studentTable;
    private DefaultTableModel tableModel;
    private JButton refreshButton;

    public GetAllStudents() {
        setLayout(new BorderLayout(15, 10));

        // Top panel with Refresh button
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        refreshButton = new JButton("Refresh");
        topPanel.add(refreshButton);
        add(topPanel, BorderLayout.NORTH);

        // Define table columns
        String[] columnNames = {
                "Full Name", "Email", "Phone", "DOB",
                "Gender", "Address", "Course", "Enrollment No", "Admission Date"
        };

        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // make table non-editable
            }
        };

        studentTable = new JTable(tableModel);
        studentTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JScrollPane scrollPane = new JScrollPane(studentTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load data on startup and on refresh
        loadStudents();

        refreshButton.addActionListener(e -> loadStudents());
    }

    private void loadStudents() {
        try {
            tableModel.setRowCount(0); // Clear old data

            List<Student> students = new StudentDAO().getAllStudents();
            DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            for (Student s : students) {
                tableModel.addRow(new Object[]{
                        s.getFullName(),
                        s.getEmail(),
                        s.getPhone(),
                        s.getDateOfBirth().format(df),
                        s.getGender(),
                        s.getAddress(),
                        s.getCourse(),
                        s.getEnrollmentNumber(),
                        s.getAdmissionDate().format(df)
                });
            }

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error loading student data: " + ex.getMessage());
        }
    }
}

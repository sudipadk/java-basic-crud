package com.studentApp.ui;

import com.studentApp.dao.StudentDAO;
import com.studentApp.model.Student;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;

public class ViewStudentDetails extends JPanel {

    private JTextField enrollField;
    private JButton searchButton;

    private JTable detailsTable;
    private DefaultTableModel tableModel;

    public ViewStudentDetails() {
        setLayout(new BorderLayout(20, 10));

        JPanel topPanel = new JPanel(new FlowLayout());
        topPanel.add(new JLabel("Enrollment No:"));
        enrollField = new JTextField(20);
        topPanel.add(enrollField);

        searchButton = new JButton("Search");
        topPanel.add(searchButton);

        add(topPanel, BorderLayout.NORTH);

        // Setup table model with 2 columns: Field and Value
        tableModel = new DefaultTableModel(new Object[]{"Field", "Value"}, 0) {
            // Make cells non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        detailsTable = new JTable(tableModel);
        add(new JScrollPane(detailsTable), BorderLayout.CENTER);

        searchButton.addActionListener(e -> searchStudent());
    }

    private void searchStudent() {
        try {
            String enroll = enrollField.getText().trim();
            if (enroll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Enrollment No to search.");
                return;
            }

            Student student = new StudentDAO().getStudentByEnrollment(enroll);
            if (student != null) {
                displayDetails(student);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
                clearTable();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
            clearTable();
        }
    }

    private void displayDetails(Student student) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // Clear previous data
        clearTable();

        // Add rows: Field name and value
        tableModel.addRow(new Object[]{"Full Name", student.getFullName()});
        tableModel.addRow(new Object[]{"Email", student.getEmail()});
        tableModel.addRow(new Object[]{"Phone", student.getPhone()});
        tableModel.addRow(new Object[]{"Date of Birth", student.getDateOfBirth().format(df)});
        tableModel.addRow(new Object[]{"Gender", student.getGender()});
        tableModel.addRow(new Object[]{"Address", student.getAddress()});
        tableModel.addRow(new Object[]{"Course", student.getCourse()});
        tableModel.addRow(new Object[]{"Enrollment No", student.getEnrollmentNumber()});
        tableModel.addRow(new Object[]{"Admission Date", student.getAdmissionDate().format(df)});
    }

    private void clearTable() {
        tableModel.setRowCount(0);
    }
}

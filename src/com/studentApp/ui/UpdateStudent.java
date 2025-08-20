package com.studentApp.ui;

import com.studentApp.dao.StudentDAO;
import com.studentApp.model.Student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class UpdateStudent extends JPanel {

    private JTextField nameField, emailField, phoneField, dobField, addressField, enrollField, admissionDateField;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private ButtonGroup genderGroup;
    private JComboBox<String> courseDropdown;
    private JButton searchButton, updateButton, deleteButton;

    public UpdateStudent() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

        addLabelField(this, gbc, "Enrollment No:", enrollField = new JTextField(20), row++);

        searchButton = new JButton("Search");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.insets=new Insets(5,5,5,5);
        add(searchButton, gbc);

        addLabelField(this, gbc, "Full Name:", nameField = new JTextField(20), row++);
        addLabelField(this, gbc, "Email:", emailField = new JTextField(20), row++);
        addLabelField(this, gbc, "Phone:", phoneField = new JTextField(20), row++);
        addLabelField(this, gbc, "Date of Birth (YYYY-MM-DD):", dobField = new JTextField(20), row++);

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Gender:"), gbc);

        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        otherRadio = new JRadioButton("Other");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);
        genderGroup.add(otherRadio);

        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        genderPanel.add(maleRadio);
        genderPanel.add(femaleRadio);
        genderPanel.add(otherRadio);

        gbc.gridx = 1;
        add(genderPanel, gbc);
        row++;

        addLabelField(this, gbc, "Address:", addressField = new JTextField(20), row++);

        gbc.gridx = 0;
        gbc.gridy = row;
        add(new JLabel("Course:"), gbc);

        String[] courses = {
                "BBA", "BCA", "CSIT", "BIT", "BIM", "BBM",
                "BSW", "BHM", "Cybersecurity", "BEd", "BCE", "BSE"
        };
        courseDropdown = new JComboBox<>(courses);
        gbc.gridx = 1;
        add(courseDropdown, gbc);
        row++;

        addLabelField(this, gbc, "Admission Date (YYYY-MM-DD):", admissionDateField = new JTextField(20), row++);

        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        add(buttonPanel, gbc);

        // Listeners
        searchButton.addActionListener(e -> searchStudent());
        updateButton.addActionListener(e -> updateStudent());
        deleteButton.addActionListener(e -> deleteStudent());
    }

    private void addLabelField(JPanel panel, GridBagConstraints gbc, String label, JTextField field, int row) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 1;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        panel.add(field, gbc);
    }

    private String getSelectedGender() {
        if (maleRadio.isSelected()) return "Male";
        if (femaleRadio.isSelected()) return "Female";
        if (otherRadio.isSelected()) return "Other";
        return "";
    }

    private void setSelectedGender(String gender) {
        switch (gender) {
            case "Male": maleRadio.setSelected(true); break;
            case "Female": femaleRadio.setSelected(true); break;
            case "Other": otherRadio.setSelected(true); break;
        }
    }

    private Student getStudentFromForm() {
        Student student = new Student();
        student.setEnrollmentNumber(enrollField.getText());
        student.setFullName(nameField.getText());
        student.setEmail(emailField.getText());
        student.setPhone(phoneField.getText());
        student.setDateOfBirth(LocalDate.parse(dobField.getText()));
        student.setGender(getSelectedGender());
        student.setAddress(addressField.getText());
        student.setCourse((String) courseDropdown.getSelectedItem());
        student.setAdmissionDate(LocalDate.parse(admissionDateField.getText()));
        return student;
    }

    private void populateForm(Student student) {
        nameField.setText(student.getFullName());
        emailField.setText(student.getEmail());
        phoneField.setText(student.getPhone());
        dobField.setText(student.getDateOfBirth().toString());
        setSelectedGender(student.getGender());
        addressField.setText(student.getAddress());
        courseDropdown.setSelectedItem(student.getCourse());
        admissionDateField.setText(student.getAdmissionDate().toString());
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dobField.setText("");
        genderGroup.clearSelection();
        addressField.setText("");
        courseDropdown.setSelectedIndex(0);
        admissionDateField.setText("");
    }

    private void searchStudent() {
        try {
            String enroll = enrollField.getText();
            if (enroll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Enrollment No to search.");
                return;
            }

            Student student = new StudentDAO().getStudentByEnrollment(enroll);
            if (student != null) {
                populateForm(student);
            } else {
                JOptionPane.showMessageDialog(this, "Student not found.");
                clearFields();
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void updateStudent() {
        try {
            Student student = getStudentFromForm();
            new StudentDAO().updateStudent(student);
            JOptionPane.showMessageDialog(this, "Student Updated Successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }

    private void deleteStudent() {
        try {
            String enroll = enrollField.getText();
            if (enroll.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please enter Enrollment No to delete.");
                return;
            }

            new StudentDAO().deleteStudent(enroll);
            JOptionPane.showMessageDialog(this, "Student Deleted Successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

package com.studentApp.ui;

import com.studentApp.dao.StudentDAO;
import com.studentApp.model.Student;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class AddStudent extends JPanel {

    private JTextField nameField, emailField, phoneField, dobField, addressField, enrollField, admissionDateField;
    private JRadioButton maleRadio, femaleRadio, otherRadio;
    private ButtonGroup genderGroup;
    private JComboBox<String> courseDropdown;
    private JButton registerButton;

    public AddStudent() {
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int row = 0;

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

        addLabelField(this, gbc, "Enrollment No:", enrollField = new JTextField(20), row++);
        addLabelField(this, gbc, "Admission Date (YYYY-MM-DD):", admissionDateField = new JTextField(20), row++);

        registerButton = new JButton("Register");
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(registerButton, gbc);

        registerButton.addActionListener(e -> registerStudent());
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

    private Student getStudentFromForm() {
        Student student = new Student();
        student.setFullName(nameField.getText());
        student.setEmail(emailField.getText());
        student.setPhone(phoneField.getText());
        student.setDateOfBirth(LocalDate.parse(dobField.getText()));
        student.setGender(getSelectedGender());
        student.setAddress(addressField.getText());
        student.setCourse((String) courseDropdown.getSelectedItem());
        student.setEnrollmentNumber(enrollField.getText());
        student.setAdmissionDate(LocalDate.parse(admissionDateField.getText()));
        return student;
    }

    private void clearFields() {
        nameField.setText("");
        emailField.setText("");
        phoneField.setText("");
        dobField.setText("");
        genderGroup.clearSelection();
        addressField.setText("");
        courseDropdown.setSelectedIndex(0);
        enrollField.setText("");
        admissionDateField.setText("");
    }

    private void registerStudent() {
        try {
            Student student = getStudentFromForm();
            new StudentDAO().addStudent(student);
            JOptionPane.showMessageDialog(this, "Student Registered Successfully!");
            clearFields();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage());
        }
    }
}

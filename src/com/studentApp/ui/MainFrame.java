package com.studentApp.ui;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private JPanel containerPanel;
    private AddStudent addStudentPanel;
    private UpdateStudent updateStudentPanel;
    private ViewStudentDetails viewStudentPanel;
    private GetAllStudents allStudentsPanel;

    public MainFrame() {
        setTitle("Student Management System");
        setSize(800, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Top Menu Panel
        JPanel menuPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton addBtn = new JButton("Add Student");
        JButton updateBtn = new JButton("Update Student");
        JButton viewBtn = new JButton("View Student Details");
        JButton allStudentsBtn = new JButton("All Students");

        menuPanel.add(addBtn);
        menuPanel.add(updateBtn);
        menuPanel.add(viewBtn);
        menuPanel.add(allStudentsBtn);

        add(menuPanel, BorderLayout.NORTH);

        // Card Layout Container
        containerPanel = new JPanel(new CardLayout());

        addStudentPanel = new AddStudent();
        updateStudentPanel = new UpdateStudent();
        viewStudentPanel = new ViewStudentDetails();
        allStudentsPanel = new GetAllStudents();

        containerPanel.add(addStudentPanel, "Add");
        containerPanel.add(updateStudentPanel, "Update");
        containerPanel.add(viewStudentPanel, "View");
        containerPanel.add(allStudentsPanel, "All");

        add(containerPanel, BorderLayout.CENTER);

        // Button Actions
        addBtn.addActionListener(e -> switchPanel("Add"));
        updateBtn.addActionListener(e -> switchPanel("Update"));
        viewBtn.addActionListener(e -> switchPanel("View"));
        allStudentsBtn.addActionListener(e -> switchPanel("All"));

        // Default Panel
        switchPanel("Add");

        setVisible(true);
    }

    private void switchPanel(String panelName) {
        CardLayout cl = (CardLayout) containerPanel.getLayout();
        cl.show(containerPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainFrame::new);
    }
}

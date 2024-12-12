package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class Signup2 extends JFrame implements ActionListener {
    JLabel l1, l2, l3, l4, l5, l6, l7, l8, l9;
    JComboBox<String> religionComboBox, categoryComboBox, incomeComboBox;
    JTextField t5, t6, t7, t8;
    private final ButtonGroup buttonGroup = new ButtonGroup();
    private JRadioButton rdbtnNewRadioButton;
    private JRadioButton rdbtnNewRadioButton_1;
    private JButton btnNewButton;

    Signup2() {
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 2");
        
        l1 = new JLabel("Page 2: Additional Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));
        
        l2 = new JLabel("Religion:");
        l2.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l3 = new JLabel("Category:");
        l3.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l4 = new JLabel("Income:");
        l4.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l5 = new JLabel("Qualification:");
        l5.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l6 = new JLabel("Occupation:"); 
        l6.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l7 = new JLabel("PAN Number:");
        l7.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l8 = new JLabel("Aadhar Number:");
        l8.setFont(new Font("Raleway", Font.BOLD, 20));
        
        l9 = new JLabel("Senior Citizen:");
        l9.setFont(new Font("Raleway", Font.BOLD, 20));
        
        religionComboBox = new JComboBox<>(new String[]{"--select--", "Hinduism", "Islam", "Christianity"});
        religionComboBox.setFont(new Font("Raleway", Font.BOLD, 14));

        categoryComboBox = new JComboBox<>(new String[]{"--select--", "General", "OBC", "SC", "ST"});
        categoryComboBox.setFont(new Font("Raleway", Font.BOLD, 14));

        incomeComboBox = new JComboBox<>(new String[]{"--select--", "Below 1 Lakh", "1-2 Lakh", "2-5 Lakh", "Above 5 Lakh"});
        incomeComboBox.setFont(new Font("Raleway", Font.BOLD, 14));

        t5 = new JTextField();
        t5.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t6 = new JTextField();
        t6.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t7 = new JTextField();
        t7.setFont(new Font("Raleway", Font.BOLD, 14));
        
        t8 = new JTextField();
        t8.setFont(new Font("Raleway", Font.BOLD, 14));
        
        getContentPane().setLayout(null);
        getContentPane().setBackground(new Color(255, 255, 224)); // Light Yellow background color

        l1.setBounds(290, 30, 600, 30);
        getContentPane().add(l1);
        
        l2.setBounds(100, 80, 150, 30);
        getContentPane().add(l2);
        
        l3.setBounds(100, 130, 150, 30);
        getContentPane().add(l3);
        
        l4.setBounds(100, 180, 150, 30);
        getContentPane().add(l4);
        
        l5.setBounds(100, 230, 150, 30);
        getContentPane().add(l5);
        
        l6.setBounds(100, 280, 150, 30);
        getContentPane().add(l6);
        
        l7.setBounds(100, 330, 150, 30);
        getContentPane().add(l7);
        
        l8.setBounds(100, 380, 150, 30);
        getContentPane().add(l8);
        
        l9.setBounds(100, 430, 150, 30);
        getContentPane().add(l9);
        
        religionComboBox.setBounds(300, 80, 400, 30);
        getContentPane().add(religionComboBox);
        
        categoryComboBox.setBounds(300, 130, 400, 30);
        getContentPane().add(categoryComboBox);
        
        incomeComboBox.setBounds(300, 180, 400, 30);
        getContentPane().add(incomeComboBox);
        
        t5.setBounds(300, 230, 400, 30);
        getContentPane().add(t5);
        
        t6.setBounds(300, 280, 400, 30);
        getContentPane().add(t6);
        
        t7.setBounds(300, 330, 400, 30);
        getContentPane().add(t7);
        
        t8.setBounds(300, 380, 400, 30);
        getContentPane().add(t8);
        
        rdbtnNewRadioButton = new JRadioButton("Yes");
        buttonGroup.add(rdbtnNewRadioButton);
        rdbtnNewRadioButton.setBounds(300, 430, 100, 30);
        getContentPane().add(rdbtnNewRadioButton);
        
        rdbtnNewRadioButton_1 = new JRadioButton("No");
        buttonGroup.add(rdbtnNewRadioButton_1);
        rdbtnNewRadioButton_1.setBounds(450, 430, 100, 30);
        getContentPane().add(rdbtnNewRadioButton_1);
        
        btnNewButton = new JButton("Next");
        btnNewButton.setBounds(650, 430, 100, 30);
        getContentPane().add(btnNewButton);
        btnNewButton.addActionListener(this);
        
        setSize(850, 550);
        setLocation(300, 100);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String religion = (String) religionComboBox.getSelectedItem();
        String category = (String) categoryComboBox.getSelectedItem();
        String income = (String) incomeComboBox.getSelectedItem();
        String qualification = t5.getText();
        String occupation = t6.getText();
        String panNumber = t7.getText();
        String aadharNumber = t8.getText();
        
        if (religion.equals("--select--") || category.equals("--select--") || income.equals("--select--") || 
            qualification.isEmpty() || occupation.isEmpty() || panNumber.isEmpty() || aadharNumber.isEmpty()) {
            JOptionPane.showMessageDialog(this, "⚠ All fields are required!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (aadharNumber.length() != 12) {
            JOptionPane.showMessageDialog(this, "⚠ Invalid Aadhar number! It must be 12 digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        } 

        if (panNumber.length() != 10) {
            JOptionPane.showMessageDialog(this, "⚠ Invalid PAN number! It must be 10 digits", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        try (Connection c = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07")) {
            String sql = "INSERT INTO signuptwo (religion, category, income, qualification, occupation, pan_number, aadhar_number) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setString(1, religion);
                pstmt.setString(2, category);
                pstmt.setString(3, income);
                pstmt.setString(4, qualification);
                pstmt.setString(5, occupation);
                pstmt.setString(6, panNumber);
                pstmt.setString(7, aadharNumber);
                pstmt.executeUpdate();
            }
            JOptionPane.showMessageDialog(this, "Details saved successfully.");
            setVisible(false);
            new Signup3().setVisible(true);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Database connection error!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Signup2();
    }
}

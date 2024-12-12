package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.Calendar;

public class Signup1 extends JFrame implements ActionListener {
    // Declare the components
    JTextField nameField, emailField, addressField;
    JButton nextButton;
    JRadioButton maleButton, femaleButton, marriedButton, unmarriedButton;
    JComboBox<Integer> dayComboBox, monthComboBox, yearComboBox;
    ButtonGroup genderGroup, maritalGroup;
    private JTextField mobilen;

    public Signup1() {
        // Set up the frame
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 1");
        setSize(761, 583);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setLayout(null); // Use absolute positioning
        getContentPane().setBackground(new Color(255, 255, 224));

        // Initialize the components
        nameField = new JTextField();
        nameField.setBounds(200, 100, 354, 24);

        emailField = new JTextField();
        emailField.setBounds(200, 309, 354, 24);

        addressField = new JTextField();
        addressField.setBounds(200, 415, 354, 24);

        nextButton = new JButton("Next");
        nextButton.setBounds(473, 478, 79, 36);
        nextButton.addActionListener(this);

        // Create labels
        JLabel titleLabel = new JLabel("APPLICATION FORM NO.", JLabel.CENTER);
        titleLabel.setBounds(150, 20, 500, 24);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel pageLabel = new JLabel("Page 1: Personal Details", JLabel.CENTER);
        pageLabel.setBounds(150, 50, 500, 19);
        pageLabel.setFont(new Font("Arial", Font.PLAIN, 16));

        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 100, 100, 24);

        JLabel dobLabel = new JLabel("Date of Birth:");
        dobLabel.setBounds(50, 150, 100, 27);

        JLabel genderLabel = new JLabel("Gender:");
        genderLabel.setBounds(50, 204, 100, 27);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 308, 100, 27);

        JLabel maritalLabel = new JLabel("Marital Status:");
        maritalLabel.setBounds(50, 368, 100, 19);

        JLabel addressLabel = new JLabel("Address:");
        addressLabel.setBounds(50, 417, 100, 21);

        // Gender Radio Buttons
        genderGroup = new ButtonGroup();
        maleButton = new JRadioButton("Male");
        maleButton.setBounds(200, 206, 111, 23);
        maleButton.setActionCommand("Male"); // Set ActionCommand
        femaleButton = new JRadioButton("Female");
        femaleButton.setBounds(350, 206, 111, 23);
        femaleButton.setActionCommand("Female"); // Set ActionCommand
        genderGroup.add(maleButton);
        genderGroup.add(femaleButton);

        // Marital Status Radio Buttons
        maritalGroup = new ButtonGroup();
        marriedButton = new JRadioButton("Married");
        marriedButton.setBounds(200, 366, 111, 23);
        marriedButton.setActionCommand("Married"); // Set ActionCommand
        unmarriedButton = new JRadioButton("Unmarried");
        unmarriedButton.setBounds(350, 366, 111, 23);
        unmarriedButton.setActionCommand("Unmarried"); // Set ActionCommand
        maritalGroup.add(marriedButton);
        maritalGroup.add(unmarriedButton);

        // Date of Birth ComboBoxes
        dayComboBox = new JComboBox<>();
        for (int i = 1; i <= 31; i++) {
            dayComboBox.addItem(i);
        }
        dayComboBox.setBounds(200, 150, 50, 24);

        monthComboBox = new JComboBox<>();
        for (int i = 1; i <= 12; i++) {
            monthComboBox.addItem(i);
        }
        monthComboBox.setBounds(270, 150, 50, 24);

        yearComboBox = new JComboBox<>();
        int currentYear = Calendar.getInstance().get(Calendar.YEAR);
        for (int i = currentYear; i >= 1900; i--) {
            yearComboBox.addItem(i);
        }
        yearComboBox.setBounds(340, 150, 80, 24);

        // Add components to the frame
        getContentPane().add(titleLabel);
        getContentPane().add(pageLabel);
        getContentPane().add(nameLabel);
        getContentPane().add(dobLabel);
        getContentPane().add(genderLabel);
        getContentPane().add(emailLabel);
        getContentPane().add(maritalLabel);
        getContentPane().add(addressLabel);
        getContentPane().add(nameField);
        getContentPane().add(dayComboBox);
        getContentPane().add(monthComboBox);
        getContentPane().add(yearComboBox);
        getContentPane().add(maleButton);
        getContentPane().add(femaleButton);
        getContentPane().add(emailField);
        getContentPane().add(marriedButton);
        getContentPane().add(unmarriedButton);
        getContentPane().add(addressField);
        getContentPane().add(nextButton);

        JLabel lblNewLabel = new JLabel("Mobile No.:");
        lblNewLabel.setBounds(50, 259, 79, 14);
        getContentPane().add(lblNewLabel);

        mobilen = new JTextField();
        mobilen.setBounds(200, 254, 354, 24);
        getContentPane().add(mobilen);
        mobilen.setColumns(10);

        // Make the frame visible
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == nextButton) {
            // Collect data from fields
            String name = nameField.getText();
            String gender = genderGroup.getSelection() != null ? genderGroup.getSelection().getActionCommand() : "";
            int day = (int) dayComboBox.getSelectedItem();
            int month = (int) monthComboBox.getSelectedItem();
            int year = (int) yearComboBox.getSelectedItem();
            String dob = year + "-" + String.format("%02d", month) + "-" + String.format("%02d", day);
            String email = emailField.getText().trim();
            String marital = maritalGroup.getSelection() != null ? maritalGroup.getSelection().getActionCommand() : "";
            String address = addressField.getText();
            String mobileno = mobilen.getText();

            // Validation
            if (name.trim().isEmpty() || email.trim().isEmpty() || address.trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "❌ Please fill all the required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (mobileno.length() != 10 || !mobileno.matches("\\d+")) {
                JOptionPane.showMessageDialog(this, "❌ Invalid mobile number. It must be 10 digits.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!email.contains("@") || !email.contains(".")) {
                JOptionPane.showMessageDialog(this, "❌ Invalid email. It must contain '@' and a domain.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Connection conn = null;
            PreparedStatement pstmt = null;

            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");

                // Insert data into the signup table
                String sql = "INSERT INTO signup (name, gender, dob, email, marital, address, mobileno) " +
                             "VALUES (?, ?, ?, ?, ?, ?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setString(1, name);
                pstmt.setString(2, gender);
                pstmt.setString(3, dob);
                pstmt.setString(4, email);
                pstmt.setString(5, marital);
                pstmt.setString(6, address);
                pstmt.setString(7, mobileno);

                pstmt.executeUpdate();

                // Open Signup2
                new Signup2().setVisible(true);

                // Optionally, close the current frame
                this.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                try {
                    if (pstmt != null) pstmt.close();
                    if (conn != null) conn.close();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Signup1());
    }
}

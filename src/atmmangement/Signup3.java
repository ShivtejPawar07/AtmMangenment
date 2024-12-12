package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Random;

public class Signup3 extends JFrame implements ActionListener {

    JLabel l1, l2, l5, lblNewLabel, lblNewLabel_1;
    JTextField t4, textField;
    JButton b;
    JComboBox<String> comboBox, comboBox_1;

    Connection c;
    Statement s;

    Signup3() {
        setTitle("NEW ACCOUNT APPLICATION FORM - PAGE 3");

        // Initialize JDBC connection
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }

        l1 = new JLabel("Page 3: Account Details");
        l1.setFont(new Font("Raleway", Font.BOLD, 22));

        l2 = new JLabel("Account Type:");
        l2.setFont(new Font("Raleway", Font.BOLD, 20));

        l5 = new JLabel("Services Required:");
        l5.setFont(new Font("Dialog", Font.BOLD, 20));

        t4 = new JTextField();
        t4.setFont(new Font("Raleway", Font.BOLD, 14));

        b = new JButton("Submit");
        b.setFont(new Font("Raleway", Font.BOLD, 14));
        b.setBackground(Color.LIGHT_GRAY);
        b.setForeground(Color.WHITE);

        // Set light green background for submit button
        b.setBackground(new Color(144, 238, 144));

        getContentPane().setLayout(null);

        l1.setBounds(290, 80, 600, 30);
        getContentPane().add(l1);

        l2.setBounds(74, 137, 200, 30);
        getContentPane().add(l2);

        l5.setBounds(74, 197, 200, 30);
        getContentPane().add(l5);

        t4.setBounds(300, 200, 400, 30);
        getContentPane().add(t4);

        b.setBounds(415, 398, 120, 50);
        getContentPane().add(b);

        b.addActionListener(this);

        getContentPane().setBackground(new Color(255, 255, 204)); // Light yellow background

        lblNewLabel = new JLabel("Nominee Name:");
        lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 20));
        lblNewLabel.setBounds(80, 253, 163, 35);
        getContentPane().add(lblNewLabel);

        textField = new JTextField();
        textField.setBounds(300, 259, 400, 30);
        getContentPane().add(textField);
        textField.setColumns(10);

        lblNewLabel_1 = new JLabel("Nominee Relation:");
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
        lblNewLabel_1.setBounds(80, 313, 163, 35);
        getContentPane().add(lblNewLabel_1);

        comboBox = new JComboBox<>();
        comboBox.setModel(new DefaultComboBoxModel<>(new String[]{"--select--", "Close Friend", "Boyfriend", "Girlfriend", "Mother", "Father", "Sister", "Brother", "Wife", "Daughter", "Son", "Grandmother", "Grandfather"}));
        comboBox.setBounds(300, 319, 206, 30);
        getContentPane().add(comboBox);

        comboBox_1 = new JComboBox<>();
        comboBox_1.setModel(new DefaultComboBoxModel<>(new String[]{"--select--", "savings", "current", "business"}));
        comboBox_1.setBounds(300, 145, 206, 30);
        getContentPane().add(comboBox_1);

        setSize(850, 800);
        setLocation(400, 20);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        String accountType = (String) comboBox_1.getSelectedItem();
        String services = t4.getText();
        String nomineeName = textField.getText();
        String nomineeRelation = (String) comboBox.getSelectedItem();

        // Basic validation
        if (accountType.equals("--select--") || services.isEmpty() || nomineeName.isEmpty() || nomineeRelation.equals("--select--")) {
            JOptionPane.showMessageDialog(null, "Please fill all required fields", "Validation Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Generate random form number, ATM card number, and PIN
        Random random = new Random();
        String formNo = String.format("%04d", random.nextInt(10000));
        String atmCardNo = String.format("%016d", Math.abs(random.nextLong() % 10000000000000000L));
        String pin = String.format("%04d", random.nextInt(10000));

        try {
            String query = "INSERT INTO signupthree (form_no, account_type, services, nominee_name, nominee_relation, atm_card_no, pin) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = c.prepareStatement(query);
            stmt.setString(1, formNo);
            stmt.setString(2, accountType);
            stmt.setString(3, services);
            stmt.setString(4, nomineeName);
            stmt.setString(5, nomineeRelation);
            stmt.setString(6, atmCardNo);
            stmt.setString(7, pin);
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(null, "Account Created Successfully\n" +
                        "Form No: " + formNo + "\nATM Card No: " + atmCardNo + "\nPIN: " + pin);
                new LoginPage().setVisible(true);
                setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Error creating account");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        new Signup3().setVisible(true);
    }
}

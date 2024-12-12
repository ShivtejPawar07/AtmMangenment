package atmmangement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;
import java.math.BigDecimal;

public class BalanceEnq extends JFrame implements ActionListener {
    String pin;

    BalanceEnq(String pin) {
        this.pin = pin;
        setTitle("Balance Enquiry");
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.PINK);
        // Label to display the balance
        JLabel text = new JLabel("Your Current Balance is Rs ");
        text.setBounds(190, 200, 400, 35);
        getContentPane().add(text);

        // Initialize balance
        BigDecimal balance = BigDecimal.ZERO;

        try {
            // Establish connection to the database
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");

            // SQL query to get the balance by summing deposits and withdrawals
            String query = "SELECT SUM(CASE WHEN type = 'Deposit' THEN CAST(amount AS DECIMAL(20,2)) ELSE -CAST(amount AS DECIMAL(20,2)) END) AS balance FROM bank WHERE pin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, pin);

            // Execute the query and obtain the result set
            ResultSet rs = preparedStatement.executeQuery();

            // Retrieve the balance
            if (rs.next()) {
                balance = rs.getBigDecimal("balance");
            }

            // Display the current balance
            text.setText("Your Current Balance is Rs " + balance.toString());

            // Close resources
            rs.close();
            preparedStatement.close();
            connection.close();

        } catch (Exception e) {
            e.printStackTrace();
           
        }

        // Back button
        JButton back = new JButton("Back");
        back.setBounds(355, 388, 150, 35);
        getContentPane().add(back);
        back.addActionListener(this);

        setSize(800, 800);
        setLocation(300, 0);
        setVisible(true);
    }

    // ActionListener method to handle button clicks
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() instanceof JButton) {
            setVisible(false);
            new transaction(pin).setVisible(true);
        }
    }

    // Main method for testing
    public static void main(String[] args) {
        new BalanceEnq("").setVisible(true); 
    }
}

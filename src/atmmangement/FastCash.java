package atmmangement;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class FastCash extends JFrame implements ActionListener {
    JButton withdraw100, withdraw500, withdraw1000, withdraw2000, withdraw5000, withdraw10000, back;
    String pinNumber;

    FastCash(String pinNumber) {
        this.pinNumber = pinNumber;
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.PINK); // Set background color to light red

        // Set up buttons and their properties
        withdraw100 = new JButton("Rs 100");
        withdraw100.setBounds(170, 100, 150, 30);
        getContentPane().add(withdraw100);
        withdraw100.addActionListener(this);

        withdraw500 = new JButton("Rs 500");
        withdraw500.setBounds(170, 150, 150, 30);
        getContentPane().add(withdraw500);
        withdraw500.addActionListener(this);

        withdraw1000 = new JButton("Rs 1000");
        withdraw1000.setBounds(170, 200, 150, 30);
        getContentPane().add(withdraw1000);
        withdraw1000.addActionListener(this);

        withdraw2000 = new JButton("Rs 2000");
        withdraw2000.setBounds(170, 250, 150, 30);
        getContentPane().add(withdraw2000);
        withdraw2000.addActionListener(this);

        withdraw5000 = new JButton("Rs 5000");
        withdraw5000.setBounds(170, 300, 150, 30);
        getContentPane().add(withdraw5000);
        withdraw5000.addActionListener(this);

        withdraw10000 = new JButton("Rs 10000");
        withdraw10000.setBounds(170, 350, 150, 30);
        getContentPane().add(withdraw10000);
        withdraw10000.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(170, 410, 150, 30);
        getContentPane().add(back);
        back.addActionListener(this);

        setSize(800, 600);
        setLocation(300, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == back) {
            // If back button is clicked, return to the transactions page
            setVisible(false);
            new transaction(pinNumber).setVisible(true);
            return;
        }

        String amountStr = ((JButton) ae.getSource()).getText().substring(3);
        int amount = Integer.parseInt(amountStr);

        // Perform fast cash withdrawal logic here
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");
            Statement statement = connection.createStatement();

            // Check the current balance by summing up the deposits and withdrawals
            String queryBalance = "SELECT SUM(CASE WHEN type = 'Deposit' THEN CAST(amount AS SIGNED) ELSE -CAST(amount AS SIGNED) END) AS balance FROM bank WHERE pin = '" + pinNumber + "'";
            ResultSet resultSet = statement.executeQuery(queryBalance);

            if (resultSet.next()) {
                int balance = resultSet.getInt("balance");

                if (balance >= amount) {
                    // Perform withdrawal
                    String queryWithdraw = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + pinNumber + "', CURRENT_TIMESTAMP, 'Withdrawal', '" + amount + "')";
                    statement.executeUpdate(queryWithdraw);

                    JOptionPane.showMessageDialog(null, "Rs " + amount + " Debited Successfully");

                } else {
                    JOptionPane.showMessageDialog(null, "Insufficient Balance");
                }
            }

            resultSet.close();
            statement.close();
            connection.close();

            setVisible(false);
            new transaction(pinNumber).setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new FastCash("").setVisible(true); 
    }
}

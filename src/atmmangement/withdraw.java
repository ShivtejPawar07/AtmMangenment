package atmmangement;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class withdraw extends JFrame implements ActionListener {
    JTextField amount;
    JButton withdraw, back;
    String pinNumber;

    withdraw(String pinNumber) {
        this.pinNumber = pinNumber;
        getContentPane().setBackground(Color.PINK); // Set background color to light red

        // Set up the JFrame
        setLayout(null);

        JLabel text = new JLabel("Enter the amount you want to withdraw");
        text.setBounds(190, 200, 400, 35);
        add(text);

        amount = new JTextField();
        amount.setBounds(190, 260, 320, 25);
        add(amount);

        withdraw = new JButton("Withdraw"); 
        withdraw.setBounds(355, 388, 150, 35);
        add(withdraw);
        withdraw.addActionListener(this);

        back = new JButton("Back");
        back.setBounds(355, 433, 150, 35);
        add(back);
        back.addActionListener(this);

        setSize(800, 800);
        setLocation(300, 0);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == withdraw) {
            String numberStr = amount.getText();
            int withdrawAmount;

            try {
                withdrawAmount = Integer.parseInt(numberStr);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid amount");
                return;
            }

            if (withdrawAmount <= 0) {
                JOptionPane.showMessageDialog(null, "Please enter an amount greater than zero");
                return;
            }

            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07"+ "");
                Statement statement = connection.createStatement();

                // Check the current balance by summing up the deposits and withdrawals
                String queryBalance = "SELECT SUM(CASE WHEN type = 'Deposit' THEN CAST(amount AS SIGNED) ELSE -CAST(amount AS SIGNED) END) AS balance FROM bank WHERE pin = '" + pinNumber + "'";
                ResultSet rs = statement.executeQuery(queryBalance);

                if (rs.next()) {
                    int balance = rs.getInt("balance");

                    if (balance < withdrawAmount) {
                        JOptionPane.showMessageDialog(null, "Insufficient Balance");
                        return;
                    }

                    // Proceed with the withdrawal
                    String queryWithdraw = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + pinNumber + "', CURRENT_TIMESTAMP, 'Withdrawal', '" + numberStr + "')";
                    statement.executeUpdate(queryWithdraw);
                    JOptionPane.showMessageDialog(null, "Rs " + numberStr + " Debited Successfully");

                    setVisible(false);
                    new transaction(pinNumber).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Unable to retrieve balance. Please try again.");
                }

                rs.close();
                statement.close();
                connection.close();

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new transaction(pinNumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new withdraw("").setVisible(true);
    }
}

package atmmangement;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.sql.*;

public class deposit extends JFrame implements ActionListener {
    JTextField amount;
    JButton deposit, back;
    String pinNumber;
    
    deposit(String pinNumber) {
        this.pinNumber = pinNumber;
        
        // Set up the JFrame
        getContentPane().setLayout(null);
        
        JLabel text = new JLabel("Enter the amount you want to deposit");
        text.setBounds(190, 200, 400, 35);
        getContentPane().add(text);
        getContentPane().setBackground(Color.PINK);
        amount = new JTextField();
        amount.setBounds(190, 260, 320, 25);
        getContentPane().add(amount);
        
        deposit = new JButton("Deposit");
        deposit.setBounds(355, 388, 150, 35);
        getContentPane().add(deposit);
        deposit.addActionListener(this);
        
        back = new JButton("Back");
        back.setBounds(355, 433, 150, 35);
        getContentPane().add(back);
        back.addActionListener(this);
        
        setSize(800, 800);
        setLocation(300, 0);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deposit) {
            String number = amount.getText();
            // Perform deposit logic here
            
            try {
                Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");
                Statement statement = connection.createStatement();
                String query = "INSERT INTO bank (pin, date, type, amount) VALUES ('" + pinNumber + "', CURRENT_TIMESTAMP, 'Deposit', '" + number + "')";
                statement.executeUpdate(query);
                JOptionPane.showMessageDialog(null, "Rs " + number + " Credited Successfully");
                
                setVisible(false);
                new transaction(pinNumber).setVisible(true);
                
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new transaction(pinNumber).setVisible(true);
        }
    }
    
    public static void main(String[] args) {
        new deposit("").setVisible(true);
    }
}

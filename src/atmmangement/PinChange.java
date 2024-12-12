package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class PinChange extends JFrame implements ActionListener {
    JButton changePinButton, back;
    JPasswordField newPinField, confirmPinField;
    String pinNumber;

    PinChange(String pinNumber) {
        this.pinNumber = pinNumber;
        setLayout(null);
        getContentPane().setBackground(Color.PINK); // Set background color to pink

        JLabel newPinLabel = new JLabel("New PIN:");
        newPinLabel.setFont(new Font("System", Font.BOLD, 20));
        newPinLabel.setBounds(100, 150, 150, 30);
        add(newPinLabel);

        newPinField = new JPasswordField();
        newPinField.setBounds(250, 150, 200, 30);
        add(newPinField);

        JLabel confirmPinLabel = new JLabel("Confirm PIN:");
        confirmPinLabel.setFont(new Font("System", Font.BOLD, 20));
        confirmPinLabel.setBounds(100, 200, 150, 30);
        add(confirmPinLabel);

        confirmPinField = new JPasswordField();
        confirmPinField.setBounds(250, 200, 200, 30);
        add(confirmPinField);

        changePinButton = new JButton("Change PIN");
        changePinButton.setBounds(100, 250, 150, 30);
        changePinButton.addActionListener(this);
        add(changePinButton);

        back = new JButton("Back");
        back.setBounds(250, 250, 150, 30);
        back.addActionListener(this);
        add(back);

        setSize(500, 400);
        setLocation(300, 200);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == changePinButton) {
            String newPin = new String(newPinField.getPassword());
            String confirmPin = new String(confirmPinField.getPassword());

            if (newPin.equals(confirmPin)) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");
                    Statement statement = connection.createStatement();

                    String query = "UPDATE signupthree SET pin = '" + newPin + "' WHERE pin = '" + pinNumber + "'";
                    statement.executeUpdate(query);

                    JOptionPane.showMessageDialog(null, "PIN Changed Successfully");

                    setVisible(false);
                    new transaction(newPin).setVisible(true);

                    statement.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(null, "PINs do not match");
            }
        } else if (ae.getSource() == back) {
            setVisible(false);
            new transaction(pinNumber).setVisible(true);
        }
    }

    public static void main(String[] args) {
        new PinChange("").setVisible(true);
    }
}

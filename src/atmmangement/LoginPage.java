package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginPage extends JFrame implements ActionListener {
    JLabel l1, l2, l3;
    JTextField tf1;
    JPasswordField pf2;
    JButton b1, b2, b3;

    Connection connection;
    PreparedStatement preparedStatement;

    LoginPage() {
        setTitle("ATM MANAGEMENT SYSTEM");

        // Add gradient background
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                int width = getWidth();
                int height = getHeight();
                Color color1 = new Color(173, 216, 230); // Light blue
                Color color2 = new Color(240, 248, 255); // Alice blue
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, height, color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, width, height);
            }
        };
        setContentPane(gradientPanel);
        gradientPanel.setLayout(null);

        // Header label
        l1 = new JLabel("WELCOME TO ATM");
        l1.setFont(new Font("Osward", Font.BOLD, 38));
        l1.setForeground(Color.RED); // Set text color to red

        // Field labels
        l2 = new JLabel("Card No:");
        l2.setFont(new Font("Raleway", Font.BOLD, 28));
        l2.setForeground(new Color(54, 73, 93)); // Medium gray-blue

        l3 = new JLabel("PIN:");
        l3.setFont(new Font("Raleway", Font.BOLD, 28));
        l3.setForeground(new Color(54, 73, 93));

        tf1 = new JTextField(15);
        tf1.setFont(new Font("Arial", Font.BOLD, 14));
        tf1.setBackground(new Color(224, 236, 244)); // Light blue-gray
        tf1.setBorder(BorderFactory.createLineBorder(new Color(169, 188, 208)));

        pf2 = new JPasswordField(15);
        pf2.setFont(new Font("Arial", Font.BOLD, 14));
        pf2.setBackground(new Color(224, 236, 244));
        pf2.setBorder(BorderFactory.createLineBorder(new Color(169, 188, 208)));

        b1 = new JButton("SIGN IN");
        styleButton(b1);

        b2 = new JButton("CLEAR");
        styleButton(b2);

        b3 = new JButton("SIGN UP");
        styleButton(b3);

        l1.setBounds(175, 50, 450, 200);
        gradientPanel.add(l1);

        l2.setBounds(125, 150, 375, 200);
        gradientPanel.add(l2);

        l3.setBounds(125, 225, 375, 200);
        gradientPanel.add(l3);

        tf1.setBounds(300, 235, 230, 30);
        gradientPanel.add(tf1);

        pf2.setBounds(300, 310, 230, 30);
        gradientPanel.add(pf2);

        b1.setBounds(300, 400, 100, 30);
        gradientPanel.add(b1);

        b2.setBounds(430, 400, 100, 30);
        gradientPanel.add(b2);

        b3.setBounds(300, 450, 230, 30);
        gradientPanel.add(b3);

        b1.addActionListener(this);
        b2.addActionListener(this);
        b3.addActionListener(this);

        setSize(800, 800);
        setLocation(300, 40);
        setVisible(true);
    }

    private void styleButton(JButton button) {
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setBackground(new Color(70, 130, 180)); // Steel blue
        button.setForeground(Color.WHITE);
        button.setBorder(BorderFactory.createEmptyBorder());
        button.setFocusPainted(false);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(100, 149, 237)); // Lighter blue
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(70, 130, 180));
            }
        });
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        try {
            if (ae.getSource() == b1) {
                String cardno = tf1.getText();
                String pin = new String(pf2.getPassword());

                if (cardno.isEmpty() || pin.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Card Number or PIN cannot be empty", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");

                String query = "SELECT * FROM signupthree WHERE atm_card_no = ? AND pin = ?";
                preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, cardno);
                preparedStatement.setString(2, pin);

                ResultSet rs = preparedStatement.executeQuery();

                if (rs.next()) {
                    String insertQuery = "INSERT INTO login (formno, cardNumber, PinNumber) VALUES (?, ?, ?)";
                    PreparedStatement insertStmt = connection.prepareStatement(insertQuery);
                    insertStmt.setString(1, rs.getString("form_no"));
                    insertStmt.setString(2, cardno);
                    insertStmt.setString(3, pin);
                    insertStmt.executeUpdate();
                    insertStmt.close();

                    new transaction(pin).setVisible(true);
                    setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(null, "Incorrect Card Number or PIN", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }

                rs.close();
                preparedStatement.close();
                connection.close();

            } else if (ae.getSource() == b2) {
                tf1.setText("");
                pf2.setText("");
            } else if (ae.getSource() == b3) {
                new Signup1().setVisible(true);
                setVisible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new LoginPage().setVisible(true);
    }
}

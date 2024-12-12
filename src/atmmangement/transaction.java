package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class transaction extends JFrame implements ActionListener {
    JButton deposit, withdraw, fastCash, miniStatement, pinChange, balanceEnquiry, exit;
    String pinNumber;
    private JLabel lblNewLabel;
    
    transaction(String pinNumber) {
        this.pinNumber = pinNumber;
        getContentPane().setLayout(null);
        
        JLabel text = new JLabel("Please select your Transaction");
        text.setForeground(new Color(0, 255, 255));
        text.setFont(new Font("System", Font.BOLD, 38));
        text.setBounds(76, 66, 700, 40);
        getContentPane().add(text);
        
        deposit = new JButton("Deposit");
        deposit.setBounds(170, 250, 150, 30);
        deposit.addActionListener(this);
        getContentPane().add(deposit);
        
        withdraw = new JButton("Withdraw");
        withdraw.setBounds(355, 250, 150, 30);
        withdraw.addActionListener(this);
        getContentPane().add(withdraw);
        
        fastCash = new JButton("Fast Cash");
        fastCash.setBounds(170, 300, 150, 30);
        fastCash.addActionListener(this);
        getContentPane().add(fastCash);
        
        miniStatement = new JButton("Mini Statement");
        miniStatement.setBounds(355, 300, 150, 30);
        miniStatement.addActionListener(this);
        getContentPane().add(miniStatement);
        
        pinChange = new JButton("Pin Change");
        pinChange.setBounds(170, 350, 150, 30);
        pinChange.addActionListener(this);
        getContentPane().add(pinChange);
        
        balanceEnquiry = new JButton("Balance Enquiry");
        balanceEnquiry.setBounds(355, 350, 150, 30);
        balanceEnquiry.addActionListener(this);
        getContentPane().add(balanceEnquiry);
        
        exit = new JButton("Exit");
        exit.setBounds(355, 400, 150, 30);
        exit.addActionListener(this);
        getContentPane().add(exit);
        
        setSize(800, 800);
        setLocation(300, 0);
        getContentPane().setBackground(Color.WHITE);
        
        lblNewLabel = new JLabel("");
        lblNewLabel.setIcon(new ImageIcon("D:\\java project\\AtmMangenment\\images\\bank2.jpg"));
        lblNewLabel.setBounds(-13, 11, 711, 649);
        getContentPane().add(lblNewLabel);
        setVisible(true);
    }
    
    public void actionPerformed(ActionEvent ae) {
        if (ae.getSource() == deposit) {
            new deposit(pinNumber).setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == withdraw) {
            new withdraw(pinNumber).setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == fastCash) {
            new FastCash(pinNumber).setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == miniStatement) {
            new MiniStatement(pinNumber).setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == pinChange) {
            new PinChange(pinNumber).setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == balanceEnquiry) {
            new BalanceEnq(pinNumber).setVisible(true);
            setVisible(false);
        } else if (ae.getSource() == exit) {
            System.exit(0);
        }
    }
    
    public static void main(String[] args) {
        new transaction("").setVisible(true);
    }
}
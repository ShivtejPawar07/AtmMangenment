package atmmangement;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class MiniStatement extends JFrame {
    JTextArea textArea;
    String pinNumber;

    MiniStatement(String pinNumber) {
        this.pinNumber = pinNumber;
        setLayout(new BorderLayout());

        textArea = new JTextArea();
        textArea.setEditable(false);
        add(new JScrollPane(textArea), BorderLayout.CENTER);

        JButton back = new JButton("Back");
        add(back, BorderLayout.SOUTH);
        back.addActionListener(e -> {
            setVisible(false);
            new transaction(pinNumber).setVisible(true);
        });

        setSize(500, 400);
        setLocation(300, 200); 
        getContentPane().setBackground(Color.WHITE);
        setVisible(true);

        fetchMiniStatement();
    }

    private void fetchMiniStatement() {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");
            Statement statement = connection.createStatement();

            String query = "SELECT * FROM bank WHERE pin = '" + pinNumber + "' ORDER BY date DESC LIMIT 10";
            ResultSet resultSet = statement.executeQuery(query);

            StringBuilder statementBuilder = new StringBuilder();
            while (resultSet.next()) {
                statementBuilder.append(resultSet.getString("date")).append(" ")
                        .append(resultSet.getString("type")).append(" ")
                        .append(resultSet.getString("amount")).append("\n");
            }

            textArea.setText(statementBuilder.toString());

            resultSet.close();
            statement.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new MiniStatement("1234").setVisible(true);
    }
}
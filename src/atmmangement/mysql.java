package atmmangement;
import java.sql.*;

public class mysql {
    Connection c;
    Statement s;

    public mysql() {
        try {
            // Adjust the URL, username, and password as per your database configuration
            Class.forName("com.mysql.cj.jdbc.Driver");
            c = DriverManager.getConnection("jdbc:mysql://localhost:3306/atm?useSSL=false", "root", "Shivtej@07");
            s = c.createStatement();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
 
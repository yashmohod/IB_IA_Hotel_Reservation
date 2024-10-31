package loop;
import java.sql.*;

/**
 *
 * @author Yash
 */
public class connect {
    
    Connection cin = null;
    public static Connection connects()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            Connection con = DriverManager.getConnection("jdbc:sqlite:Loop_hotel.db");
            System.out.println("Connected to sqlite");
            return con;
        }
        catch(Exception e )
        {
            System.out.println("Not Connected to sqlite");
            return null;
        }
    }
    
   // public static void main(String arg[]){connects();}
    
    
}

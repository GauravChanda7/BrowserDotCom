package gauravchanda7.browserdotcom;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {

    private Connection connection;

    public DBController(){
        String DB_URL = "jdbc:sqlite:C:\\Users\\KIIT\\Desktop\\Project\\BrowserDotCom\\src\\main\\resources\\gauravchanda7\\browserdotcom\\BrowserDB.db";
        try {
            connection = DriverManager.getConnection(DB_URL);
            //System.out.println("history.db Connection Successful");
        } catch (SQLException e){
            System.out.println("history.db Connection Failed");
            e.printStackTrace();
        }
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection(){
        try{
            if (connection != null) {
                connection.close();
                System.out.println("history.db connection closed");
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }
}

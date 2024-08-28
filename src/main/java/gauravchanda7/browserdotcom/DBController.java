package gauravchanda7.browserdotcom;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBController {

    private Connection connection;

    public DBController(){
        final String dbPath = new File("src/main/resources/gauravchanda7/browserdotcom/BrowserDB.db").getAbsolutePath();
        final String db_URL = "jdbc:sqlite:" + dbPath;
        try {
            connection = DriverManager.getConnection(db_URL);
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

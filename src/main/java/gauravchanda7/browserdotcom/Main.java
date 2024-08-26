package gauravchanda7.browserdotcom;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DBController db = new DBController();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("tabpage.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);

        InputStream iconStream = getClass().getResourceAsStream("Logo.png");
        if (iconStream != null) {
            Image icon = new Image(iconStream);
            stage.getIcons().add(icon);
        } else {
            System.err.println("Logo.png not found!");
        }

        stage.setTitle("Browser.Com");
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();

        Connection connection = db.getConnection();
        if (connection != null){
            try {
                Statement statement = connection.createStatement();
                String createTable = "CREATE TABLE IF NOT EXISTS browser_history (" +
                        "    Site_Id INTEGER PRIMARY KEY AUTOINCREMENT," +
                        "    Site_Title TEXT," +
                        "    Site_URL TEXT NOT NULL," +
                        "    Site_Visit_Date TEXT NOT NULL," +
                        "    Site_Visit_Time TEXT NOT NULL" +
                        ");";

                statement.executeUpdate(createTable);
                db.closeConnection();

            } catch (SQLException e){
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

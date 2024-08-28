package gauravchanda7.browserdotcom;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TabPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;

public class HistoryPageController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button clearHistoryButton;

    @FXML
    private ImageView historyImageView;

    @FXML
    private ScrollPane scrollPane;

    @FXML
    private Text titleText;

    private TabPageController tabPageController;

    public void setTabPageController(TabPageController tabPageController) {
        this.tabPageController = tabPageController;
    }

    @FXML
    void clearAllHistory(ActionEvent event) {
        DBController db = new DBController();
        Connection connection = db.getConnection();
        if (connection != null){
            try {
                Statement statement = connection.createStatement();
                String clearAllHistory = "DELETE FROM browser_history";
                statement.executeUpdate(clearAllHistory);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        db.closeConnection();
    }

    public HBox formatRecord(String site_Visit_Time, String site_Title, String site_URL){
        HBox hBox = new HBox(10);
        Text time = new Text(site_Visit_Time);
        time.setFont(new Font(15));
        Button siteButton = new Button(site_Title);
        siteButton.setOnAction(event -> {
            if (tabPageController != null) {
                tabPageController.createTabWithURL(site_URL);
            } else {
                System.err.println("TabPageController is not initialized.1");
            }
        });
        Button deleteButton = new Button("Delete");
        siteButton.setAlignment(Pos.CENTER_LEFT);
        siteButton.setPrefSize(500.0, 15.0);
        deleteButton.setPrefHeight(15.0);
        deleteButton.setStyle("-fx-text-fill: red;");
        hBox.getChildren().addAll(time, siteButton, deleteButton);
        return hBox;
    }


    public VBox setHistoryPage(){
        VBox vBox = new VBox(10);
        DBController db = new DBController();
        Connection connection = db.getConnection();
        if (connection != null){
            try {
                Statement statement = connection.createStatement();
                String getHistory = "SELECT * FROM browser_history ORDER BY Site_Id DESC";
                ResultSet resultSet = statement.executeQuery(getHistory);

                String site_visit_date = "9999-99-99";

                while (resultSet.next()) {
                    String site_visit_time = resultSet.getString("Site_Visit_Time");
                    String site_title = resultSet.getString("Site_Title");
                    String site_url = resultSet.getString("Site_URL");

                    if (!site_visit_date.equals(resultSet.getString("Site_Visit_Date"))){
                        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 50);
                        site_visit_date = resultSet.getString("Site_Visit_Date");
                        String datetext = reverseDate(site_visit_date);
                        Text date = new Text(datetext);
                        date.setFont(new Font(30));
                        vBox.getChildren().add(date);

                    } else {
                        anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 35);
                        HBox hBox = formatRecord(site_visit_time, site_title, site_url);
                        vBox.getChildren().add(hBox);
                    }

                }
                anchorPane.setPrefHeight(anchorPane.getPrefHeight() + 20);
            } catch (SQLException e){
                e.printStackTrace();
            }
        }
        db.closeConnection();
        return vBox;
    }

    private String reverseDate(String date){
        String [] arr = date.split("-");
        String result = "";
        for (int i = 0; i < arr.length; i++){
            result = result + arr [arr.length - i - 1] + "/";
        }
        result = result.substring(0,result.length()-1);
        result = result + " - " + convertDateToDay(result);
        return result;
    }

    public static String convertDateToDay(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        SimpleDateFormat dayFormat = new SimpleDateFormat("EEEE", Locale.ENGLISH);
        String dayOfWeek = "";

        try {
            Date date = dateFormat.parse(dateString);
            dayOfWeek = dayFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return dayOfWeek;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        clearHistoryButton.setStyle("-fx-background-color: red; -fx-text-fill: white;");
        VBox vBox = setHistoryPage();
        anchorPane.getChildren().add(vBox);
        AnchorPane.setTopAnchor(vBox, 120.0);
        AnchorPane.setLeftAnchor(vBox, 50.0);
    }
}

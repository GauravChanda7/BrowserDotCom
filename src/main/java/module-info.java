module gauravchanda7.browserdotcom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;
    requires java.sql;

    opens gauravchanda7.browserdotcom to javafx.fxml;
    exports gauravchanda7.browserdotcom;
}
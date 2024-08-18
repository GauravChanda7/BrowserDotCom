module gauravchanda7.browserdotcom {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    opens gauravchanda7.browserdotcom to javafx.fxml;
    exports gauravchanda7.browserdotcom;
}
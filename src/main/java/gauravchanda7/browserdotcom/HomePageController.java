package gauravchanda7.browserdotcom;

import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebHistory;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.ResourceBundle;

public class HomePageController implements Initializable {

    @FXML
    private Button ReloadButton;

    @FXML
    private TextField URLTextField;

    @FXML
    private Button ZoomInButton;

    @FXML
    private Button ZoomOutButton;

    @FXML
    private Button backButton;

    @FXML
    private Button forwardButton;

    @FXML
    private Button searchButton;

    @FXML
    private ProgressBar progressBar;

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private WebHistory webHistory;

    private String tabName;

    @FXML
    void NextWebPage(ActionEvent event) {
        webHistory = webView.getEngine().getHistory();
        webHistory.go(+1);
        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
        URLTextField.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
    }

    @FXML
    void PreviousWebPage(ActionEvent event) {
        webHistory = webView.getEngine().getHistory();
        webHistory.go(-1);
        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
        URLTextField.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
    }

    @FXML
    void ReloadWebPage(ActionEvent event) {
        webEngine.reload();
    }

    @FXML
    void Search(ActionEvent event) {
        LoadURL();
    }


    @FXML
    void URLTextBoxEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            LoadURL();
        }
    }

    @FXML
    void ZoomIn(ActionEvent event) {
        webView.setZoom(webView.getZoom() + 0.1);
    }

    @FXML
    void ZoomOut(ActionEvent event) {
        webView.setZoom(webView.getZoom() - 0.1);
    }

    void LoadURL(){
        //webEngine = webView.getEngine();
        webEngine.load("https://"+URLTextField.getText());
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == Worker.State.SUCCEEDED) {
                System.out.println("WebPage loaded");
            } else if (newValue == Worker.State.FAILED) {
                System.out.println("WebPage failed to load");
            }
        });
    }

    String TabName(){
        webHistory = webView.getEngine().getHistory();
        ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
        tabName = entries.get(webHistory.getCurrentIndex()).getTitle();
        return tabName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");
    }
}

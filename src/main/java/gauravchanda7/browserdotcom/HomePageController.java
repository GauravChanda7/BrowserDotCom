package gauravchanda7.browserdotcom;


import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Tab;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
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

    private ObservableList<WebHistory.Entry> entries;

    private String URL;

    @FXML
    void NextWebPage(ActionEvent event) {
        //webHistory = webView.getEngine().getHistory();
        webHistory.go(+1);
        //ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
        URLTextField.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
    }

    @FXML
    void PreviousWebPage(ActionEvent event) {
        //webHistory = webView.getEngine().getHistory();
        webHistory.go(-1);
        //ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
        URLTextField.setText(entries.get(webHistory.getCurrentIndex()).getUrl());
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
    }

    @FXML
    void ReloadWebPage(ActionEvent event) {
        webEngine.reload();
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());
    }

    @FXML
    void Search(ActionEvent event) {
        setURL();
        LoadURL();
    }


    @FXML
    void URLTextBoxEnterPress(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER){
            setURL();
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

    private void setURL(){
        URL = URLTextField.getText().trim();
    }

    private void LoadURL(){
        //webEngine = webView.getEngine();
        if (!URL.contains("https://")) {
            URL = "https://" + URL;
        }
        webEngine.load(URL);
        progressBar.progressProperty().bind(webEngine.getLoadWorker().progressProperty());

        webEngine.getLoadWorker().stateProperty().addListener((obs, oldValue, newValue) -> {
            if (newValue == Worker.State.FAILED) {
                    System.out.println("WebPage failed to load: " + URL);
                if (!URL.contains("www.")){
                    URL = URL.substring(0,8) + "www." + URL.substring(8);
                } if (!URL.contains(".com")){
                    URL = URL + ".com";
                    LoadURL();
                }
            }
        });
    }




    private void setupKeyShortcuts(Button button, KeyCombination keyCombination) {
        button.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                button.getScene().getAccelerators().put(keyCombination, () -> {
                    button.fire();
                });
            }
        });
    }


    public void updateTabTitle(Tab tab) {
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Platform.runLater(() -> {
                    String title = webEngine.getTitle();
                    if (title == null || title.isEmpty()) {
                        title = "New Tab";
                    }
                    if (title.length() > 29) {
                        title = title.substring(0,26) + "...";
                    }
                    tab.setText(title);
                });
            }
        });
    }

    public void showOnCurrentSiteOnTerminal(){
        webEngine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.SUCCEEDED) {
                Platform.runLater(() -> {
                    String title = webEngine.getTitle();
                    if (title == null || title.isEmpty()) {
                        title = "Blank Name";
                    } else {
                        System.out.println(title);
                    }
                });
            } else if (newState == Worker.State.FAILED) {
                System.out.println("Failed to load");
            }
        });
    }


    public WebEngine getWebEngine() {
        return webEngine;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

        webHistory = webView.getEngine().getHistory();
        entries = webHistory.getEntries();

        setupKeyShortcuts(ReloadButton, new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN));
        setupKeyShortcuts(ZoomInButton, new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN));
        setupKeyShortcuts(ZoomOutButton, new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN));
        setupKeyShortcuts(backButton, new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN));
        setupKeyShortcuts(forwardButton, new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN));

        showOnCurrentSiteOnTerminal();
    }


}

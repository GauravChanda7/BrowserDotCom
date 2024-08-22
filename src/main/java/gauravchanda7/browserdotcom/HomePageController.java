package gauravchanda7.browserdotcom;

import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
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

    private String tabName;

    private ObservableList<WebHistory.Entry> entries;

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
        String URL = URLTextField.getText();
        if (!URLTextField.getText().contains(".com")){
            URL = URL + ".com";
        } if (!URLTextField.getText().contains("https://")) {
            URL = "https://" + URL;
        }
        webEngine.load(URL);
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
        //ObservableList<WebHistory.Entry> entries = webHistory.getEntries();
        tabName = entries.get(webHistory.getCurrentIndex()).getTitle();
        return tabName;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        webEngine = webView.getEngine();
        webEngine.load("https://www.google.com");

        webHistory = webView.getEngine().getHistory();
        entries = webHistory.getEntries();

        ReloadButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                KeyCombination reloadKeyCombination = new KeyCodeCombination(KeyCode.R, KeyCombination.ALT_DOWN);
                ReloadButton.getScene().getAccelerators().put(reloadKeyCombination, () -> {
                    ReloadButton.fire();
                });
            }
        });

        ZoomInButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                KeyCombination zoomInKeyCombination = new KeyCodeCombination(KeyCode.EQUALS, KeyCombination.CONTROL_DOWN);
                ZoomInButton.getScene().getAccelerators().put(zoomInKeyCombination, () -> {
                    ZoomInButton.fire();
                });
            }
        });

        ZoomOutButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                KeyCombination zoomOutKeyCombination = new KeyCodeCombination(KeyCode.MINUS, KeyCombination.CONTROL_DOWN);
                ZoomOutButton.getScene().getAccelerators().put(zoomOutKeyCombination, () -> {
                    ZoomOutButton.fire();
                });
            }
        });

        backButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                KeyCombination backKeyCombination = new KeyCodeCombination(KeyCode.B, KeyCombination.CONTROL_DOWN);
                backButton.getScene().getAccelerators().put(backKeyCombination, () -> {
                    backButton.fire();
                });
            }
        });

        forwardButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null){
                KeyCombination forwardKeyCombination = new KeyCodeCombination(KeyCode.F, KeyCombination.CONTROL_DOWN);
                forwardButton.getScene().getAccelerators().put(forwardKeyCombination, () -> {
                    forwardButton.fire();
                });
            }
        });
    }
}

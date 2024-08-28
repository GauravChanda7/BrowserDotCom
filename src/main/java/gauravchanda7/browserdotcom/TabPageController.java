package gauravchanda7.browserdotcom;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TabPageController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Tab homeTab;

    @FXML
    private Tab newTab;

    @FXML
    private TabPane tabPane;

    public void createTabWithURL(String url) {
        Tab tab = new Tab("New Tab");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Pane tabContent;
        try {
            tabContent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HomePageController newTabController = loader.getController();
        newTabController.updateTabTitle(tab);
        newTabController.setTabPageController(this);
        newTabController.setURLTextField(url);
        newTabController.searchHistoryByURL();

        tab.setContent(tabContent);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }

    public void createNewTab() {
        Tab tab = new Tab("New Tab");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        Pane tabContent;
        try {
            tabContent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HomePageController newTabController = loader.getController();
        newTabController.updateTabTitle(tab);
        newTabController.setTabPageController(this);

        tab.setContent(tabContent);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }


    public void createHistoryTab() {
        Tab tab = new Tab("History");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("historypage.fxml"));
        ScrollPane tabContent;
        try {
            tabContent = loader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        HistoryPageController historyPageController = loader.getController();
        historyPageController.setTabPageController(this); // Pass TabPageController instance

        tab.setContent(tabContent);
        tabPane.getTabs().add(tabPane.getTabs().size() - 1, tab);
        tabPane.getSelectionModel().select(tabPane.getTabs().size() - 2);
    }




    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        homeTab.setText("New Tab");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homepage.fxml"));
        try {
            Pane homePageContent = loader.load();
            homeTab.setContent(homePageContent);

            HomePageController homePageController = loader.getController();
            homePageController.updateTabTitle(homeTab);
            homePageController.setTabPageController(this);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        newTab.setOnSelectionChanged(event -> {
            if (newTab.isSelected()) {
                createNewTab();
            }
        });



        anchorPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                Scene scene = anchorPane.getScene();
                scene.setOnKeyPressed(keyEvent -> {
                    KeyCombination newTabShortcut = new KeyCodeCombination(KeyCode.T, KeyCombination.CONTROL_DOWN);
                    if (newTabShortcut.match(keyEvent)) {
                        createNewTab();
                    }
                });
            }
        });
    }
}

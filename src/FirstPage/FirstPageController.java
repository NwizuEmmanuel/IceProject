/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstPage;

import StartPage.StartPageController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Onyekachukwu
 */
public class FirstPageController implements Initializable {

    @FXML
    private JFXButton formButton;

    @FXML
    private JFXButton searchButton;

    @FXML
    private JFXButton chartsButton;

    @FXML
    private MenuItem startPageItem;

    @FXML
    private MenuItem switchBoardItem;

    @FXML
    private MenuItem closeItem;

    @FXML
    private void switchBoardItemAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SwitchBoard/SwitchBoard.fxml"));
            Stage stage = new Stage();
            Stage stage1 = (Stage) formButton.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.initStyle(StageStyle.UTILITY);
            stage.setResizable(false);
            stage1.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void startPageItemAction() {
        StartPage.StartPageController controller = new StartPageController();
        Preferences prefs = Preferences.userRoot().node(controller.getClass().getName());
        prefs.putBoolean("isChecked", false);
        openTabForStartPage("/StartPage/StartPage.fxml", "Start page");
    }

    @FXML
    private void terminator() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm exit?");
        alert.setHeaderText(null);
        alert.setTitle(null);

        ButtonType confirmBtn = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(confirmBtn, cancelBtn);

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.get() == confirmBtn) {
            System.exit(0);
        } else if (answer.get() == cancelBtn) {
            alert.close();
        }
    }

    @FXML
    private TabPane tabPane;

    private Map<String, Tab> openTabs = new HashMap<>();

//    private ScrollPane scrollPane = new ScrollPane();
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            formButton.setOnAction(e -> openTabForStartPage("/FormPage/formPage.fxml", "Form")
            // this is not a start page
            );
            searchButton.setOnAction(e -> {
                openTab("/SearchPage/searchPage.fxml", "Search");
            });

            chartsButton.setOnAction(e -> {
                openTab("/ChartPage/ChartPage.fxml", "Chart");
            });
        }

        {
            StartPage.StartPageController controller = new StartPageController();
            Preferences prefs = Preferences.userRoot().node(controller.getClass().getName());
            if (prefs.getBoolean("isChecked", true) != true) {
                openTabForStartPage("/StartPage/StartPage.fxml", "Start page");
                System.out.println(prefs.getBoolean("isChecked", true));
            } else {
                openTabForStartPage("/FormPage/formPage.fxml", "Form"); // this is not a start page
                openTab("/SearchPage/searchPage.fxml", "Search");
            }
        }
    }

    private void openTab(String fxmlFile, String tabName) {
        FXMLLoader loader = new FXMLLoader();
        if (openTabs.containsKey(fxmlFile)) {
            tabPane.getSelectionModel().select(openTabs.get(fxmlFile));
        } else {
            try {
                Tab newTab = new Tab(tabName);
                Node borderPane = FXMLLoader.load(getClass().getResource(fxmlFile));
                newTab.setContent(borderPane);
                tabPane.getTabs().add(newTab);
                openTabs.put(fxmlFile, newTab);
                tabPane.getSelectionModel().select(openTabs.get(fxmlFile));
                newTab.setOnClosed(e -> openTabs.remove(fxmlFile));
            } catch (IOException ex) {
                Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void openTabForStartPage(String fxmlFile, String tabName) {
//        FXMLLoader loader = new FXMLLoader();
        if (openTabs.containsKey(fxmlFile)) {
            tabPane.getSelectionModel().select(openTabs.get(fxmlFile));
        } else {
            try {
                Tab newTab = new Tab(tabName);
                Node anchorPane = FXMLLoader.load(getClass().getResource(fxmlFile));
                newTab.setContent(anchorPane);
                tabPane.getTabs().add(newTab);
                openTabs.put(fxmlFile, newTab);
                tabPane.getSelectionModel().select(openTabs.get(fxmlFile));
                newTab.setOnClosed(e -> openTabs.remove(fxmlFile));
            } catch (IOException ex) {
                Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

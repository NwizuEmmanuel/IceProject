/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package AdminBoard;

import Admin_Server.AdminServer;
import FirstPage.FirstPageController;
import InternFees.InternFeeController;
import NewAdmin.NewAdminController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRippler;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class AdminBoardController implements Initializable {

    @FXML
    private BorderPane borderPane;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem switchBoardItem;

    @FXML
    private MenuItem signOutItem;

    @FXML
    private StackPane stackPane;

    @FXML
    private HBox dashBoardBtn;

    @FXML
    private HBox ledgerBtn;

    @FXML
    private HBox scheduleBtn;

    @FXML
    private VBox basePane;

    private HashMap<String, Node> openNodes = new HashMap<>();

    Node ledgerNode;
    Node dashBoardNode;
    Node scheduleNode;

    private void pageSetterAtInit() {
        try {
            ledgerNode = FXMLLoader.load(getClass().getResource("/Ledger/Ledger.fxml"));
            dashBoardNode = FXMLLoader.load(getClass().getResource("/DashBoard/DashBoard.fxml"));
            scheduleNode = FXMLLoader.load(getClass().getResource("/Schedules/Schedules.fxml"));
            stackPane.getChildren().addAll(dashBoardNode, ledgerNode, scheduleNode);
            ledgerNode.setVisible(false);
            dashBoardNode.setVisible(false);
            scheduleNode.setVisible(false);
        } catch (IOException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void pageSetter(int nodeNumber) throws IOException {
        switch (nodeNumber) {
            case 1:
                dashBoardNode.setVisible(true);
                dashBoardNode.toFront();
                break;
            case 2:
                ledgerNode.setVisible(true);
                ledgerNode.toFront();
                break;
            case 3:
                scheduleNode.setVisible(true);
                scheduleNode.toFront();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // At initial
        {
            pageSetterAtInit();
        }
        // Set pages at initial
        {
            try {
                pageSetter(1);
            } catch (IOException ex) {
                Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        // for ledger
        {
            ledgerBtn.setOnMouseClicked(e -> {
                ledgerBtnAction();
            });
        }
        // for dash board
        {
            dashBoardBtn.setOnMouseClicked(e -> {
                dashBoardBtnAction();
            });
        }
        // for schedules
        {
            scheduleBtn.setOnMouseClicked(e -> {
                schedulesBtnAction();
            });
        }
    }

    @FXML
    private void ledgerBtnAction() {
        JFXRippler rippler = new JFXRippler(ledgerBtn);
        basePane.getChildren().add(1, ledgerBtn);
        try {
            pageSetter(2);
        } catch (IOException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void dashBoardBtnAction() {
        try {
            pageSetter(1);
        } catch (IOException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void schedulesBtnAction() {
        try {
            pageSetter(3);
        } catch (IOException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void closeMenuItemAction() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm exit?");
        alert.setHeaderText(null);
        alert.setTitle(null);

        ButtonType yesBtn = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.get() == yesBtn) {
            System.exit(0);
        } else if (answer.get() == noBtn) {
            alert.close();
        }
    }

    @FXML
    private void switchBoardItemAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstPage/FirstPage.fxml"));
            Stage stage = new Stage();
            Stage stage1 = (Stage) dashBoardBtn.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage1.close();
            stage.setMaximized(true);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(FirstPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void signOutItemAction() throws SQLException, IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setContentText("Confirm sign out?");
        alert.setHeaderText(null);

        ButtonType yesBtn = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        ButtonType noBtn = new ButtonType("Cancel", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);

        Optional<ButtonType> answer = alert.showAndWait();

        if (answer.get() == yesBtn) {
            Connection connection;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            String sqlCommand = "DELETE FROM admim_key";
            PreparedStatement ps = connection.prepareStatement(sqlCommand);
            ps.execute();

            NewAdmin.NewAdminController controller = new NewAdminController();
            InternFees.InternFeeController feeController = new InternFeeController();
            Preferences preferences = Preferences.userRoot().node(controller.getClass().getName());
            Preferences pref;
            preferences.put("username","Nil");
            preferences.put("password","Nil");
            pref = Preferences.userRoot().node(feeController.getClass().getName());
            pref.remove("internFees");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/SwitchBoard/SwitchBoard.fxml"));
            Stage stage = new Stage();
            Stage stage1 = (Stage) dashBoardBtn.getScene().getWindow();
            stage1.close();
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } else if (answer.get() == noBtn) {
            alert.close();
        }

    }

    @FXML
    private void internFeesAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/InternFees/InternFee.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.initStyle(StageStyle.UTILITY);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(AdminBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

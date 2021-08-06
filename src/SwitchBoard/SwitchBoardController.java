/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SwitchBoard;

import Admin_Server.AdminServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
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
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class SwitchBoardController implements Initializable {

    @FXML
    private JFXButton notAdminBtn;

    @FXML
    private JFXButton adminBtn;

    @FXML
    private JFXTextField loginUsername;

    @FXML
    private JFXPasswordField loginPassword;

    @FXML
    private JFXTabPane tabPane;

    // for notAdminBtn
    @FXML
    private void notAdminAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/FirstPage/FirstPage.fxml"));
            Stage stage = new Stage();
            Stage stage1 = (Stage) notAdminBtn.getScene().getWindow();
            stage.setScene(new Scene(loader.load()));
            stage.setMaximized(true);
            stage1.close();
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SwitchBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void adminBtnAction() {
        try {
            String sqlCmd = "SELECT username,password FROM admim_key WHERE username = '" + loginUsername.getText() + "' AND password = '" + loginPassword.getText() + "' ";
            Connection connection;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            String username = "";
            String password = "";
            while (rs.next()) {
                username = rs.getString("username");
                password = rs.getString("password");
            }
            if (loginUsername.getText().equals(username) && loginPassword.getText().equals(password)) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminBoard/AdminBoard.fxml"));
                    Stage stage = new Stage();
                    Stage stage1 = (Stage) notAdminBtn.getScene().getWindow();
                    stage.setScene(new Scene(loader.load()));
                    stage.setMaximized(true);
                    stage1.close();
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(SwitchBoardController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Unrecognised Admin");
                alert.setHeaderText(null);
                alert.setTitle("Admin");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SwitchBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void adminChecker() {
        try {
            Connection connection;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            String sqlcml = "SELECT COUNT(ID) FROM admim_key";
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlcml);
            int number = 0;
            while (rs.next()) {
                number = rs.getInt("COUNT(ID)");
            }
            if (number == 0) {
                Tab();
            }
        } catch (SQLException ex) {
            Logger.getLogger(SwitchBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        adminChecker();
    }

    private void Tab() {
        try {
            Tab tab = new Tab("New Admin");
            Node content = FXMLLoader.load(getClass().getResource("/NewAdmin/NewAdmin.fxml"));
            tab.setContent(content);
            tabPane.getTabs().add(tab);
            tabPane.getSelectionModel().select(tab);
        } catch (IOException ex) {
            Logger.getLogger(SwitchBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

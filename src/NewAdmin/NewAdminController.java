/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NewAdmin;

import Admin_Server.AdminServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Hyperlink;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class NewAdminController implements Initializable {

    @FXML
    private JFXTextField newUsername;

    @FXML
    private JFXPasswordField newPassword;

    @FXML
    private JFXButton newLoginBtn;

    @FXML
    private void loginNewAdminAction() throws IOException {
        try {
            String sqlCmd = "INSERT INTO admim_key(username,password)VALUES(?,?)";
            Connection connection;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            PreparedStatement ps = connection.prepareStatement(sqlCmd);
            ps.setString(1, newUsername.getText());
            ps.setString(2, newPassword.getText());
            ps.execute();
            if (!newUsername.getText().isEmpty() && !newPassword.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setContentText("Do you want to be remembered next time?");
                alert.setHeaderText(null);
                alert.setTitle(null);

                ButtonType yesBtn = new ButtonType("Yes", ButtonBar.ButtonData.YES);
                ButtonType noBtn = new ButtonType("No", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yesBtn, noBtn);

                Optional<ButtonType> answer = alert.showAndWait();

                if (answer.get() == yesBtn) {
                    Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
                    String username = "username";
                    String password = "password";
                    preferences.put("username", newUsername.getText());
                    preferences.put("password", newPassword.getText());
                } else if (answer.get() == noBtn) {
                    alert.close();
                }

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/AdminBoard/AdminBoard.fxml"));
                Stage stage = new Stage();
                Stage stage1 = (Stage) newLoginBtn.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.setMaximized(true);
                stage1.close();
                stage.show();
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Invalid Username and Password");
                alert.setHeaderText(null);
                alert.setTitle("Admin");
                alert.showAndWait();
            }
        } catch (SQLException ex) {
            Logger.getLogger(NewAdminController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

//    @FXML
//    private void hyperlinkAction() {
//        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
//        String username = "username";
//        String password = "password";
//        preferences.put("username", newUsername.getText());
//        preferences.put("password", newPassword.getText());
//
//        Alert alert = new Alert(Alert.AlertType.INFORMATION);
//        alert.setContentText("Checked");
//        alert.setHeaderText(null);
//        alert.setTitle(null);
//        alert.showAndWait();
//    }

}

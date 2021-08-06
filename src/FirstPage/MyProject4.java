/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FirstPage;

import Admin_Server.AdminServer;
import NewAdmin.NewAdminController;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.prefs.Preferences;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author Onyekachukwu
 */
public class MyProject4 extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/SwitchBoard/SwitchBoard.fxml"));
        Parent root1 = FXMLLoader.load(getClass().getResource("/AdminBoard/AdminBoard.fxml"));

        NewAdmin.NewAdminController controller = new NewAdminController();
        Preferences preferences = Preferences.userRoot().node(controller.getClass().getName());
        Connection connection;
        Admin_Server.AdminServer server = new AdminServer();
        connection = server.connectCord("1");
        String sqlCommand = "SELECT username, password FROM admim_key";
        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery(sqlCommand);

        String username = "";
        String password = "";
        while (rs.next()) {
            username = rs.getString("username");
            password = rs.getString("password");
        }
        if (preferences.get("username", "").equals(username) && preferences.get("password", "").equals(password)) {
            Scene scene = new Scene(root1);
            stage.setMaximized(true);
            stage.setScene(scene);
            stage.show();
        } else {
            Scene scene = new Scene(root);
            stage.initStyle(StageStyle.UTILITY);
            stage.setScene(scene);
            stage.show();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}

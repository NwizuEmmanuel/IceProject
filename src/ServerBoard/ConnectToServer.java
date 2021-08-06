/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ServerBoard;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.Alert;
import javafx.stage.Modality;

/**
 *
 * @author Onyekachukwu
 */
public class ConnectToServer {
    Connection connection = null;
    public Connection connectCord(String plug){
        if ("1".equals(plug)) {
            try {
                connection = (Connection)DriverManager.getConnection("jdbc:mysql://localhost:3306/icehubregistration","root","");
                System.out.println("Connected");
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Connection Error");
                alert.setHeaderText(null);
                alert.setContentText("Can't connect to DB server.\n"+ex.getMessage());
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
                Logger.getLogger(ConnectToServer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }else if ("0".equals(plug)) {
            connection = null;
        }else{
            System.out.println("Remember 1 = connect, 0 = disconnect");
        }
        return connection;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UpdateAmount;

import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class UpdateAmountController implements Initializable {

    @FXML
    private TextField amountTF;

    @FXML
    private Button doneBtn;

    @FXML
    private Label idLabel;

    @FXML
    private VBox root;

    @FXML
    private JFXSnackbar snackBar;

    // to be called externally
    public void markLabelAs(String id) {
        idLabel.setText(id);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    @FXML
    public void doneBtnAction() {
        try {
            String sqlCmd = "UPDATE interns SET stillPaying = ? WHERE ID = ?";
            Connection connection = null;
            ServerBoard.ConnectToServer cts = new ConnectToServer();
            connection = cts.connectCord("1");
            PreparedStatement ps = connection.prepareStatement(sqlCmd);
            ps.setDouble(1, Double.valueOf(amountTF.getText()));
            ps.setString(2, idLabel.getText());
            ps.execute();

            snackBar = new JFXSnackbar(root);
            snackBar.getStylesheets().add("/UpdateAmount/UpdateAmount.css");
            snackBar.setPrefWidth(276);
            snackBar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Successfully done")));
        } catch (SQLException ex) {
            Logger.getLogger(UpdateAmountController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            snackBar = new JFXSnackbar(root);
            snackBar.getStylesheets().add("/UpdateAmount/UpdateAmount.css");
            snackBar.setPrefWidth(276);
            snackBar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Input amount")));
        }

    }

}

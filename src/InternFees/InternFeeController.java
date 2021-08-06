/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package InternFees;

import com.jfoenix.controls.JFXSnackbar;
import com.jfoenix.controls.JFXSnackbarLayout;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class InternFeeController implements Initializable {

    @FXML
    private Label lastEntryLabel;

    @FXML
    private TextField amountTF;

    @FXML
    private HBox root;

    @FXML
    private JFXSnackbar snackBar;

    @FXML
    private void updateBtnAction() {
        amountTF.setDisable(false);
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            lastEntry();
        }
    }

    @FXML
    private void doneBtnAction() {
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());
        double internFees = 0.0;

        if (amountTF.getText().matches("(\\d+\\.?)+")) {
            if (!(Double.valueOf(amountTF.getText()) == 0.0)) {
                preferences.remove("internFees");
                preferences.putDouble("internFees", Double.valueOf(amountTF.getText()));
                lastEntryLabel.setText(amountTF.getText());

                snackBar = new JFXSnackbar(root);
                snackBar.getStylesheets().add("/InternFees/InternFee.css");
                snackBar.setPrefWidth(276);
                snackBar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Successfully stored")));
            } else {
                snackBar = new JFXSnackbar(root);
                snackBar.getStylesheets().add("/InternFees/InternFee.css");
                snackBar.setPrefWidth(276);
                snackBar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Invalid input")));
            }

        } else {
            snackBar = new JFXSnackbar(root);
            snackBar.getStylesheets().add("/InternFees/InternFee.css");
            snackBar.setPrefWidth(276);
            snackBar.fireEvent(new JFXSnackbar.SnackbarEvent(new JFXSnackbarLayout("Invalid input")));
        }

    }

    private void lastEntry() {
        Preferences preferences = Preferences.userRoot().node(this.getClass().getName());

        if (preferences.getDouble("internFees", 0) == 0) {
            lastEntryLabel.setText("0");
        } else {
            lastEntryLabel.setText(String.valueOf(preferences.getDouble("internFees", 0)));
        }
    }
}

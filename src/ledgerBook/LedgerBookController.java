/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ledgerBook;

import Admin_Server.AdminServer;
import InternFees.InternFeeController;
import com.jfoenix.controls.JFXToggleButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.controlsfx.control.ToggleSwitch;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class LedgerBookController implements Initializable {

    @FXML
    private JFXToggleButton othersToggle;

    @FXML
    private TextArea descriptionTF;

    @FXML
    private ComboBox<String> cashFlowCombo;

    @FXML
    private TextField amountTF;

    @FXML
    private DatePicker datePicker1;

    @FXML
    private JFXToggleButton internsToggle;

    @FXML
    private TextField fullNameTF;

    @FXML
    private TextField amountTF2;

    @FXML
    private DatePicker datePicker2;

    @FXML
    private Button writeToLedgerBtn;

    @FXML
    private Button writeToLedgerBtn2;

    @FXML
    private Label cashFlowIndicator;

    @FXML
    private CheckBox notYetCheckbox;

    @FXML
    private void internsToggleAction() {
        if (internsToggle.isSelected()) {
            fullNameTF.setDisable(true);
            amountTF2.setDisable(true);
            datePicker2.setDisable(true);
            writeToLedgerBtn2.setDisable(true);
            notYetCheckbox.setDisable(true);
        } else if (!internsToggle.isSelected()) {
            fullNameTF.setDisable(false);
            amountTF2.setDisable(false);
            datePicker2.setDisable(false);
            writeToLedgerBtn2.setDisable(false);
            notYetCheckbox.setDisable(false);
        }
    }

    @FXML
    private void notYetCheckBoxAction() {
        if (notYetCheckbox.isSelected()) {
            amountTF2.setDisable(true);
            amountTF2.setText(String.valueOf(0.0));
        } else {
            amountTF2.setText(null);
            amountTF2.setDisable(false);
        }
    }

    @FXML
    private void othersToggleAction() {
        if (othersToggle.isSelected()) {
            descriptionTF.setDisable(true);
            cashFlowCombo.setDisable(true);
            datePicker1.setDisable(true);
            amountTF.setDisable(true);
            writeToLedgerBtn.setDisable(true);
        } else if (!othersToggle.isSelected()) {
            descriptionTF.setDisable(false);
            cashFlowCombo.setDisable(false);
            datePicker1.setDisable(false);
            amountTF.setDisable(false);
            writeToLedgerBtn.setDisable(false);
        }
    }

    @FXML
    private void ComboAction() {
        if (cashFlowCombo.getSelectionModel().getSelectedItem() == "Expense") {
            cashFlowIndicator.setText("-");
            cashFlowIndicator.setVisible(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            if (internsToggle.isSelected()) {
                fullNameTF.setDisable(true);
                amountTF2.setDisable(true);
                datePicker2.setDisable(true);
                writeToLedgerBtn2.setDisable(true);
                notYetCheckbox.setDisable(true);
            }
        }

        {
            cashFlowCombo.getItems().addAll("Income", "Expense");
        }
    }

    @FXML
    private void ledgerButtonAction() {
        if (amountTF.getText().matches("\\d+") && descriptionTF.getText() != null && datePicker1.getValue() != null) {
            String sqlCmd = "INSERT INTO icehub_admin(Descriptions,Income,Date)VALUES(?,?,?)";
            String sqlCmd3 = "INSERT INTO icehub_admin(Descriptions,Expense,Date)VALUES(?,?,?)";

            if (cashFlowCombo.getSelectionModel().getSelectedItem() == "Income") {
                try {
                    Admin_Server.AdminServer server = new AdminServer();
                    Connection connection = server.connectCord("1");
                    PreparedStatement ps = connection.prepareStatement(sqlCmd);
                    ps.setString(1, descriptionTF.getText());
                    ps.setDouble(2, Double.parseDouble(amountTF.getText()));
                    ps.setDate(3, Date.valueOf(datePicker1.getValue()));
                    ps.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully uploaded");
                    alert.setHeaderText(null);
                    alert.setTitle("Upload");
                    alert.showAndWait();

                    descriptionTF.clear();
                    amountTF.clear();
                    datePicker1.setValue(null);
                    cashFlowCombo.setValue(null);
                    cashFlowIndicator.setText(null);
                } catch (SQLException ex) {
                    Logger.getLogger(LedgerBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cashFlowCombo.getSelectionModel().getSelectedItem() == "Expense") {
                try {
                    Admin_Server.AdminServer server = new AdminServer();
                    Connection connection = server.connectCord("1");
                    PreparedStatement ps = connection.prepareStatement(sqlCmd3);
                    ps.setString(1, descriptionTF.getText());
                    ps.setDouble(2, Double.parseDouble(amountTF.getText()));
                    ps.setDate(3, Date.valueOf(datePicker1.getValue()));
                    ps.execute();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setContentText("Successfully uploaded");
                    alert.setHeaderText(null);
                    alert.setTitle("Upload");
                    alert.showAndWait();

                    descriptionTF.clear();
                    amountTF.clear();
                    datePicker1.setValue(null);
                    cashFlowCombo.setValue(null);
                    cashFlowIndicator.setText(null);
                } catch (SQLException ex) {
                    Logger.getLogger(LedgerBookController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Invalid inputs");
            alert.setHeaderText(null);
            alert.setTitle("Upload");
            alert.showAndWait();
        }
    }

    @FXML
    private void ledgerActionForInterns() {
        InternFees.InternFeeController feeController = new InternFeeController();
        Preferences pref = Preferences.userRoot().node(feeController.getClass().getName());
        if (fullNameTF.getText().matches("[a-zA-Z ]+") && amountTF2.getText().matches("(\\d+\\.?)+") && datePicker2.getValue() != null && (pref.getDouble("internFees", 0) >= Double.valueOf(amountTF2.getText()))) {
            try {
                String sqlCmd2 = "INSERT INTO ice_admin_student(FullName,Amount,Date)VALUES(?,?,?)";

                Admin_Server.AdminServer server = new AdminServer();
                Connection connection = server.connectCord("1");
                PreparedStatement ps = connection.prepareStatement(sqlCmd2);
                ps.setString(1, fullNameTF.getText());
                if (notYetCheckbox.isSelected()) {
                    ps.setDouble(2, 0.0);
                } else {
                    ps.setDouble(2, Double.parseDouble(amountTF2.getText()));
                }
                ps.setDate(3, Date.valueOf(datePicker2.getValue()));
                ps.execute();

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setContentText("Valid inputs");
                alert.setHeaderText(null);
                alert.setTitle("Upload");
                alert.showAndWait();

                fullNameTF.setText(null);
                amountTF2.setText(null);
                datePicker2.setValue(null);
            } catch (SQLException ex) {
                Logger.getLogger(LedgerBookController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("invalid inputs");
            alert.setHeaderText(null);
            alert.setTitle("Upload");
            alert.showAndWait();
        }
    }
}

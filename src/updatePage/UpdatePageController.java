/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package updatePage;

import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class UpdatePageController implements Initializable {

    @FXML
    private JFXTextField firstname;

    @FXML
    private JFXTextField lastname;

    @FXML
    private JFXTextField othername;

    @FXML
    private JFXComboBox<String> gender;

    @FXML
    private JFXTextField telephone;

    @FXML
    private JFXTextField email;

    @FXML
    private JFXTextField address;

    @FXML
    private JFXComboBox<String> course;

    @FXML
    private DatePicker regDate;

    @FXML
    private JFXTextField pgContact;

    @FXML
    private JFXTextField id;

    @FXML
    private JFXButton updateBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        updateBtn.setOnAction(e -> {
            updateAction();
        });
        {
            gender.getItems().addAll("Male", "Female");
        }
        {
            course.getItems().addAll("Web development & Hosting", "Mobile App development(ios and android)", "Software development(Desktop)", "UI/UX", "SEO/Content Creation", "Graphics design");
        }
    }

    public void shareData(String id, String firstName, String lastName, String otherName, String gender, String telephone, String email, String address, String course, Date regDate, String pgContact) {
        firstname.setText(firstName);
        lastname.setText(lastName);
        othername.setText(otherName);
        this.gender.setValue(gender);
        this.telephone.setText(telephone);
        this.email.setText(email);
        this.address.setText(address);
        this.course.setValue(course);
        this.regDate.setValue(regDate.toLocalDate());
        this.pgContact.setText(pgContact);
        this.id.setText(id);
    }

    private void updateAction() {
        try {
            String sqlCmd = "UPDATE interns SET FirstName = '" + firstname.getText() + "', LastName = '" + lastname.getText() + "', OtherName = '" + othername.getText() + "', Gender = '" + gender.getValue() + "', Telephone = '" + telephone.getText() + "', Email = '" + email.getText() + "', Address = '" + address.getText() + "', Course = '" + course.getValue() + "', RegistrationDate = '" + regDate.getValue() + "', PGContact = '" + pgContact.getText() + "' WHERE ID = '" + id.getText() + "'";
            Connection connection = null;
            ServerBoard.ConnectToServer cts = new ConnectToServer();
            connection = cts.connectCord("1");
            PreparedStatement ps = connection.prepareStatement(sqlCmd);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setContentText("Successfully updated");
            alert.setHeaderText(null);
            alert.setTitle("Update panel");
            alert.showAndWait();
            
            Stage stage = (Stage)updateBtn.getScene().getWindow();
            stage.close();
        } catch (SQLException ex) {
            Logger.getLogger(UpdatePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

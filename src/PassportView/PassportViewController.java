/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PassportView;

import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class PassportViewController implements Initializable {

    @FXML
    private ImageView photoPlate;

    @FXML
    private Label idLabel;

    @FXML
    private JFXCheckBox checkBox;

    @FXML
    private JFXButton addImage;

    @FXML
    private JFXButton updateBtn;

    @FXML
    private AnchorPane motherPane;

    @FXML
    private void checkBoxAction() {
        if (checkBox.isSelected()) {
            addImage.setDisable(false);
            updateBtn.setDisable(false);
        } else {
            addImage.setDisable(true);
            updateBtn.setDisable(true);
        }
    }

    File file;

    @FXML
    private void addImageBtnAction() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("PNG", "*.png"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg")
        );
        file = fileChooser.showOpenDialog(motherPane.getScene().getWindow());
        if (file != null) {
            Image image = new Image(file.toURI().toString());
            photoPlate.setImage(image);
        }
    }

    @FXML
    private void updateBtnAction() {
        try {
            FileInputStream fis = new FileInputStream(file);
            String sqlCmd = "UPDATE interns SET Passport = ? WHERE ID = '" + idLabel.getText() + "'";
            Connection connection = null;
            ServerBoard.ConnectToServer cts = new ConnectToServer();
            connection = cts.connectCord("1");
            PreparedStatement ps = connection.prepareStatement(sqlCmd);
            ps.setBinaryStream(1, fis);
            ps.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setTitle("Update Info");
            alert.setContentText("Image saved successfully.");
            alert.showAndWait();

            Stage stage = (Stage) updateBtn.getScene().getWindow();
            stage.close();

        } catch (SQLException ex) {
            Logger.getLogger(PassportViewController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setTitle("Empty update");
            alert.setContentText("You must choose an image\n"+ex.getMessage());
            alert.showAndWait();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public void placePhoto(InputStream in, String s) {
        Image image = new Image(in);
        photoPlate.setImage(image);
        idLabel.setText(s);
    }
}

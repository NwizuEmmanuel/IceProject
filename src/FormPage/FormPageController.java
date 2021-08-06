/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormPage;

import FormMarker.ErrorChecker;
import ServerBoard.ConnectToServer;
import Validator.ComponentValidation;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import org.controlsfx.control.textfield.CustomTextField;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class FormPageController implements Initializable {

    @FXML
    private CustomTextField firstName;

    @FXML
    private CustomTextField lastName;

    @FXML
    private CustomTextField otherName;

    @FXML
    private ComboBox<String> gender;

    @FXML
    private CustomTextField telNo;

    @FXML
    private CustomTextField email;

    @FXML
    private CustomTextField address;

    @FXML
    private ComboBox<String> course;

    @FXML
    private DatePicker registrationDate;

    @FXML
    private CustomTextField PGC;

    @FXML
    private Button DoneButton;

    @FXML
    private ImageView photoPlate;

    @FXML
    private Button addPhotoButton;

    @FXML
    private CheckBox photoNotAvaliable;

    @FXML
    private VBox rootPane;

    @FXML
    private AnchorPane registrationPage;

    @FXML
    private AnchorPane imagePage;

    @FXML
    private Button nextButton;

    @FXML
    private Button backButton;
    
    @FXML
    private VBox firstnameVB;

    @FXML
    private void nextBtnAction() {
        registrationPage.setVisible(false);
        registrationPage.setManaged(false);
        imagePage.setVisible(true);
        imagePage.setManaged(true);
    }

    @FXML
    private void backBtnAction() {
        imagePage.setVisible(false);
        imagePage.setManaged(false);
        registrationPage.setVisible(true);
        registrationPage.setManaged(true);
    }

    private Connection conn = null;

    String path;

    Image icon2 = new Image(getClass().getResourceAsStream("/IconPack/IconPerson.png"));

    private void registerAction() throws FileNotFoundException {
        if (photoNotAvaliable.isSelected()) {
            try {
                String sqlCmd = "INSERT INTO interns (FirstName, LastName, OtherName, Gender, Telephone, Email, Address, Course, RegistrationDate, PGContact) VALUES (?,?,?,?,?,?,?,?,?,?)";

                PreparedStatement ps = conn.prepareStatement(sqlCmd);
                ps.setString(1, firstName.getText());
                ps.setString(2, lastName.getText());
                ps.setString(3, otherName.getText());
                ps.setString(4, gender.getValue());
                ps.setString(5, telNo.getText());
                ps.setString(6, email.getText());
                ps.setString(7, address.getText());
                ps.setString(8, course.getValue());
                Date registrationDate = Date.valueOf(this.registrationDate.getValue());
                ps.setDate(9, registrationDate);
                ps.setString(10, PGC.getText());
                ps.execute();
                System.out.println("Successfully uplaoded");

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Register");
                alert.setHeaderText(null);
                alert.setContentText("Registration was successful");
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();

                firstName.clear();
                lastName.clear();
                otherName.clear();
                gender.getSelectionModel().clearSelection();
                telNo.clear();
                email.clear();
                address.clear();
                course.getSelectionModel().clearSelection();
                this.registrationDate.setValue(null);
                PGC.clear();
                Image icon = new Image(getClass().getResourceAsStream("/IconPack/IconPerson.png"));
                photoPlate.setImage(icon);
            } catch (SQLException ex) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("SQL Error");
                alert.setHeaderText(null);
                alert.setContentText("Something went wrong.\n" + ex.getMessage());
                alert.initModality(Modality.APPLICATION_MODAL);
                alert.showAndWait();
                Logger.getLogger(FormPageController.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            if (path != null) {
                try {
                    String sqlCmd = "INSERT INTO interns (FirstName, LastName, OtherName, Gender, Telephone, Email, Address, Course, RegistrationDate, PGContact, Passport) VALUES (?,?,?,?,?,?,?,?,?,?,?)";

                    PreparedStatement ps = conn.prepareStatement(sqlCmd);
                    ps.setString(1, firstName.getText());
                    ps.setString(2, lastName.getText());
                    ps.setString(3, otherName.getText());
                    ps.setString(4, gender.getValue());
                    ps.setString(5, telNo.getText());
                    ps.setString(6, email.getText());
                    ps.setString(7, address.getText());
                    ps.setString(8, course.getValue());
                    Date registrationDate = Date.valueOf(this.registrationDate.getValue());
                    ps.setDate(9, registrationDate);
                    ps.setString(10, PGC.getText());
                    File f = new File(path);
                    FileInputStream fis = new FileInputStream(f);
                    ps.setBinaryStream(11, fis, (int) f.length());
                    ps.execute();
                    path = null;
                    System.out.println("Successfully uplaoded");

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Register");
                    alert.setHeaderText(null);
                    alert.setContentText("Registration was successful");
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();

                    firstName.clear();
                    lastName.clear();
                    otherName.clear();
                    gender.getSelectionModel().clearSelection();
                    telNo.clear();
                    email.clear();
                    address.clear();
                    course.getSelectionModel().clearSelection();
                    this.registrationDate.setValue(null);
                    PGC.clear();
                    Image icon = new Image(getClass().getResourceAsStream("/IconPack/IconPerson.png"));
                    photoPlate.setImage(icon);
                } catch (SQLException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("SQL Error");
                    alert.setHeaderText(null);
                    alert.setContentText("Something went wrong.\n" + ex.getMessage());
                    alert.initModality(Modality.APPLICATION_MODAL);
                    alert.showAndWait();
                    Logger.getLogger(FormPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    private void link() {
        ServerBoard.ConnectToServer DBConnection = new ConnectToServer();
        conn = DBConnection.connectCord("1");
    }

    File file;

    private void photoGrabber() throws FileNotFoundException {
        FileInputStream fileInputStream = null;
        FileChooser fileChooser = new FileChooser();
        FileChooser.ExtensionFilter extFilterJPG = new FileChooser.ExtensionFilter("JPG files (*.JPG)", "*.JPG");
        FileChooser.ExtensionFilter extFilterjpg = new FileChooser.ExtensionFilter("jpg files (*.jpg)", "*.jpg");
        FileChooser.ExtensionFilter extFilterPNG = new FileChooser.ExtensionFilter("PNG files (*.PNG)", "*.PNG");
        FileChooser.ExtensionFilter extFilterpng = new FileChooser.ExtensionFilter("png files (*.png)", "*.png");
        fileChooser.getExtensionFilters().addAll(extFilterJPG, extFilterjpg, extFilterPNG, extFilterpng);
        file = fileChooser.showOpenDialog(rootPane.getScene().getWindow());

        if (file != null) {
            // identifying file as image
            Image image = new Image(file.toURI().toString());
            // setting image to photo frame
            photoPlate.setImage(image);
            path = file.getAbsolutePath();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            gender.getItems().addAll("Male", "Female");
        }
        {
            course.getItems().addAll("Web development & Hosting", "Mobile App development(ios and android)",
                    "Software development(Desktop)", "UI/UX", "SEO/Content Creation", "Graphics design");
        }
        {
            link();
        }
        {
            DoneButton.setOnAction(e -> {
                try {
                    registerAction();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FormPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        {
            addPhotoButton.setOnAction(e -> {
                try {
                    photoGrabber();
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(FormPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }

        {
            imagePage.setVisible(false);
            imagePage.setManaged(false);
        }
        
        {
            FormMarker.ErrorChecker checker = new ErrorChecker();
            checker.formChecker(firstnameVB, "[a-zA-Z]{1,5}", firstName);
        }
        
        //ValidationAttachment();
    }

    private void ValidationAttachment() {
        ComponentValidation componentValidation = new ComponentValidation();
        componentValidation.TextFieldValidation(firstName, "Firstname is required.");
        componentValidation.TextFieldValidation(lastName, "Lastname is required.");
        componentValidation.TextFieldValidation(otherName, "Othername is required.");
        componentValidation.TextFieldValidation(telNo, "Telephone numbers required.");
        componentValidation.TextFieldValidation(email, "Email required.");
        componentValidation.TextFieldValidation(address, "Address is required.");
        componentValidation.TextFieldValidation(PGC, "Parent/Guardian contact required");
        componentValidation.ComboBoxValidation(course, "Must select a course");
        componentValidation.ComboBoxValidation(gender, "Gender is required.");
        componentValidation.DatePickerValidation(registrationDate, "Only today's date.");
    }

}

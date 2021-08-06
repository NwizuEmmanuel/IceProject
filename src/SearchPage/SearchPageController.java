/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SearchPage;

import Models.tableData;
import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class SearchPageController implements Initializable {

    @FXML
    private TableView<tableData> tableView;

    @FXML
    private TableColumn<tableData, String> idCol;

    @FXML
    private TableColumn<tableData, String> firstNameCol;

    @FXML
    private TableColumn<tableData, String> lastNameCol;

    @FXML
    private TableColumn<tableData, String> otherNameCol;

    @FXML
    private TableColumn<tableData, String> genderCol;

    @FXML
    private TableColumn<tableData, String> telNoCol;

    @FXML
    private TableColumn<tableData, String> emailCol;

    @FXML
    private TableColumn<tableData, String> addressCol;

    @FXML
    private TableColumn<tableData, String> courseCol;

    @FXML
    private TableColumn<tableData, String> RegDateCol;

    @FXML
    private TableColumn<tableData, String> pgContactCol;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TableColumn editCol;

    @FXML
    private TableColumn passportCol;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private JFXButton editAmountBtn;

    // this function is activated when search tab is opened.
    @FXML
    public void refreshOnOPen() {
        ObservableList<tableData> datas = FXCollections.observableArrayList();
        LocalDate ld = LocalDate.now();
        try {
            String sqlCommand = "SELECT * FROM interns WHERE RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer cts = new ConnectToServer();
            connection = cts.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                datas.addAll(new tableData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
                tableView.setItems(datas);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // this refreshes the table view 
        {
            refreshOnOPen();
        }
        {
            refreshBtn.setText("Refresh");
            refreshBtn.setOnAction(e -> {
                ObservableList<tableData> datas = FXCollections.observableArrayList();
                LocalDate ld = LocalDate.now();
                try {
                    String sqlCommand = "SELECT * FROM interns WHERE RegistrationDate LIKE '" + ld.getYear() + "%' ";
                    Connection connection = null;
                    ServerBoard.ConnectToServer cts = new ConnectToServer();
                    connection = cts.connectCord("1");
                    Statement statement = connection.createStatement();
                    ResultSet rs = statement.executeQuery(sqlCommand);
                    while (rs.next()) {
                        datas.addAll(new tableData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
                        tableView.setItems(datas);
                    }
                } catch (SQLException ex) {
                    Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            });
        }
        {
            columnData();
        }

        {
            searchBar.setOnKeyReleased(e -> {
                searchBarAction();
            });
        }
    }

    private void columnData() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        firstNameCol.setCellValueFactory(new PropertyValueFactory<>("FirstName"));
        lastNameCol.setCellValueFactory(new PropertyValueFactory<>("LastName"));
        otherNameCol.setCellValueFactory(new PropertyValueFactory<>("OtherName"));
        genderCol.setCellValueFactory(new PropertyValueFactory<>("Gender"));
        telNoCol.setCellValueFactory(new PropertyValueFactory<>("Telephone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("Email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("Address"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("Course"));
        RegDateCol.setCellValueFactory(new PropertyValueFactory<>("RegistrationDate"));
        pgContactCol.setCellValueFactory(new PropertyValueFactory<>("PGContact"));
        passportCol.setCellFactory(cellFactory);
        editCol.setCellFactory(cellFactoryEdit);
    }

    private void searchBarAction() {
        ObservableList<tableData> datas = FXCollections.observableArrayList();
        LocalDate ld = LocalDate.now();
        try {
            String sqlCommand = "SELECT * FROM interns WHERE (FirstName LIKE '" + searchBar.getText() + "%' OR LastName LIKE '" + searchBar.getText() + "%' OR OtherName LIKE '" + searchBar.getText() + "%' OR Email LIKE '" + searchBar.getText() + "%' OR Telephone LIKE '" + searchBar.getText() + "%' OR Course LIKE '" + searchBar.getText() + "%' OR RegistrationDate LIKE '" + searchBar.getText() + "%' OR Address LIKE '" + searchBar.getText() + "%') AND RegistrationDate LIKE '" + ld.getYear() + "%'";
            String emptyResult = "SELECT COUNT(ID) FROM interns WHERE (FirstName LIKE '" + searchBar.getText() + "%' OR LastName LIKE '" + searchBar.getText() + "%' OR OtherName LIKE '" + searchBar.getText() + "%' OR Email LIKE '" + searchBar.getText() + "%' OR Telephone LIKE '" + searchBar.getText() + "%' OR Course LIKE '" + searchBar.getText() + "%' OR RegistrationDate LIKE '" + searchBar.getText() + "%' OR Address LIKE '" + searchBar.getText() + "%') AND RegistrationDate LIKE '" + ld.getYear() + "%'";
            int idCounter = 0;
            Connection connection = null;
            ServerBoard.ConnectToServer cts = new ConnectToServer();
            connection = cts.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs;
            ResultSet rs1 = statement.executeQuery(emptyResult);
            while (rs1.next()) {
                idCounter = rs1.getInt("COUNT(ID)");
            }
            if (idCounter > 0) {
                rs = statement.executeQuery(sqlCommand);
                while (rs.next()) {
                    datas.addAll(new tableData(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9), rs.getString(10), rs.getString(11)));
                    tableView.setItems(datas);
                }
            } else {
                tableView.setItems(null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    Callback<TableColumn<tableData, String>, TableCell<tableData, String>> cellFactory = (param) -> {

        // setting a cell to accept button
        final TableCell<tableData, String> cell = new TableCell<tableData, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    LocalDate ld = LocalDate.now();
                    final JFXButton button = new JFXButton("View Passport");
                    button.setButtonType(JFXButton.ButtonType.FLAT);
                    button.setStyle("-fx-background-color: lightsteelblue");
                    button.setOnAction(e -> {
                        try {
                            tableData data = tableView.getItems().get(getIndex());
                            String sqlCmd = "SELECT ID, Passport FROM interns WHERE ID = '" + data.getId() + "' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
                            Connection connection = null;
                            ServerBoard.ConnectToServer cts = new ConnectToServer();
                            connection = cts.connectCord("1");
                            Statement statement = connection.createStatement();
                            ResultSet rs = statement.executeQuery(sqlCmd);

                            FXMLLoader loader = new FXMLLoader(getClass().getResource("/PassportView/PassportView.fxml"));
                            Stage stage = new Stage();
                            stage.setResizable(false);
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setScene(new Scene(loader.load()));
                            PassportView.PassportViewController controller = loader.getController();
                            if (rs.next()) {
                                controller.placePhoto(rs.getBinaryStream("Passport"), rs.getString("ID"));
                            }
                            stage.show();
                        } catch (SQLException ex) {
                            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IOException ex) {
                            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    });

                    setGraphic(button);
                    setText(null);
                }
            }
        };

        return cell;
    };

    Callback<TableColumn<tableData, String>, TableCell<tableData, String>> cellFactoryEdit = (param) -> {

        // setting a cell to accept button
        final TableCell<tableData, String> cell = new TableCell<tableData, String>() {

            @Override
            public void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    final JFXButton button = new JFXButton("Edit");
                    button.setButtonType(JFXButton.ButtonType.FLAT);
                    button.setStyle("-fx-background-color: lightsteelblue");
                    button.setFont(Font.font(12));
                    button.setOnAction(e -> {
                        try {
                            FXMLLoader loader = new FXMLLoader();
                            loader.setLocation(getClass().getResource("/updatePage/updatePage.fxml"));
                            Stage stage = new Stage();
                            stage.initModality(Modality.APPLICATION_MODAL);
                            stage.setResizable(false);
                            stage.setScene(new Scene(loader.load()));
                            updatePage.UpdatePageController controller = loader.getController();
                            stage.show();

                            tableData data = tableView.getItems().get(getIndex());
                            controller.shareData(data.getId(), data.getFirstName(), data.getLastName(), data.getOtherName(), data.getGender(), data.getTelephone(), data.getEmail(), data.getAddress(), data.getCourse(), Date.valueOf(data.getRegistrationDate()), data.getPGContact());
                        } catch (IOException ex) {
                            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
                        }

                    });

                    setGraphic(button);
                    setText(null);
                }
            }
        };

        return cell;
    };

    @FXML
    private void editAmountAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/UpdateAmount/UpdateAmount.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.setTitle("Update");
            stage.initModality(Modality.APPLICATION_MODAL);
            tableData data = tableView.getSelectionModel().getSelectedItem();
            UpdateAmount.UpdateAmountController controller = loader.getController();
            controller.markLabelAs(data.getId());
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("Choose a person.");
            alert.setTitle("Ghost entry");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
}

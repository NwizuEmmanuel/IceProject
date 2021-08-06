/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ledger;

import Admin_Server.AdminServer;
import LedgerModels.ledgerModels;
import Models.tableData;
import SearchPage.SearchPageController;
import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class LedgerController implements Initializable {

    @FXML
    private JFXButton addBtn;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private JFXDatePicker dateSearch;

    @FXML
    private JFXComboBox<String> monthsearch;

    @FXML
    private TableView<ledgerModels> tableView;

    @FXML
    private TableColumn<ledgerModels, Integer> idCol;

    @FXML
    private TableColumn<ledgerModels, String> descriptionCol;

    @FXML
    private TableColumn<ledgerModels, Date> dateCol;

    @FXML
    private TableColumn<ledgerModels, Double> expenseCol;

    @FXML
    private TableColumn<ledgerModels, Double> incomeCol;

    private void openOnInitialize() {
        LocalDate ld = LocalDate.now();
        ObservableList<ledgerModels> datas = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM icehub_admin WHERE Date LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                tableView.setItems(datas);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void tableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("Descriptions"));
        incomeCol.setCellValueFactory(new PropertyValueFactory<>("Income"));
        expenseCol.setCellValueFactory(new PropertyValueFactory<>("Expense"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            openOnInitialize();
            tableColumns();
        }

        {
            String[] months = {
                "January",
                "Febuary",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
            };
            monthsearch.getItems().addAll(months);
        }
    }

    @FXML
    private void addBtnAction() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ledgerBook/ledgerBook.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException ex) {
            Logger.getLogger(LedgerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void refreshAction() {
        ObservableList<ledgerModels> datas = FXCollections.observableArrayList();
        LocalDate ld = LocalDate.now();
        try {
            String sqlCommand = "SELECT * FROM icehub_admin WHERE Date LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCommand);
            while (rs.next()) {
                datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                tableView.setItems(datas);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SearchPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void dateSearchAction() {
        LocalDate ld = LocalDate.now();
        ObservableList<ledgerModels> datas = FXCollections.observableArrayList();
        try {
            String sqlCommand = "SELECT * FROM icehub_admin WHERE Date LIKE '" + dateSearch.getValue() + "%' AND Date LIKE '" + ld.getYear() + "%' ";
            String emptyResult = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '" + dateSearch.getValue() + "%' AND Date LIKE '" + ld.getYear() + "%' ";
            int idCounter = 0;
            Connection connection = null;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs;
            ResultSet rs1 = statement.executeQuery(emptyResult);
            while (rs1.next()) {
                idCounter = rs1.getInt("COUNT(ID)");
            }
            if (idCounter > 0) {
                rs = statement.executeQuery(sqlCommand);
                while (rs.next()) {
                    datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                    tableView.setItems(datas);
                }
            } else {
                tableView.setItems(null);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LedgerController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @FXML
    private void monthSearchAction() {
        LocalDate ld = LocalDate.now();
        try {
            ObservableList<ledgerModels> datas = FXCollections.observableArrayList();
            String sqlCommandForJan = "SELECT * FROM icehub_admin WHERE Date LIKE '%-01-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForFeb = "SELECT * FROM icehub_admin WHERE Date LIKE '%-02-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForMar = "SELECT * FROM icehub_admin WHERE Date LIKE '%-03-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForApl = "SELECT * FROM icehub_admin WHERE Date LIKE '%-04-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForMay = "SELECT * FROM icehub_admin WHERE Date LIKE '%-05-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForJun = "SELECT * FROM icehub_admin WHERE Date LIKE '%-06-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForJul = "SELECT * FROM icehub_admin WHERE Date LIKE '%-07-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForAug = "SELECT * FROM icehub_admin WHERE Date LIKE '%-08-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForSep = "SELECT * FROM icehub_admin WHERE Date LIKE '%-09-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForOct = "SELECT * FROM icehub_admin WHERE Date LIKE '%-10-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForNov = "SELECT * FROM icehub_admin WHERE Date LIKE '%-11-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String sqlCommandForDec = "SELECT * FROM icehub_admin WHERE Date LIKE '%-12-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForJan = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-01-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForFeb = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-02-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForMar = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-03-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForApl = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-04-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForMay = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-05-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForJun = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-06-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForJul = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-07-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForAug = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-08-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForSep = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-09-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForOct = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-10-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForNov = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-11-%' AND Date LIKE '" + ld.getYear() + "%' ";
            String EmptyResultForDec = "SELECT COUNT(ID) FROM icehub_admin WHERE Date LIKE '%-12-%' AND Date LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs;
            ResultSet rs1;
            int count = 0;
            switch (monthsearch.getValue()) {
                case "January":
                    rs1 = statement.executeQuery(EmptyResultForJan);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForJan);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "Febuary":
                    rs1 = statement.executeQuery(EmptyResultForFeb);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForFeb);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "March":
                    rs1 = statement.executeQuery(EmptyResultForMar);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForMar);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "April":
                    rs1 = statement.executeQuery(EmptyResultForApl);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForApl);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "May":
                    rs1 = statement.executeQuery(EmptyResultForMay);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForMay);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "June":
                    rs1 = statement.executeQuery(EmptyResultForJun);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForJun);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "July":
                    rs1 = statement.executeQuery(EmptyResultForJul);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForJul);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "August":
                    rs1 = statement.executeQuery(EmptyResultForAug);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForAug);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "September":
                    rs1 = statement.executeQuery(EmptyResultForSep);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForSep);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "October":
                    rs1 = statement.executeQuery(EmptyResultForOct);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForOct);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "November":
                    rs1 = statement.executeQuery(EmptyResultForNov);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForNov);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
                case "December":
                    rs1 = statement.executeQuery(EmptyResultForDec);
                    while (rs1.next()) {
                        count = rs1.getInt("COUNT(ID)");
                    }
                    if (count > 0) {
                        rs = statement.executeQuery(sqlCommandForDec);
                        while (rs.next()) {
                            datas.addAll(new ledgerModels(rs.getInt("ID"), rs.getString("Descriptions"), rs.getDouble("Income"), rs.getDouble("Expense"), rs.getDate("Date")));
                            tableView.setItems(datas);
                        }
                    } else {
                        tableView.setItems(null);
                    }
                    break;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LedgerController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

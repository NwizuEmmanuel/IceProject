/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DashBoard;

import Admin_Server.AdminServer;
import InternFees.InternFeeController;
import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXProgressBar;
import com.jfoenix.controls.JFXSpinner;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class DashBoardController implements Initializable {

    @FXML
    private JFXSpinner incomeSpinner;

    @FXML
    private JFXSpinner expenseSpinner;

    @FXML
    private ComboBox<String> monthSelection;

    @FXML
    private Label monthDisplay;

    @FXML
    private JFXProgressBar stillPayingBar;

    @FXML
    private Label stillPayingLabel;

    @FXML
    private JFXProgressBar donePayingBar;

    @FXML
    private Label donePayingLabel;

    @FXML
    private JFXProgressBar haventPaidBar;

    @FXML
    private Label haventPaidLabel;

    @FXML
    private Label monthDisplayForStu;

    @FXML
    private PieChart pieChart;

    @FXML
    private ToggleButton removeChartLbsBtn;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            incomeStatement();
        }

        {
            monthSelection.getItems().addAll(""
                    + "January",
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
                    "December");
        }

        {
            studentPaymentProgression();
        }
        {
            pieChartStats();
        }
    }

    @FXML
    private void refreshBtn() {
        incomeStatement();
        studentPaymentProgression();
    }

    private void incomeStatement() {
        monthDisplay.setText("Annual stats");
        LocalDate ld = LocalDate.now();
        try {
            Connection connection = null;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            String incomeSum = "SELECT SUM(Income) FROM icehub_admin WHERE Date LIKE '" + ld.getYear() + "%' ";
            String expenseSum = "SELECT SUM(Expense) FROM icehub_admin WHERE Date LIKE '" + ld.getYear() + "%' ";
            double incomeElement = 0;
            double expenseElement = 0;
            double totalElements = 0;
            Statement statement = connection.createStatement();

            ResultSet rs1;
            ResultSet rs2 = statement.executeQuery(incomeSum);
            while (rs2.next()) {
                incomeElement = rs2.getDouble("SUM(Income)");
            }
            rs1 = statement.executeQuery(expenseSum);
            while (rs1.next()) {
                expenseElement = rs1.getDouble("SUM(Expense)");
            }

            totalElements = incomeElement + expenseElement;
            double incomeFormular = ((incomeElement / totalElements) * (100 / 1)) / 100;
            double expenseFormular = ((expenseElement / totalElements) * (100 / 1)) / 100;

            incomeSpinner.setProgress(incomeFormular);
            expenseSpinner.setProgress(expenseFormular);

        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void monthSelection() {
        String month = "";
        LocalDate ld = LocalDate.now();
        switch (monthSelection.getValue()) {
            case "January":
                month = "-01-";
                monthDisplay.setText("January's income statement");
                break;
            case "Febuary":
                month = "-02-";
                monthDisplay.setText("Febuary's income statement");
                break;
            case "March":
                month = "-03-";
                monthDisplay.setText("March's income statement");
                break;
            case "April":
                month = "-04-";
                monthDisplay.setText("April's income statement");
                break;
            case "May":
                month = "-05";
                monthDisplay.setText("May's income statement");
                break;
            case "June":
                month = "-06-";
                monthDisplay.setText("June's income statement");
                break;
            case "July":
                month = "-07-";
                monthDisplay.setText("July's income statement");
                break;
            case "August":
                month = "-08-";
                monthDisplay.setText("August's income statement");
                break;
            case "September":
                month = "-09-";
                monthDisplay.setText("September's income statement");
                break;
            case "October":
                month = "-10-";
                monthDisplay.setText("October's income statement");
                break;
            case "November":
                month = "-11-";
                monthDisplay.setText("November's income statement");
                break;
            case "December":
                month = "-08-";
                monthDisplay.setText("December's income statement");
        }
        try {
            Connection connection = null;
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            String incomeSum = "SELECT SUM(Income) FROM icehub_admin WHERE Date LIKE '%" + month + "%' AND Date LIKE '" + ld.getYear() + "%'";
            String expenseSum = "SELECT SUM(Expense) FROM icehub_admin WHERE Date LIKE '%" + month + "%' AND Date LIKE '" + ld.getYear() + "%' ";
            double incomeElement = 0;
            double expenseElement = 0;
            double totalElements = 0;
            Statement statement = connection.createStatement();

            ResultSet rs1;
            ResultSet rs2 = statement.executeQuery(incomeSum);
            while (rs2.next()) {
                incomeElement = rs2.getDouble("SUM(Income)");
            }
            rs1 = statement.executeQuery(expenseSum);
            while (rs1.next()) {
                expenseElement = rs1.getDouble("SUM(Expense)");
            }

            totalElements = incomeElement + expenseElement;
            double incomeFormular = ((incomeElement / totalElements) * (100 / 1)) / 100;
            double expenseFormular = ((expenseElement / totalElements) * (100 / 1)) / 100;

            incomeSpinner.setProgress(incomeFormular);
            expenseSpinner.setProgress(expenseFormular);

        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void studentPaymentProgression() {
        try {
            LocalDate localDate = LocalDate.now();
            Connection connection = null;
            InternFees.InternFeeController feeController = new InternFeeController();
            Preferences pref = Preferences.userRoot().node(feeController.getClass().getName());
            Admin_Server.AdminServer server = new AdminServer();
            connection = server.connectCord("1");
            double stillPaying = 0;
            double donePaying = 0;
            double haventPaid = 0;
            double total = 0;
            String amountSum = "SELECT COUNT(ID) FROM ice_admin_student WHERE Date LIKE '" + localDate.getYear() + "%'";
            String stillPayingSum = "SELECT COUNT(ID) FROM ice_admin_student WHERE NOT Amount = 0 AND Amount < " + pref.getDouble("internFees", 0) + " AND DATE LIKE '" + localDate.getYear() + "%'";
            String donePayingSum = "SELECT COUNT(ID) FROM ice_admin_student WHERE Amount = " + pref.getDouble("internFees", 0) + " AND DATE LIKE '" + localDate.getYear() + "%'";
            String havantPaidSum = "SELECT COUNT(ID) FROM ice_admin_student WHERE Amount = 0 AND DATE LIKE '" + localDate.getYear() + "%'";
            Statement statement = connection.createStatement();

            ResultSet rs1;
            ResultSet rs2;
            ResultSet rs3;
            ResultSet rs4;

            rs1 = statement.executeQuery(amountSum);
            while (rs1.next()) {
                total = rs1.getDouble("COUNT(ID)");
            }

            rs2 = statement.executeQuery(stillPayingSum);
            while (rs2.next()) {
                stillPaying = rs2.getDouble("COUNT(ID)");
            }

            rs3 = statement.executeQuery(donePayingSum);
            while (rs3.next()) {
                donePaying = rs3.getDouble("COUNT(ID)");
            }

            rs4 = statement.executeQuery(havantPaidSum);
            while (rs4.next()) {
                haventPaid = rs4.getDouble("COUNT(ID)");
            }

            double stillPayingBarFormula = ((stillPaying / total) * (100 / 1)) / 100;
            double donePayingBarFormula = ((donePaying / total) * (100 / 1)) / 100;
            double haventPaidBarFormula = ((haventPaid / total) * (100 / 1)) / 100;

            double stillPayingLabelFormula = ((stillPaying / total) * (100 / 1));
            double donePayingLabelFormula = ((donePaying / total) * (100 / 1));
            double haventPaidLabelFormula = ((haventPaid / total) * (100 / 1));

            DecimalFormat df = new DecimalFormat("#");

            stillPayingBar.setProgress(stillPayingBarFormula);
            donePayingBar.setProgress(donePayingBarFormula);
            haventPaidBar.setProgress(haventPaidBarFormula);

            stillPayingLabel.setText(df.format(stillPayingLabelFormula) + "%");
            donePayingLabel.setText(df.format(donePayingLabelFormula) + "%");
            haventPaidLabel.setText(df.format(haventPaidLabelFormula) + "%");
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void pieChartStats() {
        try {
            LocalDate ld = LocalDate.now();
            Connection connection;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            String commandForSoft = "SELECT COUNT(ID) FROM interns WHERE Course = 'Software development(Desktop)' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            String commandForWeb = "SELECT COUNT(ID) FROM interns WHERE Course = 'Web development & Hosting' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            String commandForUi = "SELECT COUNT(ID) FROM interns WHERE Course = 'UI/UX' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            String commandForGra = "SELECT COUNT(ID) FROM interns WHERE Course = 'Graphics design' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            String commandForSeo = "SELECT COUNT(ID) FROM interns WHERE Course = 'SEO/Content Creation' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            String commandForMob = "SELECT COUNT(ID) FROM interns WHERE Course = 'Mobile App development(ios and android)' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";

            int webCount = 0;
            int softCount = 0;
            int uiCount = 0;
            int graCount = 0;
            int seoCount = 0;
            int mobCount = 0;

            ResultSet rsWeb;
            ResultSet rsSoft;
            ResultSet rsUi;
            ResultSet rsGra;
            ResultSet rsSeo;
            ResultSet rsMob;

            rsWeb = statement.executeQuery(commandForWeb);
            while (rsWeb.next()) {
                webCount = rsWeb.getInt("COUNT(ID)");
            }

            rsSoft = statement.executeQuery(commandForSoft);
            while (rsSoft.next()) {
                softCount = rsSoft.getInt("COUNT(ID)");
            }

            rsUi = statement.executeQuery(commandForUi);
            while (rsUi.next()) {
                uiCount = rsUi.getInt("COUNT(ID)");
            }

            rsSeo = statement.executeQuery(commandForSeo);
            while (rsSeo.next()) {
                seoCount = rsSeo.getInt("COUNT(ID)");
            }

            rsMob = statement.executeQuery(commandForMob);
            while (rsMob.next()) {
                mobCount = rsMob.getInt("COUNT(ID)");
            }

            rsGra = statement.executeQuery(commandForGra);
            while (rsGra.next()) {
                graCount = rsGra.getInt("COUNT(ID)");
            }

            ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList(
                    new PieChart.Data("Web development and hostings", webCount),
                    new PieChart.Data("Software development", softCount),
                    new PieChart.Data("UI/UX", uiCount),
                    new PieChart.Data("Graphic design", graCount),
                    new PieChart.Data("SEO content/Creation", graCount),
                    new PieChart.Data("Mobile app development", graCount)
            );
            pieChart.setData(pieData);
        } catch (SQLException ex) {
            Logger.getLogger(DashBoardController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @FXML
    private void removeChartLabels() {
        if (removeChartLbsBtn.isSelected()) {
            pieChart.setLabelsVisible(false);
            removeChartLbsBtn.setText("Add chart labels");
        } else {
            pieChart.setLabelsVisible(true);
            removeChartLbsBtn.setText("Remove chart labels");
        }
    }
}

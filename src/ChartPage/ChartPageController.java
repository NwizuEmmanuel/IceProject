/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ChartPage;

import ServerBoard.ConnectToServer;
import com.jfoenix.controls.JFXButton;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class ChartPageController implements Initializable {

    @FXML
    private BarChart<String, Number> barChart;

    @FXML
    private CategoryAxis xAxis;

    @FXML
    private NumberAxis yAxis;

    @FXML
    private JFXButton refreshBtn;

    @FXML
    private TextField totalInterns;

    private XYChart.Series<String, Number> Web_DevelopmentHosting;
    private XYChart.Series<String, Number> MobileAppDevelopment_Ios_Android;
    private XYChart.Series<String, Number> Software_development_Desktop;
    private XYChart.Series<String, Number> UI_UX;
    private XYChart.Series<String, Number> SEO_Content_Creation;
    private XYChart.Series<String, Number> Graphics_design;

    @FXML
    private void refreshBtnAction() {
        barChart.setAnimated(false);
        barChart.getData().clear();
        BarChartData();
        totalInternData();
    }

    private LocalDate ld = LocalDate.now();
    public void totalInternData() {
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            while (rs.next()) {
                totalInterns.setText((String.valueOf(rs.getInt("COUNT(ID)"))));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void BarChartData() {
        // for software development
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE Course = 'Software development(Desktop)' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            int idCount = 0;
            while (rs.next()) {
                Software_development_Desktop = new XYChart.Series<>();
                Software_development_Desktop.setName("Software development");
                Software_development_Desktop.getData().add(new XYChart.Data<>("", rs.getInt("COUNT(ID)")));
                idCount = rs.getInt("COUNT(ID)");
            }
            if (true) {
                barChart.getData().add(Software_development_Desktop);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for UI/UX
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE Course = 'UI/UX' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            int idCount = 0;
            while (rs.next()) {
                UI_UX = new XYChart.Series<>();
                UI_UX.setName("UI/UX");
                UI_UX.getData().add(new XYChart.Data<>("", rs.getInt("COUNT(ID)")));
                idCount = rs.getInt("COUNT(ID)");
            }
            if (idCount != 0) {
                barChart.getData().add(UI_UX);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for mobile development
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE Course = 'Mobile App development(ios and android)' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            int idCount = 0;
            while (rs.next()) {
                MobileAppDevelopment_Ios_Android = new XYChart.Series<>();
                MobileAppDevelopment_Ios_Android.setName("Mobile development");
                MobileAppDevelopment_Ios_Android.getData().add(new XYChart.Data<>("", rs.getInt("COUNT(ID)")));
                idCount = rs.getInt("COUNT(ID)");
            }
            if (idCount != 0) {
                barChart.getData().add(MobileAppDevelopment_Ios_Android);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for web development and hosting
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE Course = 'Web development & Hosting' AND RegistrationDate LIKE '" + ld.getYear() + "%'";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            int idCount = 0;
            while (rs.next()) {
                Web_DevelopmentHosting = new XYChart.Series<>();
                Web_DevelopmentHosting.setName("Web development and Hosting");
                Web_DevelopmentHosting.getData().add(new XYChart.Data<>("", rs.getInt("COUNT(ID)")));
                idCount = rs.getInt("COUNT(ID)");
            }
            if (idCount != 0) {
                barChart.getData().add(Web_DevelopmentHosting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        // for SEO/Content Creation
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE Course = 'SEO/Content Creation' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            int idCount = 0;
            while (rs.next()) {
                SEO_Content_Creation = new XYChart.Series<>();
                SEO_Content_Creation.setName("SEO/Content Creation");
                SEO_Content_Creation.getData().add(new XYChart.Data<>("", rs.getInt("COUNT(ID)")));
                idCount = rs.getInt("COUNT(ID)");
            }
            if (idCount != 0) {
                barChart.getData().add(SEO_Content_Creation);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }

        //for Graphics design
        try {
            String sqlCmd = "SELECT COUNT(ID) FROM interns WHERE Course = 'Graphics design' AND RegistrationDate LIKE '" + ld.getYear() + "%' ";
            Connection connection = null;
            ServerBoard.ConnectToServer server = new ConnectToServer();
            connection = server.connectCord("1");
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(sqlCmd);
            int idCount = 0;
            while (rs.next()) {
                Graphics_design = new XYChart.Series<>();
                Graphics_design.setName("Graphics design");
                Graphics_design.getData().add(new XYChart.Data<>("", rs.getInt("COUNT(ID)")));
                idCount = rs.getInt("COUNT(ID)");
            }
            if (idCount != 0) {
                barChart.getData().add(Graphics_design);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartPageController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        barChart.setTitle("Ice interns population status");
        barChart.setAnimated(true);

        BarChartData();
        totalInternData();
    }
}

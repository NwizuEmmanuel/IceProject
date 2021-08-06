/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StartPage;

import com.jfoenix.controls.JFXCheckBox;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.prefs.Preferences;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author Onyekachukwu
 */
public class StartPageController implements Initializable {

    @FXML
    private Label startLabel;

    @FXML
    private StackPane formPane;

    @FXML
    private StackPane searchPane;

    @FXML
    private StackPane emailPane;

    @FXML
    private StackPane chartPane;

    @FXML
    private JFXCheckBox startCheckBox;

    @FXML
    private void startCheckBoxAction() {
        Preferences prefs = Preferences.userRoot().node(this.getClass().getName());
        String isChecked = "isCheckedPref";
        if (startCheckBox.isSelected()) {
            prefs.putBoolean("isChecked", true);
            System.out.println("Done");
        } else if (!startCheckBox.isSelected()) {
            prefs.putBoolean("isChecked", false);
        }
    }

    @FXML
    private Label featureLabel;

    private double time = 3000;

    private void AnimationForLabel(Node node) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration(Duration.millis(time));
        fadeTransition.setFromValue(0.1);
        fadeTransition.setToValue(20);
        fadeTransition.setCycleCount(1);
        fadeTransition.setAutoReverse(false);
        fadeTransition.setNode(node);
        fadeTransition.play();

        ScaleTransition st = new ScaleTransition();
        st.setDuration(Duration.millis(time));
        st.setByX(0.5);
        st.setByY(0.5);
        st.setCycleCount(1);
        st.setNode(node);
        st.setAutoReverse(false);
        st.play();

        ParallelTransition pt = new ParallelTransition(st, fadeTransition);
        pt.play();
    }

    private void AnimationForPane(Node node) {
        TranslateTransition tt = new TranslateTransition();
        tt.setDuration(Duration.millis(time));
        tt.setFromY(300);
        tt.setToY(0);
        tt.setCycleCount(1);
        tt.setAutoReverse(false);
        tt.setNode(node);
        tt.play();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        {
            startLabel.setTextFill(Paint.valueOf("white"));
        }
        {
            featureLabel.setTextFill(Paint.valueOf("white"));
        }

        {
            AnimationForLabel(startLabel);// for the huge label
            AnimationForLabel(featureLabel);// for fearture label
            AnimationForPane(formPane);// for form pane
            AnimationForPane(searchPane);// for search pane
            AnimationForPane(emailPane);// for email pane
            AnimationForPane(chartPane);// for chart pane
        }
    }

}

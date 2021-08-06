/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FormMarker;

import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

/**
 *
 * @author Onyekachukwu
 */
public class ErrorChecker {
    public void formChecker(VBox vBox, String regex, TextField textField){
        if (!textField.getText().matches(regex)) {
            vBox.setStyle("-fx-border-color: red");
        }
    }
    
    public void formChecker(VBox vBox, String regex, ComboBox comboBox){
        if (comboBox.getValue()==null) {
            vBox.setStyle("-fx-border-color: red");
        }
    }
    
    public void formChecker(VBox vBox, String regex, DatePicker datePicker){
        if (datePicker.getValue()!= LocalDate.now()) {
            vBox.setStyle("-fx-border-color: red");
        }
    }
}

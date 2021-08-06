/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validator;

import java.time.LocalDate;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Control;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import org.controlsfx.validation.ValidationResult;
import org.controlsfx.validation.ValidationSupport;
import org.controlsfx.validation.Validator;

/**
 *
 * @author Onyekachukwu
 */
public class ComponentValidation {

    private ValidationSupport validationSupport = new ValidationSupport();

    // for textfields
    public void TextFieldValidation(TextField textField, String message) {
        validationSupport.registerValidator(textField, Validator.createEmptyValidator(message));
    }

    //for combo boxes
    public void ComboBoxValidation(ComboBox comboBox, String message) {
        validationSupport.registerValidator(comboBox, Validator.createEmptyValidator(message));
    }

    //for datepicker
    public void DatePickerValidation(DatePicker datePicker, String message) {
        validationSupport.registerValidator(datePicker, false, (Control c, LocalDate newValue)
                -> ValidationResult.fromWarningIf(c, message, !LocalDate.now().equals(newValue))
        );
    }
}

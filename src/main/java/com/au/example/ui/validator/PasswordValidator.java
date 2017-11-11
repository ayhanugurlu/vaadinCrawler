package com.au.example.ui.validator;

import com.vaadin.data.validator.AbstractValidator;
import de.steinwedel.messagebox.MessageBox;

/**
 * Created by ayhanu on 2/2/17.
 */
// Validator for validating the passwords
public final class PasswordValidator extends AbstractValidator<String> {

    public PasswordValidator() {
        super("The password provided is not valid");
    }

    @Override
    protected boolean isValidValue(String value) {
        //
        // Password must be at least 8 characters long and contain at least
        // one number
        //
        if (value != null && (value.length() < 8 || !value.matches(".*\\d.*"))) {
            MessageBox.createError().asModal(true).withCaption("Invalid password")
                    .withMessage("Password must be at least 8 characters long and contain at least one number")
                    .open();
            return false;
        }
        return true;
    }

    @Override
    public Class<String> getType() {
        return String.class;
    }
}
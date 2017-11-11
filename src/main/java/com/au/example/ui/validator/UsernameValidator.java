package com.au.example.ui.validator;

import com.vaadin.data.validator.EmailValidator;
import de.steinwedel.messagebox.MessageBox;

/**
 * Created by ayhanu on 2/2/17.
 */

// Validator for validating the passwords
public final class UsernameValidator extends EmailValidator {

    public UsernameValidator() {
        super("The username provided is not valid");
    }

    @Override
    protected boolean isValidValue(String value) {

        if (!super.isValidValue(value)) {
            MessageBox.createError().asModal(true).withCaption("username password")
                    .withMessage("Username must be an email address")
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
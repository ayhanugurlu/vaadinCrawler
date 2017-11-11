package com.au.example.view;

import com.au.example.model.User;
import com.au.example.repo.UserRepository;
import com.au.example.ui.validator.PasswordValidator;
import com.au.example.ui.validator.UsernameValidator;
import com.vaadin.data.validator.AbstractValidator;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.themes.Reindeer;

import java.util.List;

public class SimpleCreateUserView extends CustomComponent implements View, Button.ClickListener {

    public static final String NAME = "createUser";

    private final TextField email;

    private final TextField name;

    private final TextField lastName;

    private final PasswordField password;

    private final Button createUser;

    private UserRepository userRepository;


    public SimpleCreateUserView() {
        setSizeFull();

        // Create the email input field
        email = new TextField("Email:");
        email.setWidth("300px");
        email.setRequired(true);
        email.setInputPrompt("Your username (eg. joe@email.com)");
        email.addValidator(new UsernameValidator());
        email.setInvalidAllowed(false);

        // Create the password input field
        password = new PasswordField("Password:");
        password.setWidth("300px");
        password.addValidator(new PasswordValidator());
        password.setRequired(true);
        password.setValue("");
        password.setNullRepresentation("");

        // Create the username input field
        name = new TextField("Name:");
        name.setWidth("300px");
        name.setRequired(true);
        name.setInputPrompt("Your name (eg. ayhan)");
        name.setInvalidAllowed(false);

        // Create the surname input field
        lastName = new TextField("Surname:");
        lastName.setWidth("300px");
        lastName.setRequired(true);
        lastName.setInputPrompt("Your surname (eg. uğurlu)");
        lastName.setInvalidAllowed(false);

        // Create user button
        createUser = new Button("Create User", this);

        // Add both to a panel
        VerticalLayout fields = new VerticalLayout(email, password, name, lastName, createUser);
        fields.setCaption("Create an account");
        fields.setSpacing(true);
        fields.setMargin(new MarginInfo(true, true, true, false));
        fields.setSizeUndefined();

        // The view root layout
        VerticalLayout viewLayout = new VerticalLayout(fields);
        viewLayout.setSizeFull();
        viewLayout.setComponentAlignment(fields, Alignment.MIDDLE_CENTER);
        viewLayout.setStyleName(Reindeer.LAYOUT_BLUE);
        setCompositionRoot(viewLayout);
    }

    public void init(UserRepository userRepository) {
        this.userRepository = userRepository;

    }

    @Override
    public void enter(ViewChangeEvent event) {
        // focus the username field when user arrives to the login view
        email.focus();
    }



    @Override
    public void buttonClick(ClickEvent event) {

        //
        // Validate the fields using the navigator. By using validors for the
        // fields we reduce the amount of queries we have to use to the database
        // for wrongly entered passwords
        //
        if (!email.isValid() || !password.isValid() || !name.isValid() || !lastName.isValid()) {
            return;
        }

        User userEntity = new User(this.email.getValue(), this.name.getValue(), this.lastName.getValue(), this.password.getValue());


        userRepository.save(userEntity);

        // Navigate to main view
        getUI().getNavigator().navigateTo(SimpleLoginView.NAME);//


    }
}
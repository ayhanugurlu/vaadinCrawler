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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;
import de.steinwedel.messagebox.MessageBox;

import java.util.List;



public class SimpleLoginView extends CustomComponent implements View{

	public static final String NAME = "login";

	private final TextField user;

	private final PasswordField password;

	private final Button loginButton;

	private final Button createUserButton;

	private UserRepository userRepository;


	public SimpleLoginView() {
		setSizeFull();

		// Create the user input field
		user = new TextField("User:");
		user.setWidth("300px");
		user.setRequired(true);
		user.setInputPrompt("Your username (eg. joe@email.com)");
		user.addValidator(new UsernameValidator());
		user.setInvalidAllowed(false);

		// Create the password input field
		password = new PasswordField("Password:");
		password.setWidth("300px");
		password.addValidator(new PasswordValidator());
		password.setRequired(true);
		password.setValue("");
		password.setNullRepresentation("");

		// Create login button
		loginButton = new Button("Login");
		loginButton.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(ClickEvent clickEvent) {

					if (!SimpleLoginView.this.user.isValid() || !SimpleLoginView.this.password.isValid()) {
						return;
					}

					String username = SimpleLoginView.this.user.getValue();
					String password = SimpleLoginView.this.password.getValue();

					List<User> users = userRepository.findByEmailStartsWithIgnoreCase(username);

					boolean isValid = users != null && users.size()>0&&password.equals(users.get(0).getPassword());

					if (isValid) {

						// Store the current user in the service session
						getSession().setAttribute("user", username);

						// Navigate to main view
						getUI().getNavigator().navigateTo(UrlHtmlTagView.NAME);

					} else {

						// Wrong password clear the password field and refocuses it
						SimpleLoginView.this.password.setValue(null);
						SimpleLoginView.this.password.focus();

					}

				}

		});
		createUserButton = new Button("Create User", clickEvent -> {

			getUI().getNavigator().navigateTo(SimpleCreateUserView.NAME);
		});

		// Add both to a panel
		VerticalLayout fields = new VerticalLayout(user, password, loginButton,createUserButton);
		fields.setCaption("Please login to access the application.");
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

	public void init(UserRepository userRepository){
		this.userRepository = userRepository;

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// focus the username field when user arrives to the login view
		user.focus();
	}






}
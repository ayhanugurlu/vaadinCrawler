package com.au.example.ui;

import com.au.example.repo.UserRepository;
import com.au.example.view.SimpleCreateUserView;
import com.vaadin.spring.server.SpringVaadinServlet;
import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.repo.UrlHtmlTagRepository;
import com.au.example.ui.editor.UrlHtmlTagEditor;
import com.au.example.view.SimpleLoginView;
import com.au.example.view.UrlHtmlTagView;
import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SpringUI(path = "loginUI")
@Theme("valo")
public class LoginUI extends UI {
	
	@Autowired
	UrlHtmlTagRepository repo;

	@Autowired
	UserRepository userRepo;
	
	@Autowired
	UrlHtmlTagEditor editor;
	
	@Autowired
	com.au.example.service.ICrawler iCrawler;

	/**
	 * 
	 */
	private static final long serialVersionUID = 1901676399174025830L;

	@Override
	protected void init(VaadinRequest request) {

		//
		// Create a new instance of the navigator. The navigator will attach
		// itself automatically to this view.
		//
		new Navigator(this, this);

		//
		// The initial log view where the user can login to the application
		//
		getNavigator().addView(SimpleLoginView.NAME, SimpleLoginView.class);//

		//
		// Add the main view of the application
		//
		getNavigator().addView(UrlHtmlTagView.NAME, UrlHtmlTagView.class);


		//
		// Add the main view of the application
		//
		getNavigator().addView(SimpleCreateUserView.NAME, SimpleCreateUserView.class);

		getNavigator().navigateTo(SimpleLoginView.NAME);
		//
		// We use a view change handler to ensure the user is always redirected
		// to the login view if the user is not logged in.
		//
		getNavigator().addViewChangeListener(new ViewChangeListener() {

			@Override
			public boolean beforeViewChange(ViewChangeEvent event) {

				// Check if a user has logged in
				boolean isLoggedIn = getSession().getAttribute("user") != null;
				boolean isLoginView = event.getNewView() instanceof SimpleLoginView ||  event.getNewView() instanceof SimpleCreateUserView;
				boolean isCreateView = event.getNewView() instanceof SimpleCreateUserView;

				if(isCreateView){
					getNavigator().navigateTo(SimpleLoginView.NAME);
					SimpleCreateUserView simpleCreateUserView = (com.au.example.view.SimpleCreateUserView) event.getNewView();
					simpleCreateUserView.init(userRepo);
					return true;
				}
				if (!isLoggedIn && !isLoginView) {
					// Redirect to login view always if a user has not yet
					// logged in
					getNavigator().navigateTo(SimpleLoginView.NAME);
					return false;

				} else if (isLoggedIn && isLoginView) {
					// If someone tries to access to login view while logged in,
					// then cancel
					return false;
				}
				if(event.getViewName().equals(UrlHtmlTagView.NAME)){
					UrlHtmlTagView urlHtmlTagView = (com.au.example.view.UrlHtmlTagView) event.getNewView();
					urlHtmlTagView.init(repo, editor);
				}

				if(event.getViewName().equals(SimpleLoginView.NAME)){
					SimpleLoginView simpleLoginView = (com.au.example.view.SimpleLoginView) event.getNewView();
					simpleLoginView.init(userRepo);
				}

				return true;
			}

			@Override
			public void afterViewChange(ViewChangeEvent event) {

			}
		});
	}
}
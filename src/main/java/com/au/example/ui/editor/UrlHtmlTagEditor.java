package com.au.example.ui.editor;

import com.au.example.service.ICrawler;
import com.vaadin.ui.*;
import de.steinwedel.messagebox.MessageBox;
import org.springframework.beans.factory.annotation.Autowired;

import com.au.example.model.UrlHtmlTag;
import com.au.example.repo.UrlHtmlTagRepository;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.VaadinSessionScope;
import com.vaadin.ui.themes.ValoTheme;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@SpringComponent
@VaadinSessionScope
public class UrlHtmlTagEditor extends  VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1977906192529760933L;

	private final UrlHtmlTagRepository repository;

	private final ICrawler crawler;

	/**
	 * The currently edited urlHtmlTag
	 */
	private UrlHtmlTag urlHtmlTag;

	/* Fields to edit properties in UrlHtmlTag entity */
	TextField url = new TextField("Crawl Url");
	TextField htmlTag = new TextField("Html Tag");
	TextField htmlTagAttribute = new TextField("Html Tag Attiribute");


	/* Action buttons */
	Button save = new Button("Save", FontAwesome.SAVE);
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", FontAwesome.TRASH_O);
	Button startCrawler = new Button("Start Crawler");
	CssLayout actions = new CssLayout(save, cancel, delete,startCrawler);

	@Autowired
	public UrlHtmlTagEditor(UrlHtmlTagRepository repository, ICrawler crawler) {
		this.repository = repository;
		this.crawler = crawler;
		setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);
		addComponents(url, htmlTag,htmlTagAttribute, actions);

		// Configure and style components
		setSpacing(true);
		actions.setStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> repository.save(urlHtmlTag));
		delete.addClickListener(e -> repository.delete(urlHtmlTag));
		cancel.addClickListener(e -> editUrlHtmlTag(urlHtmlTag));
		startCrawler.addClickListener(new Button.ClickListener() {
			@Override
			public void buttonClick(Button.ClickEvent clickEvent) {
				Future<String>  result = crawler.scan(urlHtmlTag.getUrl(),urlHtmlTag.getHtmlTag(),urlHtmlTag.getHtmlTagAttribute());
				startCrawler.setEnabled(false);
				if(result.isDone()){
					startCrawler.setEnabled(true);
					String resultString ="";
					try {
						resultString = result.get();
					} catch (InterruptedException e) {
						resultString = "Crawler didn't run...";
					} catch (ExecutionException e) {
						resultString = "Crawler didn't run...";
					}
					MessageBox.create()
							.withCaption("Crawle Result")
							.withMessage(resultString)
							.open();
				}



			}
		});
		setVisible(false);
	}

	public interface ChangeHandler {

		void onChange();
	}

	public final void editUrlHtmlTag(UrlHtmlTag u) {
		final boolean persisted = u.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			urlHtmlTag = repository.findOne(u.getId());
		}
		else {
			urlHtmlTag = u;
		}
		cancel.setVisible(persisted);

		// Bind urlHtmlTag properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		BeanFieldGroup.bindFieldsUnbuffered(urlHtmlTag, this);

		setVisible(true);

		// A hack to ensure the whole form is visible
		save.focus();
		// Select all text in firstName field automatically
		url.selectAll();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		save.addClickListener(e -> h.onChange());
		delete.addClickListener(e -> h.onChange());
	}

}
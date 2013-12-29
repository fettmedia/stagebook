package com.fettmedia.stagebook.web.view;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.fettmedia.stagebook.web.event.LoginEvent;
import com.fettmedia.stagebook.web.menu.HorizontalMenu;
import com.fettmedia.stagebook.web.menu.MenuItem;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@VaadinView(LoginView.NAME)
public class LoginView extends Panel implements IMVPView
{
	public static final String NAME = "LoginView";
	
	private TextField userField;
    private PasswordField pwdField;
    private Label errorMsg;
	
	@Override
	public void enter(ViewChangeEvent event)
	{
	}
	
	public String getUser()
	{
		return userField.getValue();
	}
	
	public String getPassword()
	{
		return pwdField.getValue();
	}
	
	public void showErrorMessage()
	{
		errorMsg.setVisible(true);
	}
	
	@PostConstruct
	public void postConstruct()
	{
		VerticalLayout layout = new VerticalLayout();
		userField = new TextField("User: ");
		pwdField = new PasswordField("Password: ");
		errorMsg = new Label("Login failed!");
		errorMsg.setVisible(false);
		layout.addComponent(userField);
		layout.addComponent(pwdField);
		layout.addComponent(errorMsg);
		HorizontalMenu toolBar = new HorizontalMenu();
		toolBar.addItem(new MenuItem("Login", new LoginEvent()));
		layout.addComponent(toolBar);
		setContent(layout);
	}

}

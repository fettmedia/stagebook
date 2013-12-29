package com.fettmedia.stagebook.web.view;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(MainView.NAME)
@RequiresAuthentication
public class MainView extends Panel implements IMVPView
{
	static Logger log = Logger.getLogger(MainView.class);
	
	public static final String NAME = "MainView";
	
	@Override
	public void enter(ViewChangeEvent event)
	{
	}
	
	@PostConstruct
	public void postConstruct()
	{
		log.info("postConstruct");
		final VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new Label("Das ist der MAINVIEW!"));
		setContent(layout);
	}

}

package com.fettmedia.stagebook.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.fettmedia.stagebook.web.event.ShowViewEvent;
import com.fettmedia.stagebook.web.menu.MenuItem;
import com.fettmedia.stagebook.web.menu.VerticalMenu;
import com.fettmedia.stagebook.web.presenter.MainApplicationPresenter;
import com.fettmedia.stagebook.web.view.ContactListView;
import com.fettmedia.stagebook.web.view.MainView;
import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@Component
@Theme("tom")
@SuppressWarnings("serial")
@Scope("session")
public class StageBookApplication extends UI
{

//	@WebServlet(value = "/*", asyncSupported = true,
//			initParams=@WebInitParam(name="beanName", value="StageBookApplication"))
//	@VaadinServletConfiguration(productionMode = false, 
//	                            ui = StageBookApplication.class, 
//	                            widgetset = "com.fettmedia.stagebook.web.AppWidgetSet")
//	public static class Servlet extends SpringVaadinServlet
//	{
//	}
	
	private DiscoveryNavigator navigator;
	
	@Autowired
	private transient ApplicationContext appCtx;
	
	@Autowired
	private MainApplicationPresenter mainMenuPresenter;

	@Override
	protected void init(VaadinRequest request)
	{
		final VerticalMenu menu = new VerticalMenu();
		menu.addItem(new MenuItem("Startseite", new ShowViewEvent(MainView.NAME)));
		menu.addItem(new MenuItem("Kontakte", new ShowViewEvent(ContactListView.NAME)));
		
		mainMenuPresenter.setMenu(menu);
		mainMenuPresenter.setApplication(this);
		
		final VerticalLayout content = new VerticalLayout();
		final HorizontalSplitPanel layout = new HorizontalSplitPanel(menu, content);
		layout.addStyleName("main");
		layout.addStyleName(Reindeer.SPLITPANEL_SMALL);
		layout.setSplitPosition(20);
		navigator = new DiscoveryNavigator(this, content);
		
		if (appCtx.containsBean(MainView.NAME))
			System.out.println("CTX loaded successfully");
		
		
		setContent(layout);
		navigator.navigateTo(MainView.NAME);

	}
	
	public DiscoveryNavigator getNavigator()
	{
		return this.navigator;
	}

}

package com.fettmedia.stagebook.web;


import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;
import ru.xpoft.vaadin.security.ShiroSecurityNavigator;

import com.fettmedia.stagebook.domain.StageBookUser;
import com.fettmedia.stagebook.web.event.LogoutEvent;
import com.fettmedia.stagebook.web.event.ShowViewEvent;
import com.fettmedia.stagebook.web.menu.MenuItem;
import com.fettmedia.stagebook.web.menu.VerticalMenu;
import com.fettmedia.stagebook.web.presenter.MainApplicationPresenter;
import com.fettmedia.stagebook.web.shiro.StageBookNavigator;
import com.fettmedia.stagebook.web.shiro.VaadinSecurityContext;
import com.fettmedia.stagebook.web.view.ContactListView;
import com.fettmedia.stagebook.web.view.LoginView;
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
	
	private StageBookNavigator navigator;
	
	@Autowired
	private transient ApplicationContext appCtx;
	
	@Autowired
	private MainApplicationPresenter mainMenuPresenter;
	
	@Autowired
	private VaadinSecurityContext securityCtx;

	public static StageBookApplication getCurrent()
	{
		return (StageBookApplication) UI.getCurrent();
	}
	
	@Override
	protected void init(VaadinRequest request)
	{
		final VerticalMenu menu = new VerticalMenu();
		menu.addItem(new MenuItem("Startseite", new ShowViewEvent(MainView.NAME)));
		menu.addItem(new MenuItem("Kontakte", new ShowViewEvent(ContactListView.NAME)));
		menu.addItem(new MenuItem("Logout", new LogoutEvent()));
		final VerticalLayout content = new VerticalLayout();
		final HorizontalSplitPanel layout = new HorizontalSplitPanel(menu, content);
		layout.addStyleName("main");
		layout.addStyleName(Reindeer.SPLITPANEL_SMALL);
		layout.setSplitPosition(20);
		
		
		
		
		navigator = new StageBookNavigator(this, content, securityCtx);
				
		setContent(layout);
		Subject subject = securityCtx.getSubject();
		if ((subject == null) || !subject.isAuthenticated())
		{
			navigator.navigateTo(LoginView.NAME);
		}
		else
		{
		    navigator.navigateTo(MainView.NAME);
		}

	}
	
	

}


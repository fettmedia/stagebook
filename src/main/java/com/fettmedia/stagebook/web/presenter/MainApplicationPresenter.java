package com.fettmedia.stagebook.web.presenter;

import net.engio.mbassy.listener.Handler;

import org.apache.log4j.Logger;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.web.StageBookApplication;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.LoginSuccessEvent;
import com.fettmedia.stagebook.web.event.LogoutEvent;
import com.fettmedia.stagebook.web.event.ShowViewEvent;
import com.fettmedia.stagebook.web.menu.VerticalMenu;
import com.fettmedia.stagebook.web.shiro.StageBookNavigator;
import com.fettmedia.stagebook.web.shiro.VaadinSecurityContext;
import com.fettmedia.stagebook.web.view.LoginView;
import com.fettmedia.stagebook.web.view.MainView;

@Component
public class MainApplicationPresenter
{
	static Logger log = Logger.getLogger(MainApplicationPresenter.class);
	
	@Autowired
	VaadinSecurityContext securityCtx;
	
	public MainApplicationPresenter()
	{
		EventBusProvider.getEventBus().subscribe(this);
	}
	
	
	@Handler
	public void handleShowView(ShowViewEvent event)
	{
		StageBookApplication.getCurrent().getNavigator().navigateTo(event.getViewToShow());				
	}
	
	@Handler
	public void handleLoginSuccess(LoginSuccessEvent event)
	{
		StageBookNavigator nav = (StageBookNavigator) StageBookApplication.getCurrent().getNavigator();
		nav.initAuthenticated();
		nav.navigateTo(MainView.NAME);
	}
	
	@Handler
	public void handleLogout(LogoutEvent event)
	{
		Subject currentUser = securityCtx.getSubject();
		if ((currentUser != null) && (currentUser.isAuthenticated()))
		{
			currentUser.logout();
			StageBookApplication.getCurrent().getNavigator().navigateTo(LoginView.NAME);
		}
				
	}
	
//	@Handler
//	public void handleShowEditView(ShowEditViewEvent<? extends IEntity> event)
//	{
//		String params = String.format("/new/%s", event.getEntityClass().getCanonicalName());
//		long id = -1;
//		if (event.getEntity() != null)
//		{
//			id = event.getEntity().getId();
//			params = String.format("/edit/%s/%l", event.getEntityClass().getCanonicalName(), id);
//		}
//		log.info("NAVTO: " + params);
//		application.getNavigator().navigateTo(event.getViewToShow() + params);
//		log.info("AFTER NAVTO");
//	}

}

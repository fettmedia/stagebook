package com.fettmedia.stagebook.web.presenter;

import net.engio.mbassy.listener.Handler;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.web.StageBookApplication;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.ShowViewEvent;
import com.fettmedia.stagebook.web.menu.VerticalMenu;

@Component
public class MainApplicationPresenter
{
	static Logger log = Logger.getLogger(MainApplicationPresenter.class);
	private VerticalMenu menu;
	
	private StageBookApplication application;
	
	public MainApplicationPresenter()
	{
		EventBusProvider.getEventBus().subscribe(this);
	}
	
	public VerticalMenu getMenu()
	{
		return menu;
	}

	public void setMenu(VerticalMenu menu)
	{
		this.menu = menu;
	}

	public StageBookApplication getApplication()
	{
		return application;
	}

	public void setApplication(StageBookApplication application)
	{
		this.application = application;
	}

	@Handler
	public void handleShowView(ShowViewEvent event)
	{
		application.getNavigator().navigateTo(event.getViewToShow());				
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

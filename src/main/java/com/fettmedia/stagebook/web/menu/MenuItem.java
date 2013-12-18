package com.fettmedia.stagebook.web.menu;

import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.SBEvent;
import com.vaadin.ui.Button;

@SuppressWarnings("serial")
public class MenuItem extends Button
{
	//private Button button;
	private SBEvent menuEvent;
	
	public MenuItem(String caption, SBEvent event, int shortCutKey)
	{
		this(caption, event);
		setClickShortcut(shortCutKey);
	}
	
	public MenuItem(String caption, SBEvent event, int shortCutKey, int... shortCutModifier)
	{
		this(caption, event);
		setClickShortcut(shortCutKey, shortCutModifier);
	}
	
	public MenuItem(String caption, SBEvent event)
	{
		this.menuEvent = event;
		setCaption(caption);
		//button = new Button(caption);
		//setHeight("36px");
		//setWidth("100%");
		//addStyleName("menu-item");
		setImmediate(true);
		//addComponent(button);//, "top: 0; left: 0; right: 0;");
		setSizeFull();
		
		addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(ClickEvent event)
			{
				EventBusProvider.getEventBus().publish(menuEvent);
			}
		});
	}

	

}

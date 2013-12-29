package com.fettmedia.stagebook.web.menu;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class VerticalMenu extends VerticalLayout
{
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	
	public VerticalMenu()
	{
		setWidth("100.0%");
		setHeight("100.0%");
		setStyleName("sidebar");
		setImmediate(false);
		setMargin(false);
		setSpacing(false);
		addStyleName(Reindeer.LAYOUT_BLUE);
	}
	
	public void addItem(MenuItem item)
	{
		menuItems.add(item);
		addComponent(item);
	}
}

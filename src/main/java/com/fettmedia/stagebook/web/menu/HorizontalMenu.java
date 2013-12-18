package com.fettmedia.stagebook.web.menu;

import java.util.ArrayList;
import java.util.List;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
public class HorizontalMenu extends HorizontalLayout
{
	private List<MenuItem> menuItems = new ArrayList<MenuItem>();
	
	public HorizontalMenu()
	{
		setWidth("100.0%");
		setHeight("100.0%");
		setStyleName("sidebar");
		setImmediate(true);
		setMargin(true);
		addStyleName(Reindeer.LAYOUT_BLUE);
	}
	
	public void addItem(MenuItem item)
	{
		menuItems.add(item);
		addComponent(item);
	}
}

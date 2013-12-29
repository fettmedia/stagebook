package com.fettmedia.stagebook.web.view;

import javax.annotation.PostConstruct;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.fettmedia.stagebook.web.components.EntityTable;
import com.fettmedia.stagebook.web.event.EditEntityEvent;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.NewEntityEvent;
import com.fettmedia.stagebook.web.menu.HorizontalMenu;
import com.fettmedia.stagebook.web.menu.MenuItem;
import com.fettmedia.stagebook.web.event.TableValueChangedEvent;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.Reindeer;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(EntityListView.NAME)
@RequiresAuthentication
public abstract class EntityListView<ID, E> extends Panel implements IMVPView
{
	public static final String NAME = "EntityListView";
	
	private EntityTable<ID, E> table;
	
	@Override
	public void enter(ViewChangeEvent event)
	{
	}
	
	@PostConstruct
	public void postConstruct()
	{
		final VerticalLayout layout = new VerticalLayout();
		layout.addComponent(new Label("Das ist der Contact List View!"));
		
		final HorizontalMenu toolBar = new HorizontalMenu();
		toolBar.addItem(new MenuItem("Neu (F3)", new NewEntityEvent(), KeyCode.F3));
		toolBar.addItem(new MenuItem("Bearbeiten (F2)", new EditEntityEvent(), KeyCode.F2));
		layout.addComponent(toolBar);
		getTable().setSizeFull();
		getTable().setImmediate(true);
		getTable().setSelectable(true);
		getTable().addStyleName(Reindeer.TABLE_BORDERLESS);
		getTable().addStyleName(Reindeer.TABLE_STRONG);
		
		getTable().addValueChangeListener(new ValueChangeListener()
		{
			
			@Override
			public void valueChange(ValueChangeEvent event)
			{
				Object value = event.getProperty().getValue();
				EventBusProvider.getEventBus().publish(new TableValueChangedEvent(value));
			}
		});
		
		getTable().addItemClickListener(new ItemClickListener()
		{
			
			@Override
			public void itemClick(ItemClickEvent event)
			{
				if (event.isDoubleClick())
				{
					EventBusProvider.getEventBus().publish(new EditEntityEvent());
				}
			}
		});
		
		layout.addComponent(getTable());
		setContent(layout);
	}
	
	public Table getTable()
	{
		if (table == null)
			table = createTable();
		return table;
	}
	
	protected abstract EntityTable<ID, E> createTable();

}

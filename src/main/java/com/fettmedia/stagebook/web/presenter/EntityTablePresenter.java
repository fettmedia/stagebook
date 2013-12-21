package com.fettmedia.stagebook.web.presenter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import net.engio.mbassy.listener.Handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.service.IBaseService;
import com.fettmedia.stagebook.web.components.EntityTable;
import com.fettmedia.stagebook.web.event.ConfigureEntityTableEvent;
import com.fettmedia.stagebook.web.event.EditEntityEvent;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.NewEntityEvent;
import com.fettmedia.stagebook.web.event.TableValueChangedEvent;
import com.vaadin.data.util.BeanContainer;

@Component
public abstract class EntityTablePresenter<ID, E>
{
	static Logger log = Logger.getLogger(EntityTablePresenter.class);
	
	
	
	private Class<E> entityClass;
	private EntityTable<ID, E> table;
	private BeanContainer<ID, E> container;
	
	public EntityTablePresenter()
	{
		EventBusProvider.getEventBus().subscribe(this);
	}
	
	@Handler
	public void handleConfigureTable(ConfigureEntityTableEvent<ID, E> event)
	{
		table = event.getTable();
		entityClass = event.getEntityClass();
		table.setContainerDataSource(getBeanContainer(), getVisibleColumns());
		table.markAsDirty();
	}
	
	@Handler
	public void handleNewEntity(NewEntityEvent event)
	{
		doOnNewEntity();
	}
	
	@Handler void handleEditEntity(EditEntityEvent event)
	{
		doOnEditEntity();
	}
	
	@Handler void handleValueChanged(TableValueChangedEvent event)
	{
		log.info("value changed: " + event.getValue().toString());
		setSelectionId(event.getValue());
	}
	
	protected BeanContainer<ID, E> getBeanContainer()
	{
		container = new BeanContainer<ID, E>(entityClass);
		container.setBeanIdProperty(getIdProperty());
		for (E entity : listEntities())
		{
			container.addBean(entity);
		}
		return container;
	}
	
	protected Collection<?> getVisibleColumns()
	{
		ArrayList<Object> columnIds = new ArrayList<Object>();
		for (Object property : container.getContainerPropertyIds())
		{
			if (property != null && property.equals(getIdProperty()))
			{
				columnIds.add(0, property);
			}
			else if (property != null && !property.equals(getVersionProperty()))
			{
				columnIds.add(property);
			}
		}
		return columnIds;
	}
	
	public Object getIdProperty()
	{
		return "id";
	}
	
	public Object getVersionProperty()
	{
		return "version";
	}
	
	public abstract List<E> listEntities();
	
	public abstract void doOnNewEntity();
	public abstract void doOnEditEntity();
	public abstract void setSelectionId(Object value);
}

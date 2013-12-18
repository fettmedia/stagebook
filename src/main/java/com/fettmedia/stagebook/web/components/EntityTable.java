package com.fettmedia.stagebook.web.components;

import com.fettmedia.stagebook.web.event.ConfigureEntityTableEvent;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.vaadin.ui.Table;

public class EntityTable<ID, E> extends Table
{

	private static final long	serialVersionUID	= 1L;
	
	private Class<E> entityClass;
	private Class<ID> idClass;
	
	public EntityTable(Class<ID> idClass, Class<E> entityClass)
	{
		this.setEntityClass(entityClass);
		this.setIdClass(idClass);
		EventBusProvider.getEventBus().publish(new ConfigureEntityTableEvent<ID, E>(this, idClass, entityClass));
	}
	
	public void refresh()
	{
		
	}

	public Class<E> getEntityClass()
	{
		return entityClass;
	}

	public void setEntityClass(Class<E> entityClass)
	{
		this.entityClass = entityClass;
	}

	public Class<ID> getIdClass()
	{
		return idClass;
	}

	public void setIdClass(Class<ID> idClass)
	{
		this.idClass = idClass;
	}
	
	
	
	

}

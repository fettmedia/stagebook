package com.fettmedia.stagebook.web.event;

public class ShowEditViewEvent<E> extends ShowViewEvent
{

	private Class<E> entityClass;
	private E        entity;
	
	public ShowEditViewEvent(String viewToShow, Class<E> entityClass, E entity)
	{
		super(viewToShow);
		this.entityClass = entityClass;
		this.entity = entity;
	}

	public Class<E> getEntityClass()
	{
		return entityClass;
	}

	public void setEntityClass(Class<E> entityClass)
	{
		this.entityClass = entityClass;
	}

	public E getEntity()
	{
		return entity;
	}

	public void setEntity(E entity)
	{
		this.entity = entity;
	}

}

package com.fettmedia.stagebook.web.event;

import com.fettmedia.stagebook.web.components.EntityTable;

public class ConfigureEntityTableEvent<ID, E> extends SBEvent
{
	private EntityTable<ID, E> table;
	private Class<E> entityClass;
	private Class<ID> idClass;
	
	public ConfigureEntityTableEvent(EntityTable<ID, E> table, Class<ID> idClass, Class<E> entityClass)
	{
		this.table = table;
		this.entityClass = entityClass;
		this.idClass = idClass;
	}
	
	public EntityTable<ID, E> getTable()
	{
		return table;
	}
	
	public Class<E> getEntityClass()
	{
		return entityClass;
	}
	
	public Class<ID> getIdClass()
	{
		return idClass;
	}
}

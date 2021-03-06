package com.fettmedia.stagebook.web.forms;

import com.fettmedia.stagebook.domain.AbstractEntity;

public interface IEntityForm<E extends AbstractEntity<E>>
{

	public abstract E getInput();

	public abstract void commit();

	public abstract void setInput(E entity);

	public abstract Class<E> getEntityClass();

	public abstract void setEntityClass(Class<E> entityClass);

	public abstract void focusStandardField();

}
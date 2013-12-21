package com.fettmedia.stagebook.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fettmedia.stagebook.domain.AbstractEntity;
import com.fettmedia.stagebook.domain.repos.IBaseRepository;

@Transactional
public abstract class BaseService<E extends AbstractEntity<E>> implements IBaseService<E>
{
	public abstract IBaseRepository<E, Long> getRepository();
	
	public long countAll()
	{
		return getRepository().count();
	}
	
	public E find(Long id)
	{
		return getRepository().findOne(id);
	}
	
	public List<E> findAll()
	{
		return getRepository().findAll();
	}
	
	public List<E> findAll(Sort sort)
	{
		return getRepository().findAll(sort);
	}
	
	public E save(E entity)
	{
		return getRepository().save(entity);
	}
	
	public void delete(E entity)
	{
		getRepository().delete(entity);
	}

	public void flush()
	{
		getRepository().flush();
	}
}

package com.fettmedia.stagebook.domain.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fettmedia.stagebook.domain.AbstractEntity;
import com.fettmedia.stagebook.domain.repos.IBaseRepository;

@Service
@Transactional
public class BaseService<E extends AbstractEntity> implements IBaseService<E>
{
	@Autowired
	IBaseRepository<E, Long> repo;
	
	public long countAll()
	{
		return repo.count();
	}
	
	public E find(Long id)
	{
		return repo.findOne(id);
	}
	
	public List<E> findAll()
	{
		return repo.findAll();
	}
	
	public List<E> findAll(Sort sort)
	{
		return repo.findAll(sort);
	}
	
	public E save(E entity)
	{
		return repo.save(entity);
	}
	
	public void delete(E entity)
	{
		repo.delete(entity);
	}

	public void flush()
	{
		repo.flush();
	}
}

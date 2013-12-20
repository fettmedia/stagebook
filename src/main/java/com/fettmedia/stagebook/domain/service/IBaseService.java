package com.fettmedia.stagebook.domain.service;

import java.util.List;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

public interface IBaseService<E>
{
	public long countAll();
	public List<E> findAll();
	public List<E> findAll(Sort sort);
	public E find(Long id);
	
	public E save(E entity);
	public void delete(E entity);
	
	public void flush();
}

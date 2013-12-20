package com.fettmedia.stagebook.domain.repos;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import com.fettmedia.stagebook.domain.AbstractEntity;

@NoRepositoryBean
public class BaseRepository<E extends AbstractEntity, ID extends Serializable> extends SimpleJpaRepository<E, ID> implements
		IBaseRepository<E, ID>
{

	public BaseRepository(Class<E> domainClass, EntityManager em)
	{
		super(domainClass, em);
	}

}

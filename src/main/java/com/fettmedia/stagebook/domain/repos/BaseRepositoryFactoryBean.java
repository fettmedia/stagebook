package com.fettmedia.stagebook.domain.repos;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactory;
import org.springframework.data.jpa.repository.support.JpaRepositoryFactoryBean;
import org.springframework.data.repository.core.RepositoryMetadata;
import org.springframework.data.repository.core.support.RepositoryFactorySupport;

import com.fettmedia.stagebook.domain.AbstractEntity;

public class BaseRepositoryFactoryBean<R extends JpaRepository<E, ID>, E extends AbstractEntity<E>, ID extends Serializable>
		extends JpaRepositoryFactoryBean<R, E, ID>
{
	protected RepositoryFactorySupport createRepositoryFactory(
			EntityManager entityManager)
	{
		return new BaseRepositoryFactory<E, ID>(entityManager);
	}

	private static class BaseRepositoryFactory<E extends AbstractEntity<E>, ID extends Serializable>
			extends JpaRepositoryFactory
	{

		private EntityManager	entityManager;

		public BaseRepositoryFactory(EntityManager entityManager)
		{
			super(entityManager);
			this.entityManager = entityManager;
		}

		protected Object getTargetRepository(RepositoryMetadata metadata)
		{
			Class<E> domainClass = (Class<E>) metadata.getDomainType();
			BaseRepository<E, ID> result = new BaseRepository<E, ID>(domainClass, entityManager);
			return result;
		}

		protected Class<?> getRepositoryBaseClass(RepositoryMetadata metadata)
		{

			// The RepositoryMetadata can be safely ignored, it is used by the
			// JpaRepositoryFactory
			// to check for QueryDslJpaRepository's which is out of scope.
			return BaseRepository.class;
		}

	}

}

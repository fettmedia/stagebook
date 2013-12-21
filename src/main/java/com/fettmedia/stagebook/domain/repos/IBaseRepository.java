package com.fettmedia.stagebook.domain.repos;
import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import com.fettmedia.stagebook.domain.AbstractEntity;

//@Repository
//@RooJpaRepository(domainType = Contact.class)
@NoRepositoryBean
public interface IBaseRepository<E extends AbstractEntity<E>, ID extends Serializable> extends JpaSpecificationExecutor<E>, JpaRepository<E, ID> {
}

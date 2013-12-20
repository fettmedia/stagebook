package com.fettmedia.stagebook.domain.repos;

import org.springframework.stereotype.Repository;

import com.fettmedia.stagebook.domain.Contact;

@Repository
public interface IContactRepository extends IBaseRepository<Contact, Long>
{

}

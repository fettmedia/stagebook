package com.fettmedia.stagebook.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fettmedia.stagebook.domain.Contact;
import com.fettmedia.stagebook.domain.repos.IBaseRepository;
import com.fettmedia.stagebook.domain.repos.IContactRepository;

@Service
public class ContactService extends BaseService<Contact>
{

	@Autowired
	IContactRepository repo;
	
	@Override
	public IContactRepository getRepository()
	{
		return repo;
	}

}

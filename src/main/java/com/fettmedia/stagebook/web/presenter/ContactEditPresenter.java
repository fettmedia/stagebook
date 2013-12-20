package com.fettmedia.stagebook.web.presenter;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.Contact;
import com.fettmedia.stagebook.domain.service.IBaseService;

@Component
public class ContactEditPresenter extends EntityFormPresenter<Long, Contact>
{
	static Logger log = Logger.getLogger(ContactEditPresenter.class);
	
	@Autowired
	IBaseService<Contact> service;
	
	public ContactEditPresenter()
	{
		log.info("ContactEditPresenter.init");
	}
	
	@Override
	public Contact createEntity()
	{
		return new Contact();
	}

	@Override
	protected void doOnEnter(String params)
	{
	    StringTokenizer tokenizer = new StringTokenizer(params, "/");
		String verb = tokenizer.nextToken();
		Long id = new Long(-1);
		if (tokenizer.hasMoreTokens())
		  id = Long.parseLong(tokenizer.nextToken());
		
		if (verb.equals("new"))
		{
			getForm().setInput(createEntity());
		}
		if (verb.equals("edit"))
		{
			Contact c = service.find(id);
			getForm().setInput(c);
		}
		
	}

}

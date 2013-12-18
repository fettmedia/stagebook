package com.fettmedia.stagebook.web.presenter;

import java.util.StringTokenizer;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.Contact;

@Component
public class ContactEditPresenter extends EntityFormPresenter<Long, Contact>
{
	static Logger log = Logger.getLogger(ContactEditPresenter.class);
	
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
			Contact c = Contact.findContact(id);
			getForm().setInput(c);
		}
		
	}

}

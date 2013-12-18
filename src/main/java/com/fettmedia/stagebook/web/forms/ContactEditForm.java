package com.fettmedia.stagebook.web.forms;

import com.fettmedia.stagebook.domain.Contact;

@SuppressWarnings("serial")
public class ContactEditForm extends EntityForm<Contact>
{

	public ContactEditForm()
	{
		setEntityClass(Contact.class);
		setStdFieldId("companyName");
	}
	
}

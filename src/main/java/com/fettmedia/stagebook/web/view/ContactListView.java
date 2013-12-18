package com.fettmedia.stagebook.web.view;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.fettmedia.stagebook.domain.Contact;
import com.fettmedia.stagebook.web.components.EntityTable;

@SuppressWarnings("serial")
@Component
@Scope("prototype")
@VaadinView(ContactListView.NAME)
public class ContactListView extends EntityListView<Long, Contact>
{
	static Logger log = Logger.getLogger(ContactListView.class);
	public static final String NAME = "ContactListView";
	
	@Override
	protected EntityTable<Long, Contact> createTable()
	{
		return new EntityTable<Long, Contact>(Long.class, Contact.class);
	}

}

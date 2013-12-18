package com.fettmedia.stagebook.web.view;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.fettmedia.stagebook.domain.Contact;
import com.fettmedia.stagebook.web.forms.ContactEditForm;
import com.fettmedia.stagebook.web.forms.EntityForm;

@SuppressWarnings("serial")
@Component
@Scope("session")
@VaadinView(ContactEditView.NAME)
public class ContactEditView extends EntityEditView<Long, Contact>
{
	public static final String NAME = "ContactEditView";

	@Override
	public EntityForm<Contact> createForm()
	{
		return new ContactEditForm();
	}

}

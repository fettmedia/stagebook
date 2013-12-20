package com.fettmedia.stagebook.web.presenter;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.Contact;
import com.fettmedia.stagebook.domain.service.IBaseService;
import com.fettmedia.stagebook.web.view.ContactEditView;
import com.vaadin.ui.UI;

@Component
public class ContactListPresenter extends EntityTablePresenter<Long, Contact>
{
		
	static Logger log = Logger.getLogger(ContactListPresenter.class);
	
	private Long selectedId;
	
	public ContactListPresenter()
	{
		log.info("ContactListPresenter.init");
	}

	@Override
	public void doOnNewEntity()
	{
		UI.getCurrent().getNavigator().navigateTo(ContactEditView.NAME + "/new");		
	}
	
	@Override
	public void doOnEditEntity()
	{
		if (selectedId > 0)
		{
			UI.getCurrent().getNavigator().navigateTo(ContactEditView.NAME + "/edit/" + selectedId);
		}
	}

	@Override
	public void setSelectionId(Object value)
	{
		selectedId = Long.parseLong(value.toString());		
	}

}

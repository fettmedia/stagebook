package com.fettmedia.stagebook.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.service.ContactService;
import com.fettmedia.stagebook.domain.service.IBaseService;

@Component
public class ContactDataOnDemand extends DataOnDemand<Contact>
{

	@Autowired
	ContactService service;
	
	@Override
	protected void populateEntity(Contact obj, int index)
	{
		obj.setCompanyName("companyName_" + index);	
		obj.setFirstName("firstName_" + index);
		obj.setLastName("lastName_" + index);
	}

	@Override
	protected Contact createEntity()
	{
		return new Contact();
	}

	@Override
	public IBaseService<Contact> getService()
	{
		return service;
	}

}

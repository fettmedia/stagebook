package com.fettmedia.stagebook.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.service.IBaseService;
import com.fettmedia.stagebook.domain.service.StageBookUserService;

@Component
@RooDataOnDemand(entity=Contact.class)
public class StageBookUserDataOnDemand extends DataOnDemand<StageBookUser>
{
	@Autowired
	StageBookUserService service;

	@Override
	protected StageBookUser createEntity()
	{
		return new StageBookUser();
	}

	@Override
	protected void populateEntity(StageBookUser obj, int index)
	{
		obj.setUserName("userName_" + index);
		obj.setUserPassword("userPassword_" + index);
	}

	@Override
	public IBaseService<StageBookUser> getService()
	{
		return service;
	}

}

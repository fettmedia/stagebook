package com.fettmedia.stagebook.web.event;

import com.fettmedia.stagebook.web.forms.IEntityForm;

public class EntityFormOnCreateEvent extends SBEvent
{
	private IEntityForm<?> form;
	
	public EntityFormOnCreateEvent(IEntityForm<?> form)
	{
		this.setForm(form);
	}

	public IEntityForm<?> getForm()
	{
		return form;
	}

	public void setForm(IEntityForm<?> form)
	{
		this.form = form;
	}
}

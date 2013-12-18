package com.fettmedia.stagebook.web.event;

import com.fettmedia.stagebook.web.forms.EntityForm;

public class EntityFormOnCreateEvent extends SBEvent
{
	private EntityForm<?> form;
	
	public EntityFormOnCreateEvent(EntityForm<?> form)
	{
		this.setForm(form);
	}

	public EntityForm<?> getForm()
	{
		return form;
	}

	public void setForm(EntityForm<?> form)
	{
		this.form = form;
	}
}

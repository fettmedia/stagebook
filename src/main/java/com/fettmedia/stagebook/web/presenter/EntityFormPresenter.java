package com.fettmedia.stagebook.web.presenter;

import net.engio.mbassy.listener.Handler;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.AbstractEntity;
import com.fettmedia.stagebook.domain.IEntity;
import com.fettmedia.stagebook.domain.service.IBaseService;
import com.fettmedia.stagebook.web.event.EntityEditViewOnEnterEvent;
import com.fettmedia.stagebook.web.event.EntityFormOnCreateEvent;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.SaveEntityAsEvent;
import com.fettmedia.stagebook.web.event.SaveEntityEvent;
import com.fettmedia.stagebook.web.forms.IEntityForm;
import com.vaadin.ui.Notification;

@Component
public abstract class EntityFormPresenter<ID, E extends AbstractEntity<E>>
{

	static Logger log = Logger.getLogger(EntityFormPresenter.class);
	
	@Autowired
	IBaseService<E> service;
	
	private IEntityForm<E> form;
	
	public EntityFormPresenter()
	{
		EventBusProvider.getEventBus().subscribe(this);
	}
	
	@SuppressWarnings("unchecked")
	@Handler
	public void handleEntityFormCreate(EntityFormOnCreateEvent event)
	{
		this.form =  (IEntityForm<E>) event.getForm();
	}
	
	@Handler
	public void handleSaveEntity(SaveEntityEvent event)
	{
		form.commit();
		service.save(form.getInput());
	    Notification.show("Erfolgreich gespeichert", Notification.Type.HUMANIZED_MESSAGE);
	    getForm().focusStandardField();
	}
	
	@Handler
	public void handleSaveEntityAs(SaveEntityAsEvent event)
	{
		form.commit();
		E sourceObj = form.getInput();
		E targetObj = sourceObj.copy();
		service.save(targetObj);
		Notification.show("Erfolgreich ALS NEUER Datensatz gespeichert", Notification.Type.HUMANIZED_MESSAGE);
		getForm().focusStandardField();
	}
	
	
	@Handler
	public void handleOnEnter(EntityEditViewOnEnterEvent event)
	{
		log.info("START handleOnEnter");
		doOnEnter(event.getParameters());
	}
	
	protected IEntityForm<E> getForm()
	{
		return form;
	}
	
	
	protected void doOnEnter(String params)
	{
				
	}
	
	public abstract E createEntity();
	
}

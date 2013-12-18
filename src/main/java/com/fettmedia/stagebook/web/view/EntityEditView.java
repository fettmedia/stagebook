package com.fettmedia.stagebook.web.view;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.VaadinView;

import com.fettmedia.stagebook.domain.IEntity;
import com.fettmedia.stagebook.web.event.EntityEditViewOnEnterEvent;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.forms.EntityForm;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.ui.VerticalLayout;

@SuppressWarnings("serial")
@Component
@Scope("session")
@VaadinView(EntityEditView.NAME)
public abstract class EntityEditView<ID, E extends IEntity<E>> extends VerticalLayout implements IMVPView
{
	static Logger log = Logger.getLogger(EntityEditView.class);
	public final static String NAME = "EntityEditView";
	
	private EntityForm<E> form;
	
	@Override
	public void enter(ViewChangeEvent event)
	{
		log.info("enter: " + event.getParameters());	
		EventBusProvider.getEventBus().publish(new EntityEditViewOnEnterEvent(event.getParameters()));
		log.info("enter: FINISHED");
	}
	
	public abstract EntityForm<E> createForm();
	
	public EntityForm<E> getForm()
	{
		if (form == null)
		{
			form = createForm();
			addComponent(getForm());
		}
		return form;
	}

	@PostConstruct
	public void postConstruct()
	{
		log.info("postConstruct");
		getForm().setImmediate(true);
		getForm().setSizeFull();
	}

}

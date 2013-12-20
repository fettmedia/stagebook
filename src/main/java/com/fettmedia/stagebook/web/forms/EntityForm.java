package com.fettmedia.stagebook.web.forms;

import org.apache.log4j.Logger;

import com.fettmedia.stagebook.domain.AbstractEntity;
import com.fettmedia.stagebook.domain.IEntity;
import com.fettmedia.stagebook.web.event.EntityFormOnCreateEvent;
import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.SaveEntityAsEvent;
import com.fettmedia.stagebook.web.event.SaveEntityEvent;
import com.fettmedia.stagebook.web.menu.HorizontalMenu;
import com.fettmedia.stagebook.web.menu.MenuItem;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup.CommitException;
import com.vaadin.data.util.BeanItem;
import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.event.ShortcutAction.ModifierKey;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Field;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextField;

@SuppressWarnings("serial")
public class EntityForm<E extends AbstractEntity<E>> extends CustomComponent implements IEntityForm<E>
{
	static Logger log = Logger.getLogger(EntityForm.class);
	private Class<E> entityClass;
	private BeanItem<E> item;
	
	private FormLayout layout = new FormLayout();
	private HorizontalMenu formToolbar;
	private FieldGroup binder;
	
	private boolean isInitialized = false;
	
	private Object stdFieldId = null;
	
	public EntityForm()
	{
		fireOnCreate();
	}
	
	protected void fireOnCreate()
	{
		EventBusProvider.getEventBus().publish(new EntityFormOnCreateEvent(this));
	}
	
	protected void generateFields()
	{
		for (Object propertyId : item.getItemPropertyIds())
		{
			Field<?> field = createField(getBinder(), propertyId);
			if (field != null)
			{
				if (field instanceof TextField)
					((TextField) field).setNullRepresentation("");
				
			  layout.addComponent(field);
			}
		}
		setCompositionRoot(layout);
		focusStandardField();
	}
	
	public FieldGroup getBinder()
	{
		if (binder == null)
		{
			binder = new FieldGroup(item);
			log.info("binder created");
		}
		return binder;
	}

	protected void createFormToolbar()
	{
		formToolbar = new HorizontalMenu();
		formToolbar.addItem(new MenuItem("Speichern (Strg+S)", new SaveEntityEvent(), KeyCode.S, ModifierKey.CTRL));
		formToolbar.addItem(new MenuItem("Speichern ALS (Shift+Strg+S)", new SaveEntityAsEvent(), KeyCode.S, ModifierKey.SHIFT, ModifierKey.CTRL));
		layout.addComponent(formToolbar);
	}
	protected Field<?> createField(FieldGroup binder, Object propertyId)
	{
		Field<?> field;
		if (getIdProperty().equals(propertyId) || getVersionProperty().equals(propertyId))
		{
			return null;
		}
		field = getBinder().buildAndBind(propertyId);
		return field;
	}
	
	/* (non-Javadoc)
	 * @see com.fettmedia.stagebook.web.forms.IEntityForm#getInput()
	 */
	@Override
	public E getInput()
	{
		E entity = item.getBean();
		return entity;
	}
	
	/* (non-Javadoc)
	 * @see com.fettmedia.stagebook.web.forms.IEntityForm#commit()
	 */
	@Override
	public void commit()
	{
		try
		{
			getBinder().commit();
		}
		catch (CommitException e)
		{
			// TODO: Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/* (non-Javadoc)
	 * @see com.fettmedia.stagebook.web.forms.IEntityForm#setInput(E)
	 */
	@Override
	public void setInput(E entity)
	{
		item = new BeanItem<E>(entity);
		log.info("BeanItem created");
		getBinder().setItemDataSource(item);
		if (!isInitialized)
		{
		    generateFields();
		    createFormToolbar();
		    isInitialized = true;
		}
	}
	
	public Object getIdProperty()
	{
		return "id";
	}
	
	public Object getVersionProperty()
	{
		return "version";
	}

	/* (non-Javadoc)
	 * @see com.fettmedia.stagebook.web.forms.IEntityForm#getEntityClass()
	 */
	@Override
	public Class<E> getEntityClass()
	{
		return entityClass;
	}

	/* (non-Javadoc)
	 * @see com.fettmedia.stagebook.web.forms.IEntityForm#setEntityClass(java.lang.Class)
	 */
	@Override
	public void setEntityClass(Class<E> entityClass)
	{
		this.entityClass = entityClass;
	}

	public Object getStdFieldId()
	{
		return stdFieldId;
	}

	public void setStdFieldId(Object stdFieldId)
	{
		this.stdFieldId = stdFieldId;
	}
	
	/* (non-Javadoc)
	 * @see com.fettmedia.stagebook.web.forms.IEntityForm#focusStandardField()
	 */
	@Override
	public void focusStandardField()
	{
		if ((binder != null) && (stdFieldId != null))
		{
			Field<?> field = binder.getField(stdFieldId);
			field.focus();
		}
	}
}

package com.fettmedia.stagebook.domain;

import com.fettmedia.stagebook.domain.service.IBaseService;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.dod.RooDataOnDemand;
import org.springframework.stereotype.Component;

@Configurable
@Component
//@RooDataOnDemand(entity = Contact.class)
public abstract class DataOnDemand<E extends AbstractEntity<E>>
{

	private Random			rnd	= new SecureRandom();

	private List<E>	data;

	

	protected abstract E createEntity();
	protected abstract void populateEntity(E obj, int index);
	public abstract IBaseService<E> getService();
	
	public E getNewTransientEntity(int index)
	{
		E obj = createEntity();
		populateEntity(obj, index);
		return obj;
	}

	public E getSpecificEntity(int index)
	{
		init();
		if (index < 0)
		{
			index = 0;
		}
		if (index > (data.size() - 1))
		{
			index = data.size() - 1;
		}
		E obj = data.get(index);
		Long id = obj.getId();
		return getService().find(id);
	}

	public E getRandomEntity()
	{
		init();
		E obj = data.get(rnd.nextInt(data.size()));
		Long id = obj.getId();
		return getService().find(id);
	}

	public boolean modifyEntity(E obj)
	{
		return false;
	}

	public void init()
	{
		int from = 0;
		int to = 10;
		data = getService()
				.findAll(new org.springframework.data.domain.PageRequest(from
						/ to, to).getSort());
		if (data == null)
		{
			throw new IllegalStateException(
					"Find entries implementation for 'Contact' illegally returned null");
		}
		if (!data.isEmpty())
		{
			return;
		}

		data = new ArrayList<E>();
		for (int i = 0; i < 10; i++)
		{
			E obj = getNewTransientEntity(i);
			try
			{
				getService().save(obj);
			}
			catch (final ConstraintViolationException e)
			{
				final StringBuilder msg = new StringBuilder();
				for (Iterator<ConstraintViolation<?>> iter = e
						.getConstraintViolations().iterator(); iter.hasNext();)
				{
					final ConstraintViolation<?> cv = iter.next();
					msg.append("[")
							.append(cv.getRootBean().getClass().getName())
							.append(".").append(cv.getPropertyPath())
							.append(": ").append(cv.getMessage())
							.append(" (invalid value = ")
							.append(cv.getInvalidValue()).append(")")
							.append("]");
				}
				throw new IllegalStateException(msg.toString(), e);
			}
			getService().flush();
			data.add(obj);
		}
	}
}

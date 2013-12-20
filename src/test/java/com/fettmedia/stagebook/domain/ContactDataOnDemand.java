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
@RooDataOnDemand(entity = Contact.class)
public class ContactDataOnDemand
{

	private Random			rnd	= new SecureRandom();

	private List<Contact>	data;

	@Autowired
	IBaseService<Contact>	service;

	public Contact getNewTransientContact(int index)
	{
		Contact obj = new Contact();
		setCompanyName(obj, index);
		setFirstName(obj, index);
		setLastName(obj, index);
		return obj;
	}

	public void setCompanyName(Contact obj, int index)
	{
		String companyName = "companyName_" + index;
		obj.setCompanyName(companyName);
	}

	public void setFirstName(Contact obj, int index)
	{
		String firstName = "firstName_" + index;
		obj.setFirstName(firstName);
	}

	public void setLastName(Contact obj, int index)
	{
		String lastName = "lastName_" + index;
		obj.setLastName(lastName);
	}

	public Contact getSpecificContact(int index)
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
		Contact obj = data.get(index);
		Long id = obj.getId();
		return service.find(id);
	}

	public Contact getRandomContact()
	{
		init();
		Contact obj = data.get(rnd.nextInt(data.size()));
		Long id = obj.getId();
		return service.find(id);
	}

	public boolean modifyContact(Contact obj)
	{
		return false;
	}

	public void init()
	{
		int from = 0;
		int to = 10;
		data = service
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

		data = new ArrayList<Contact>();
		for (int i = 0; i < 10; i++)
		{
			Contact obj = getNewTransientContact(i);
			try
			{
				service.save(obj);
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
			service.flush();
			data.add(obj);
		}
	}
}

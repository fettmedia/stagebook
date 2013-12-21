package com.fettmedia.stagebook.domain;

import com.fettmedia.stagebook.domain.service.ContactService;
import com.fettmedia.stagebook.domain.service.IBaseService;
import com.fettmedia.stagebook.domain.EntityIntegrationTest;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
public class ContactIntegrationTest extends EntityIntegrationTest<Contact>
{
	@Autowired
	ContactDataOnDemand		dod;

	@Autowired
	ContactService	service;

	@Override
	protected DataOnDemand<Contact> getDod()
	{
		return dod;
	}

	@Override
	protected IBaseService<Contact> getService()
	{
		return service;
	}

	@Override
	protected String getEntityName()
	{
		return "Contact";
	}
	
	@Test
	@Transactional
	public void testMarker()
	{
	}

	
	

	

	

	

	

	

	

	

	
	
}

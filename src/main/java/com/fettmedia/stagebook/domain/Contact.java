package com.fettmedia.stagebook.domain;

import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class Contact extends StageBookEntity<Contact>
{

	/**
     */
	private String	companyName;

	/**
     */
	private String	firstName;

	/**
     */
	private String	lastName;

	@Override
	public Contact copy()
	{
		Contact c = new Contact();
		c.setCompanyName(this.getCompanyName());
		c.setFirstName(this.getFirstName());
		c.setLastName(this.getLastName());
		return c;
    }
}

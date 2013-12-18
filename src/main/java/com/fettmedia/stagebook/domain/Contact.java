package com.fettmedia.stagebook.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.activerecord.RooJpaActiveRecord;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaActiveRecord
public class Contact implements IEntity<Contact> {

    /**
     */
    private String firstName;

    /**
     */
    private String lastName;

    /**
     */
    private String companyName;

	
	@Override
	public Contact copy(Contact source)
	{
		Contact theCopy = new Contact();
		theCopy.setCompanyName(source.getCompanyName());
		theCopy.setFirstName(source.getFirstName());
		theCopy.setLastName(source.getLastName());
		return theCopy;
	}
}

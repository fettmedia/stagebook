package com.fettmedia.stagebook.domain;
import org.springframework.roo.addon.javabean.RooJavaBean;
import org.springframework.roo.addon.jpa.entity.RooJpaEntity;
import org.springframework.roo.addon.tostring.RooToString;

@RooJavaBean
@RooToString
@RooJpaEntity
public class StageBookUser extends AbstractEntity<StageBookUser> {

    /**
     */
    private String userName;

    /**
     */
    private String userPassword;

	@Override
	public StageBookUser copy()
	{
		StageBookUser theCopy = new StageBookUser();
		theCopy.setUserName(this.getUserName());
		theCopy.setUserPassword(this.getUserPassword());
		return theCopy;
	}
    
    

}

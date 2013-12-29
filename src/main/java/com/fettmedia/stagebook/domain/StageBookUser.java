package com.fettmedia.stagebook.domain;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.util.ByteSource;
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
    
    private String salt;
    
    public void setUserPassword(String userPassword)
    {
    	SecureRandomNumberGenerator rnd = new SecureRandomNumberGenerator();
    	ByteSource salt = rnd.nextBytes();
    	Sha256Hash hasher = new Sha256Hash(userPassword, salt);
    	setSalt(salt.toHex());
    	this.userPassword = hasher.toHex();
    }

	@Override
	public StageBookUser copy()
	{
		StageBookUser theCopy = new StageBookUser();
		theCopy.setUserName(this.getUserName());
		theCopy.setUserPassword(this.getUserPassword());
		return theCopy;
	}
    
    

}

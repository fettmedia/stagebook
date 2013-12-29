package com.fettmedia.stagebook.web.shiro;

import javax.annotation.PostConstruct;

import org.apache.shiro.authc.SimpleAccount;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.Sha256CredentialsMatcher;
import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.Sha256Hash;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.domain.StageBookUser;
import com.fettmedia.stagebook.domain.service.StageBookUserService;

@Component
public class StageBookRealm extends SimpleAccountRealm
{	
	@Autowired
	StageBookUserService service;
	
	public StageBookRealm()
	{
		super();
		
	}
	
	@PostConstruct
	public void postConstruct()
	{
		StageBookUser tom = new StageBookUser();
		tom.setUserName("tom");
		tom.setUserPassword("admin");
		service.save(tom);
		
		HashedCredentialsMatcher matcher = new HashedCredentialsMatcher(Sha256Hash.ALGORITHM_NAME);
		setCredentialsMatcher(matcher);
		
		for (StageBookUser user : service.findAll())
		{
			addUser(user);
		}
	}
	
	protected void addUser(StageBookUser user)
	{
		SimplePrincipalCollection principals = new  SimplePrincipalCollection(user.getUserName(), "");
		ByteSource salt = Sha256Hash.fromHexString(user.getSalt());
		SimpleAccount account = new SimpleAccount(principals, user.getUserPassword(), salt);
		add(account);
	}
}

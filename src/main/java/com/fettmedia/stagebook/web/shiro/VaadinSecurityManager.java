package com.fettmedia.stagebook.web.shiro;

import java.util.Collection;

import org.apache.log4j.Logger;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SubjectFactory;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.mgt.SessionManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.LoginFailedEvent;
import com.fettmedia.stagebook.web.event.LoginSuccessEvent;
import com.fettmedia.stagebook.web.view.MainView;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.UI;

@Component
public class VaadinSecurityManager extends DefaultSecurityManager
{
	static Logger log = Logger.getLogger(VaadinSecurityManager.class);
	
	public VaadinSecurityManager()
	{
		super();
	}
	
	public VaadinSecurityManager(Realm singleRealm)
	{
		super(singleRealm);
	}
	
	public VaadinSecurityManager(Collection<Realm> realms)
	{
		super(realms);
	}
	
	@Override
	protected void onSuccessfulLogin(AuthenticationToken token,
			AuthenticationInfo info, Subject subject)
	{
		super.onSuccessfulLogin(token, info, subject);
		VaadinSession session = VaadinSession.getCurrent();
		session.setAttribute(VaadinSecurityContext.SUBJECT_ATTRIBUTE, subject);
		EventBusProvider.getEventBus().publish(new LoginSuccessEvent());
	}
	
	@Override
	protected void onFailedLogin(AuthenticationToken token, AuthenticationException ae, Subject subject)
	{
		super.onFailedLogin(token, ae, subject);
		EventBusProvider.getEventBus().publish(new LoginFailedEvent());
	}
	
	@Override
	public void logout(Subject subject)
	{
		super.logout(subject);
		VaadinSession session = VaadinSession.getCurrent();
		session.setAttribute(VaadinSecurityContext.SUBJECT_ATTRIBUTE, null);
	}
	
	
	@Override
	@Autowired
	public void setSessionManager(SessionManager sessionManager) 
	{
		super.setSessionManager(sessionManager);
	}
	
	@Override
	@Autowired
	public void setRealm(Realm realm)
	{
		super.setRealm(realm);
		if (realm instanceof StageBookRealm)
		{
			log.debug("StageBookRealm assigned");
		}
	}
	
	@Override
	protected SubjectContext createSubjectContext()
	{
		DefaultSubjectContext subjectCtx = new DefaultSubjectContext();
		subjectCtx.setSecurityManager(this);
		return subjectCtx;
	}
	
	
}

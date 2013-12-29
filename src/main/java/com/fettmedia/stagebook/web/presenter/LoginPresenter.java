package com.fettmedia.stagebook.web.presenter;

import net.engio.mbassy.listener.Handler;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.LoginEvent;
import com.fettmedia.stagebook.web.event.LoginFailedEvent;
import com.fettmedia.stagebook.web.shiro.VaadinSecurityContext;
import com.fettmedia.stagebook.web.view.LoginView;

@Component
public class LoginPresenter
{
	@Autowired
	LoginView view;
	
	@Autowired
	VaadinSecurityContext securityCtx;
	
	public LoginPresenter()
	{
		EventBusProvider.getEventBus().subscribe(this);
	}
	
	@Handler
	public void handleLogin(LoginEvent event)
	{
		Subject currentUser = securityCtx.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(view.getUser(), view.getPassword());
		currentUser.login(token);
	}
	
	@Handler
	public void handleFailedLogin(LoginFailedEvent event)
	{
		view.showErrorMessage();
	}

}

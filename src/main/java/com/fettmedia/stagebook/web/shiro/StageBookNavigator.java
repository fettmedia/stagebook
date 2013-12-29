package com.fettmedia.stagebook.web.shiro;

import java.util.Arrays;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresGuest;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.authz.annotation.RequiresUser;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.xpoft.vaadin.DiscoveryNavigator;

import com.vaadin.navigator.NavigationStateManager;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewDisplay;
import com.vaadin.ui.ComponentContainer;
import com.vaadin.ui.SingleComponentContainer;
import com.vaadin.ui.UI;

@SuppressWarnings("serial")
public class StageBookNavigator extends DiscoveryNavigator
{

	private static Logger logger = LoggerFactory.getLogger(StageBookNavigator.class);
	
	private VaadinSecurityContext securityCtx;
	private boolean initialized = false;
	
	public StageBookNavigator(UI ui, ComponentContainer container)
	{
		super(ui, container);
	}
	
	public StageBookNavigator(UI ui, ComponentContainer container, VaadinSecurityContext ctx)
	{
		super(ui, container);
		securityCtx = ctx;
		initViews();
	}
	
	public StageBookNavigator(UI ui, SingleComponentContainer container)
    {
        super(ui, container);
    }

    public StageBookNavigator(UI ui, ViewDisplay display)
    {
        super(ui, display);
    }

    public StageBookNavigator(UI ui, NavigationStateManager stateManager, ViewDisplay display)
    {
        super(ui, stateManager, display);
    }
    
     
	public void setSecurityContext(VaadinSecurityContext ctx)
    {
    	this.securityCtx = ctx;
    }
    
	@Override
	protected void initViews()
	{
        if ((securityCtx != null) && !initialized)
        {
        	super.initViews();
        	initialized = true;
        }
	}
	
	public void initAuthenticated()
	{
		initialized = false;
		initViews();
	}
	
    @Override
    protected void addCachedBeans()
    {
        for (ViewCache view : views)
        {
            // Only allowed beans
            if (hasAccess(view.getClazz()))
            {
                logger.debug("addCachedBeans: view name: \"{}\", class: {}, viewCached: {}", new Object[]{view.getName(), view.getClazz(), view.isCached()});
                addBeanView(view.getName(), view.getBeanName(), view.getClazz(), view.isCached());
            }
        }
    }
    
    @Override
    public void addBeanView(String viewName, Class<? extends View> viewClass, boolean cached)
    {
        if (!hasAccess(viewClass))
        {
            return;
        }

        super.addBeanView(viewName, viewClass, cached);
    }
    
   
    
    protected Subject getSubject()
    {
    	return securityCtx.getSubject();
    }
    
    public boolean hasAccess(Class<?> clazz)
    {
    	boolean isAllow = true;

        if (clazz.isAnnotationPresent(RequiresRoles.class))
        {
            isAllow = false;

            RequiresRoles requiresRoles = clazz.getAnnotation(RequiresRoles.class);
            String[] roles = requiresRoles.value();
            Logical logical = requiresRoles.logical();
            if (roles.length > 0)
            {
                Subject subject = getSubject(); //SecurityUtils.getSubject();
                if (!subject.isAuthenticated())
                {
                    return false;
                }

                if (logical == Logical.AND && subject.hasAllRoles(Arrays.asList(roles)))
                {
                    isAllow = true;
                }

                if (logical == Logical.OR)
                {
                    for (boolean hasRole : subject.hasRoles(Arrays.asList(roles)))
                    {
                        if (hasRole)
                        {
                            isAllow = true;
                            break;
                        }
                    }
                }
            }
        }

        if (isAllow && clazz.isAnnotationPresent(RequiresPermissions.class))
        {
            isAllow = false;

            RequiresPermissions requiresPermissions = clazz.getAnnotation(RequiresPermissions.class);
            String[] permissions = requiresPermissions.value();
            Logical logical = requiresPermissions.logical();
            Subject subject = getSubject();

            if (permissions.length > 0)
            {
                if (!subject.isAuthenticated())
                {
                    return false;
                }

                if (logical == Logical.AND && subject.isPermittedAll(permissions))
                {
                    isAllow = true;
                }

                if (logical == Logical.OR && subject.isPermittedAll(permissions))
                {
                    for (boolean isPermitted : subject.isPermitted(permissions))
                    {
                        if (isPermitted)
                        {
                            isAllow = true;
                            break;
                        }
                    }
                }
            }
        }

        if (isAllow && clazz.isAnnotationPresent(RequiresAuthentication.class))
        {
            Subject subject = getSubject();
            isAllow = subject.isAuthenticated();
        }

        if (isAllow && clazz.isAnnotationPresent(RequiresGuest.class))
        {
            Subject subject = getSubject();
            isAllow = subject.getPrincipals() == null;
        }

        if (isAllow && clazz.isAnnotationPresent(RequiresUser.class))
        {
            Subject subject = getSubject();
            isAllow = subject.getPrincipals() != null && !subject.getPrincipals().isEmpty();
        }

        return isAllow;
    }
    
    @Override
    protected void navigateTo(View view, String viewName, String parameters)
    {
    	if (hasAccess(view.getClass()))
    	  super.navigateTo(view, viewName, parameters);
    }

	
    

}

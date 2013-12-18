package com.fettmedia.stagebook.web.event;


public class EntityEditViewOnEnterEvent extends SBEvent
{	
	private String parameters;
	
	public EntityEditViewOnEnterEvent(String params)
	{
		setParameters(params);
	}

	public String getParameters()
	{
		return parameters;
	}

	public void setParameters(String parameters)
	{
		this.parameters = parameters;
	}

}

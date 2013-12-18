package com.fettmedia.stagebook.web.event;

public class ShowViewEvent extends SBEvent
{
	private String viewToShow;
	
	public ShowViewEvent(String viewToShow)
	{
		this.setViewToShow(viewToShow);
	}

	public String getViewToShow()
	{
		return viewToShow;
	}

	public void setViewToShow(String viewToShow)
	{
		this.viewToShow = viewToShow;
	}
	
}

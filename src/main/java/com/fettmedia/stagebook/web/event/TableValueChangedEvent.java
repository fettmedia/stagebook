package com.fettmedia.stagebook.web.event;

public class TableValueChangedEvent extends SBEvent
{
	private Object value;
	
	public TableValueChangedEvent(Object value)
	{
		this.setValue(value);
	}

	public Object getValue()
	{
		return value;
	}

	public void setValue(Object value)
	{
		this.value = value;
	}

}

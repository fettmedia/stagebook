package com.fettmedia.stagebook.web.event;

import org.springframework.stereotype.Component;

import net.engio.mbassy.bus.MBassador;
import net.engio.mbassy.bus.config.BusConfiguration;

@Component
public class EventBusProvider
{
	private static MBassador<SBEvent> eventBus;
	
	public static MBassador<SBEvent> getEventBus()
	{
		if (eventBus == null)
			eventBus = new MBassador<SBEvent>(BusConfiguration.Default());
		return eventBus;
	}
}

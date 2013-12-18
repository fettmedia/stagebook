package com.fettmedia.stagebook.event;

import net.engio.mbassy.listener.Handler;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.springframework.test.context.ContextConfiguration;

import com.fettmedia.stagebook.web.event.EventBusProvider;
import com.fettmedia.stagebook.web.event.SBEvent;

@RunWith(BlockJUnit4ClassRunner.class)
@ContextConfiguration("/spring/applicationContext.xml")
public class EventBusProviderTest
{
	public class TestEvent extends SBEvent
	{
		public String message;
	}
	
	public class TestPublisher
	{
		public TestPublisher()
		{
			
		}
		
		public void send()
		{
			TestEvent e = new TestEvent();
			e.message = "frompub";
			EventBusProvider.getEventBus().publish(e);
		}
	}
	
	public class TestSubscribe
	{
		public String message;
		
		public TestSubscribe()
		{
			EventBusProvider.getEventBus().subscribe(this);
		}
		
		@Handler
		public void handleTest(TestEvent e)
		{
			System.out.println("Handled test event..." + e.message);
			this.message = e.message;
		}
		
	}

	@Test
	public void testGetEventBus()
	{
		Assert.assertNotNull(EventBusProvider.getEventBus());
	}
	
	@Test
	public void testEventBus()
	{
		TestPublisher pub = new TestPublisher();
		TestSubscribe sub = new TestSubscribe();
		pub.send();
		Assert.assertEquals("frompub", sub.message);
		
	}

}

package com.fettmedia.stagebook.domain;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fettmedia.stagebook.domain.service.IBaseService;
import com.fettmedia.stagebook.domain.service.StageBookUserService;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
public class StageBookUserIntegrationTest extends
		EntityIntegrationTest<StageBookUser>
{
	@Autowired
	StageBookUserService service;
	
	@Autowired
	StageBookUserDataOnDemand dod;

	@Override
	protected DataOnDemand<StageBookUser> getDod()
	{
		return dod;
	}

	@Override
	protected IBaseService<StageBookUser> getService()
	{
		return service;
	}

	@Override
	protected String getEntityName()
	{
		return "StageBookUser";
	}

}

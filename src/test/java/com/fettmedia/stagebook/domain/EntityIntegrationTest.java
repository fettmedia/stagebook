package com.fettmedia.stagebook.domain;

import java.util.Iterator;
import java.util.List;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.fettmedia.stagebook.domain.service.IBaseService;

@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
public abstract class EntityIntegrationTest<E extends AbstractEntity<E>>
{
	protected static final String DOD_INIT =  "Data on demand for '%s' failed to initialize correctly";
	
	protected static final String DOD_NO_ENTITY = "Data on demand for '%s' failed to provide a new transient entity";
	protected static final String NOT_TRANSIENT = "Expected '%s' identifier to be null";
	
	protected static final String NO_ENTRIES = "Counter for '%s' incorrectly reported there were no entries";
	
	protected static final String NO_ID = "Data on demand for '%s' failed to provide an identifier";
	protected static final String FIND_FAILED = "Find method for '%s' illegally returned null for id '%d'";
	protected static final String WRONG_ID = "Find method for '%s' returned the incorrect identifier";
	
	protected static final String FINDALL_NULL = "Find all method for '%s' illegally returned null";
	protected static final String FINDALL_NO_ENTRIES = "Find all method for '%s' failed to return any data";
	
	protected static final String FINDENTRIES_NULL = "Find entries method for '%s' illegally returned null";
	protected static final String FINDENTRIES_WRONG_COUNT = "Find entries method for '%s' returned an incorrect number of entries";
	
	protected static final String FLUSH_VER_INC = "Version for '%s' failed to increment on flush directive";
	
	protected static final String MERGED_ID = "Identifier of merged object '%s' not the same as identifier of original object";
	
	protected static final String ID_STILL_NULL = "Expected '%s' identifier to no longer be null";
	
	protected static final String FAILED_DELETE = "Failed to remove '%s' with identifier '%d'";
	
	protected abstract DataOnDemand<E> getDod();
	protected abstract IBaseService<E> getService();
	protected abstract String getEntityName();

	
	@Test
	public void testCount()
	{
		E obj = getDod().getRandomEntity();
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), obj);
		long count = getService().countAll();
		Assert.assertTrue(String.format(NO_ENTRIES, getEntityName()), count > 0);
	}
	
	@Test
	public void testFind()
	{
		E obj = getDod().getRandomEntity();
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), obj);
		Long id = obj.getId();
		Assert.assertNotNull(String.format(NO_ID, getEntityName()), id);
		obj = getService().find(id);
		Assert.assertNotNull(String.format(FIND_FAILED, getEntityName(), id), obj);
		Assert.assertEquals(String.format(WRONG_ID, getEntityName()), id, obj.getId());
	}
	
	@Test
	public void testFindAll()
	{
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), getDod().getRandomEntity());
		long count = getService().countAll();
		//FIXME: Make this work without the ROO automatic integration test annotation!
		Assert.assertTrue(
				"Too expensive to perform a find all test for 'Contact', as there are "
						+ count
						+ " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test",
				count < 250);
		List<E> result = getService().findAll();
		Assert.assertNotNull(String.format(FINDALL_NULL, getEntityName()), result);
		Assert.assertTrue(String.format(FINDALL_NO_ENTRIES, getEntityName()), result.size() > 0);
	}
	
	@Test
	public void testFindEntries()
	{
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), getDod().getRandomEntity());
		long count = getService().countAll();
		if (count > 20)
			count = 20;
		int firstResult = 0;
		int maxResults = (int) count;
		List<E> result = getService().findAll(new PageRequest(firstResult / maxResults, maxResults).getSort());
		Assert.assertNotNull(String.format(FINDENTRIES_NULL, getEntityName()), result);
		Assert.assertEquals(String.format(FINDENTRIES_WRONG_COUNT, getEntityName()), count, result.size());
	}
	
	@Test
	public void testFlush()
	{
		E obj = getDod().getRandomEntity();
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), obj);
		Long id = obj.getId();
		Assert.assertNotNull(String.format(NO_ID, getEntityName()), id);
		obj = getService().find(id);
		Assert.assertNotNull(String.format(FIND_FAILED, getEntityName(), id), obj);
		boolean modified = getDod().modifyEntity(obj);
		Integer currentVersion = obj.getVersion();
		getService().flush();
		Assert.assertTrue(String.format(FLUSH_VER_INC, getEntityName()), 
				(currentVersion != null && obj.getVersion() > currentVersion) || !modified);
	}
	
	@Test
	public void testSaveUpdate()
	{
		E obj = getDod().getRandomEntity();
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), obj);
		Long id = obj.getId();
		Assert.assertNotNull(String.format(NO_ID, getEntityName()), id);
		obj = getService().find(id);
		boolean modified = getDod().modifyEntity(obj);
		Integer currentVersion = obj.getVersion();
		E merged = getService().save(obj);
		getService().flush();
		Assert.assertEquals(String.format(MERGED_ID, getEntityName()), merged.getId(), id);
		Assert.assertTrue(String.format(FLUSH_VER_INC, getEntityName()), 
				(currentVersion != null && obj.getVersion() > currentVersion) || !modified);
	}
	
	@Test
	public void testSave()
	{
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), getDod().getRandomEntity());
		E obj = getDod().getNewTransientEntity(Integer.MAX_VALUE);
		Assert.assertNotNull(String.format(DOD_NO_ENTITY, getEntityName()), obj);
		Assert.assertNull(String.format(NOT_TRANSIENT, getEntityName()), obj.getId());
		try
		{
			getService().save(obj);
		}
		catch (final ConstraintViolationException e)
		{
			final StringBuilder msg = new StringBuilder();
			for (Iterator<ConstraintViolation<?>> iter = e
					.getConstraintViolations().iterator(); iter.hasNext();)
			{
				final ConstraintViolation<?> cv = iter.next();
				msg.append("[").append(cv.getRootBean().getClass().getName())
						.append(".").append(cv.getPropertyPath()).append(": ")
						.append(cv.getMessage()).append(" (invalid value = ")
						.append(cv.getInvalidValue()).append(")").append("]");
			}
			throw new IllegalStateException(msg.toString(), e);
		}
		getService().flush();
		Assert.assertNotNull(String.format(ID_STILL_NULL, getEntityName()), obj.getId());
	}
	
	@Test
	public void testDelete()
	{
		E obj = getDod().getRandomEntity();
		Assert.assertNotNull(String.format(DOD_INIT, getEntityName()), obj);
		Long id = obj.getId();
		Assert.assertNotNull(String.format(NO_ID, getEntityName()), id);
		obj = getService().find(id);
		getService().delete(obj);
		getService().flush();
		Assert.assertNull(String.format(FAILED_DELETE, getEntityName(), id), getService().find(id));
	}


}

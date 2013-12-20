package com.fettmedia.stagebook.domain;

import com.fettmedia.stagebook.domain.service.IBaseService;
import java.util.Iterator;
import java.util.List;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.roo.addon.test.RooIntegrationTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;


@Configurable
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath*:/META-INF/spring/applicationContext*.xml")
@Transactional
public class ContactIntegrationTest
{
	
	@Test
	@Transactional
	public void testAddAndFetch()
	{
	}

	@Autowired
    ContactDataOnDemand dod;

	@Autowired
    IBaseService<Contact> service;

	@Test
    public void testCount() {
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", dod.getRandomContact());
        long count = service.countAll();
        Assert.assertTrue("Counter for 'Contact' incorrectly reported there were no entries", count > 0);
    }

	@Test
    public void testFind() {
        Contact obj = dod.getRandomContact();
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Contact' failed to provide an identifier", id);
        obj = service.find(id);
        Assert.assertNotNull("Find method for 'Contact' illegally returned null for id '" + id + "'", obj);
        Assert.assertEquals("Find method for 'Contact' returned the incorrect identifier", id, obj.getId());
    }

	@Test
    public void testFindAll() {
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", dod.getRandomContact());
        long count = service.countAll();
        Assert.assertTrue("Too expensive to perform a find all test for 'Contact', as there are " + count + " entries; set the findAllMaximum to exceed this value or set findAll=false on the integration test annotation to disable the test", count < 250);
        List<Contact> result = service.findAll();
        Assert.assertNotNull("Find all method for 'Contact' illegally returned null", result);
        Assert.assertTrue("Find all method for 'Contact' failed to return any data", result.size() > 0);
    }

	@Test
    public void testFindEntries() {
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", dod.getRandomContact());
        long count = service.countAll();
        if (count > 20) count = 20;
        int firstResult = 0;
        int maxResults = (int) count;
        List<Contact> result = service.findAll(new org.springframework.data.domain.PageRequest(firstResult / maxResults, maxResults).getSort());
        Assert.assertNotNull("Find entries method for 'Contact' illegally returned null", result);
        Assert.assertEquals("Find entries method for 'Contact' returned an incorrect number of entries", count, result.size());
    }

	@Test
    public void testFlush() {
        Contact obj = dod.getRandomContact();
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Contact' failed to provide an identifier", id);
        obj = service.find(id);
        Assert.assertNotNull("Find method for 'Contact' illegally returned null for id '" + id + "'", obj);
        boolean modified =  dod.modifyContact(obj);
        Integer currentVersion = obj.getVersion();
        service.flush();
        Assert.assertTrue("Version for 'Contact' failed to increment on flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSaveUpdate() {
        Contact obj = dod.getRandomContact();
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Contact' failed to provide an identifier", id);
        obj = service.find(id);
        boolean modified =  dod.modifyContact(obj);
        Integer currentVersion = obj.getVersion();
        Contact merged = (Contact)service.save(obj);
        service.flush();
        Assert.assertEquals("Identifier of merged object not the same as identifier of original object", merged.getId(), id);
        Assert.assertTrue("Version for 'Contact' failed to increment on merge and flush directive", (currentVersion != null && obj.getVersion() > currentVersion) || !modified);
    }

	@Test
    public void testSave() {
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", dod.getRandomContact());
        Contact obj = dod.getNewTransientContact(Integer.MAX_VALUE);
        Assert.assertNotNull("Data on demand for 'Contact' failed to provide a new transient entity", obj);
        Assert.assertNull("Expected 'Contact' identifier to be null", obj.getId());
        try {
            service.save(obj);
        } catch (final ConstraintViolationException e) {
            final StringBuilder msg = new StringBuilder();
            for (Iterator<ConstraintViolation<?>> iter = e.getConstraintViolations().iterator(); iter.hasNext();) {
                final ConstraintViolation<?> cv = iter.next();
                msg.append("[").append(cv.getRootBean().getClass().getName()).append(".").append(cv.getPropertyPath()).append(": ").append(cv.getMessage()).append(" (invalid value = ").append(cv.getInvalidValue()).append(")").append("]");
            }
            throw new IllegalStateException(msg.toString(), e);
        }
        service.flush();
        Assert.assertNotNull("Expected 'Contact' identifier to no longer be null", obj.getId());
    }

	@Test
    public void testDelete() {
        Contact obj = dod.getRandomContact();
        Assert.assertNotNull("Data on demand for 'Contact' failed to initialize correctly", obj);
        Long id = obj.getId();
        Assert.assertNotNull("Data on demand for 'Contact' failed to provide an identifier", id);
        obj = service.find(id);
        service.delete(obj);
        service.flush();
        Assert.assertNull("Failed to remove 'Contact' with identifier '" + id + "'", service.find(id));
    }
}

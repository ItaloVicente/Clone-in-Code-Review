package org.eclipse.jgit.lib;

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Field;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.junit.RepositoryTestCase;
import org.junit.Test;

public class RepositoryTest extends RepositoryTestCase {

	@Test
	public void testIncrementOpen() throws Exception {
		db.incrementOpen();
		assertEquals(2

		db.close();
		assertEquals(1

		db.close();
		assertEquals(0

		db.close();
		assertEquals(0

		db.incrementOpen();
		assertEquals(1
	}

	int getOpenCount(Repository r) throws Exception {
		Field field = Repository.class.getDeclaredField("useCnt");
		field.setAccessible(true);
		AtomicInteger count = (AtomicInteger) field.get(r);
		return count.get();
	}

}

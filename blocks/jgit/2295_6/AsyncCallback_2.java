
package org.eclipse.jgit.storage.dht;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.io.IOException;

import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.dht.spi.memory.MemoryDatabase;
import org.junit.Before;
import org.junit.Test;

public class DhtRepositoryBuilderTest {
	private MemoryDatabase db;

	@Before
	public void setUpDatabase() {
		db = new MemoryDatabase();
	}

	@Test
	public void testCreateAndOpen() throws IOException {
		String name = "test.git";

		DhtRepository repo1 = db.open(name);
		assertSame(db
		assertSame(repo1
		assertSame(repo1
				((DhtObjDatabase) repo1.getObjectDatabase()).getRepository());

		assertEquals(name
		assertNull(repo1.getRepositoryKey());
		assertFalse(repo1.getObjectDatabase().exists());

		repo1.create(true);
		assertNotNull(repo1.getRepositoryKey());
		assertTrue(repo1.getObjectDatabase().exists());

		DhtRepository repo2 = db.open(name);
		assertNotNull(repo2.getRepositoryKey());
		assertTrue(repo2.getObjectDatabase().exists());
		assertEquals(0

		Ref HEAD = repo2.getRef(Constants.HEAD);
		assertTrue(HEAD.isSymbolic());
		assertEquals(Constants.R_HEADS + Constants.MASTER
				HEAD.getLeaf().getName());
	}
}

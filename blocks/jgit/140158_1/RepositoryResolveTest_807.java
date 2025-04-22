
package org.eclipse.jgit.lib;

import static org.hamcrest.CoreMatchers.hasItem;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;

import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.RepositoryCache.FileKey;
import org.junit.Test;

public class RepositoryCacheTest extends RepositoryTestCase {
	@Test
	public void testNonBareFileKey() throws IOException {
		File gitdir = db.getDirectory();
		File parent = gitdir.getParentFile();
		File other = new File(parent
		assertEqualsFile(gitdir
		assertEqualsFile(parent
		assertEqualsFile(other

		assertEqualsFile(gitdir
		assertEqualsFile(gitdir
		assertEqualsFile(other
	}

	@Test
	public void testBareFileKey() throws IOException {
		Repository bare = createBareRepository();
		File gitdir = bare.getDirectory();
		File parent = gitdir.getParentFile();
		String name = gitdir.getName();
		assertTrue(name.endsWith(".git"));
		name = name.substring(0

		assertEqualsFile(gitdir

		assertEqualsFile(gitdir
		assertEqualsFile(gitdir
				FileKey.lenient(new File(parent
	}

	@Test
	public void testFileKeyOpenExisting() throws IOException {
		try (Repository r = new FileKey(db.getDirectory()
				.open(true)) {
			assertNotNull(r);
			assertEqualsFile(db.getDirectory()
		}

		try (Repository r = new FileKey(db.getDirectory()
				.open(false)) {
			assertNotNull(r);
			assertEqualsFile(db.getDirectory()
		}
	}

	@Test
	public void testFileKeyOpenNew() throws IOException {
		File gitdir;
		try (Repository n = createRepository(true)) {
			gitdir = n.getDirectory();
		}
		recursiveDelete(gitdir);
		assertFalse(gitdir.exists());

		try {
			new FileKey(gitdir
			fail("incorrectly opened a non existant repository");
		} catch (RepositoryNotFoundException e) {
			assertEquals("repository not found: " + gitdir.getCanonicalPath()
					e.getMessage());
		}

		final Repository o = new FileKey(gitdir
		assertNotNull(o);
		assertEqualsFile(gitdir
		assertFalse(gitdir.exists());
	}

	@Test
	public void testCacheRegisterOpen() throws Exception {
		final File dir = db.getDirectory();
		RepositoryCache.register(db);
		assertSame(db

		assertEquals(".git"
		final File parent = dir.getParentFile();
		assertSame(db
	}

	@Test
	public void testCacheOpen() throws Exception {
		final FileKey loc = FileKey.exact(db.getDirectory()
		final Repository d2 = RepositoryCache.open(loc);
		assertNotSame(db
		assertSame(d2
		d2.close();
		d2.close();
	}

	@Test
	public void testGetRegisteredWhenEmpty() {
		assertEquals(0
	}

	@Test
	public void testGetRegistered() {
		RepositoryCache.register(db);

		assertThat(RepositoryCache.getRegisteredKeys()
				hasItem(FileKey.exact(db.getDirectory()
		assertEquals(1
	}

	@Test
	public void testUnregister() {
		RepositoryCache.register(db);
		RepositoryCache
				.unregister(FileKey.exact(db.getDirectory()

		assertEquals(0
	}

	@Test
	public void testRepositoryUsageCount() throws Exception {
		FileKey loc = FileKey.exact(db.getDirectory()
		Repository d2 = RepositoryCache.open(loc);
		assertEquals(1
		RepositoryCache.open(FileKey.exact(loc.getFile()
		assertEquals(2
		d2.close();
		assertEquals(1
		d2.close();
		assertEquals(0
	}

	@Test
	public void testRepositoryUsageCountWithRegisteredRepository()
			throws IOException {
		@SuppressWarnings({"resource"
		Repository repo = createRepository(false
		assertEquals(1
		RepositoryCache.register(repo);
		assertEquals(1
		repo.close();
		assertEquals(0
	}

	@Test
	public void testRepositoryNotUnregisteringWhenClosing() throws Exception {
		FileKey loc = FileKey.exact(db.getDirectory()
		Repository d2 = RepositoryCache.open(loc);
		assertEquals(1
		assertThat(RepositoryCache.getRegisteredKeys()
				hasItem(FileKey.exact(db.getDirectory()
		assertEquals(1
		d2.close();
		assertEquals(0
		assertEquals(1
		assertTrue(RepositoryCache.isCached(d2));
	}

	@Test
	public void testRepositoryUnregisteringWhenExpiredAndUsageCountNegative()
			throws Exception {
		Repository repoA = createBareRepository();
		RepositoryCache.register(repoA);

		assertEquals(1
		assertTrue(RepositoryCache.isCached(repoA));

		repoA.close();
		repoA.close();
		repoA.closedAt.set(System.currentTimeMillis() - 65 * 60 * 1000);

		RepositoryCache.clearExpired();

		assertEquals(0
	}

	@Test
	public void testRepositoryUnregisteringWhenExpired() throws Exception {
		@SuppressWarnings({"resource"
		Repository repoA = createRepository(true
		@SuppressWarnings({"resource"
		Repository repoB = createRepository(true
		Repository repoC = createBareRepository();
		RepositoryCache.register(repoA);
		RepositoryCache.register(repoB);
		RepositoryCache.register(repoC);

		assertEquals(3
		assertTrue(RepositoryCache.isCached(repoA));
		assertTrue(RepositoryCache.isCached(repoB));
		assertTrue(RepositoryCache.isCached(repoC));

		repoA.close();
		repoA.closedAt.set(System.currentTimeMillis() - 65 * 60 * 1000);
		repoB.close();

		assertEquals(3
		assertTrue(RepositoryCache.isCached(repoA));
		assertTrue(RepositoryCache.isCached(repoB));
		assertTrue(RepositoryCache.isCached(repoC));

		RepositoryCache.clearExpired();

		assertEquals(2
		assertFalse(RepositoryCache.isCached(repoA));
		assertTrue(RepositoryCache.isCached(repoB));
		assertTrue(RepositoryCache.isCached(repoC));
	}

	@Test
	public void testReconfigure() throws InterruptedException
		@SuppressWarnings({"resource"
		Repository repo = createRepository(false
		RepositoryCache.register(repo);
		assertTrue(RepositoryCache.isCached(repo));
		repo.close();
		assertTrue(RepositoryCache.isCached(repo));

		RepositoryCacheConfig config = new RepositoryCacheConfig();
		config.setExpireAfter(1);
		config.setCleanupDelay(1);
		config.install();

		for (int i = 0; i <= 10; i++) {
			Thread.sleep(1 << i);
			if (!RepositoryCache.isCached(repo)) {
				return;
			}
		}
		fail("Repository should have been evicted from cache");
	}
}

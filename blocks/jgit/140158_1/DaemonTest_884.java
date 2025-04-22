
package org.eclipse.jgit.transport;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.lib.Constants.OBJ_BLOB;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Set;

import org.eclipse.jgit.errors.MissingBundlePrerequisiteException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.ObjectReader;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.test.resources.SampleDataRepositoryTestCase;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class BundleWriterTest extends SampleDataRepositoryTestCase {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testEmptyBundleFails() throws Exception {
		Repository newRepo = createBareRepository();
		thrown.expect(TransportException.class);
		fetchFromBundle(newRepo
	}

	@Test
	public void testNonBundleFails() throws Exception {
		Repository newRepo = createBareRepository();
		thrown.expect(TransportException.class);
		fetchFromBundle(newRepo
	}

	@Test
	public void testGarbageBundleFails() throws Exception {
		Repository newRepo = createBareRepository();
		thrown.expect(TransportException.class);
		fetchFromBundle(newRepo
				(TransportBundle.V2_BUNDLE_SIGNATURE + '\n' + "Garbage")
						.getBytes(UTF_8));
	}

	@Test
	public void testWriteSingleRef() throws Exception {
		final byte[] bundle = makeBundle("refs/heads/firstcommit"
				"42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"

		Repository newRepo = createBareRepository();
		FetchResult fetchResult = fetchFromBundle(newRepo
		Ref advertisedRef = fetchResult
				.getAdvertisedRef("refs/heads/firstcommit");

		assertEquals("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"
				.getObjectId().name());
		assertEquals("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"
				.resolve("refs/heads/firstcommit").name());
	}

	@Test
	public void testWriteHEAD() throws Exception {
		byte[] bundle = makeBundle("HEAD"
				"42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"

		Repository newRepo = createBareRepository();
		FetchResult fetchResult = fetchFromBundle(newRepo
		Ref advertisedRef = fetchResult.getAdvertisedRef("HEAD");

		assertEquals("42e4e7c5e507e113ebbb7801b16b52cf867b7ce1"
				.getObjectId().name());
	}

	@Test
	public void testIncrementalBundle() throws Exception {
		byte[] bundle;

		bundle = makeBundle("refs/heads/aa"

		Repository newRepo = createBareRepository();
		FetchResult fetchResult = fetchFromBundle(newRepo
		Ref advertisedRef = fetchResult.getAdvertisedRef("refs/heads/aa");

		assertEquals(db.resolve("a").name()
		assertEquals(db.resolve("a").name()
				.name());
		assertNull(newRepo.resolve("refs/heads/a"));

		try (RevWalk rw = new RevWalk(db)) {
			bundle = makeBundle("refs/heads/cc"
					rw.parseCommit(db.resolve("a").toObjectId()));
			fetchResult = fetchFromBundle(newRepo
			advertisedRef = fetchResult.getAdvertisedRef("refs/heads/cc");
			assertEquals(db.resolve("c").name()
			assertEquals(db.resolve("c").name()
					.name());
			assertNull(newRepo.resolve("refs/heads/c"));

			try {
				Repository newRepo2 = createBareRepository();
				fetchResult = fetchFromBundle(newRepo2
				fail("We should not be able to fetch from bundle with prerequisites that are not fulfilled");
			} catch (MissingBundlePrerequisiteException e) {
				assertTrue(e.getMessage()
						.indexOf(db.resolve("refs/heads/a").name()) >= 0);
			}
		}
	}

	@Test
	public void testAbortWrite() throws Exception {
		boolean caught = false;
		try {
			makeBundleWithCallback(
					"refs/heads/aa"
		} catch (WriteAbortedException e) {
			caught = true;
		}
		assertTrue(caught);
	}

	@Test
	public void testCustomObjectReader() throws Exception {
		String refName = "refs/heads/blob";
		String data = "unflushed data";
		ObjectId id;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		try (Repository repo = new InMemoryRepository(
					new DfsRepositoryDescription("repo"));
				ObjectInserter ins = repo.newObjectInserter();
				ObjectReader or = ins.newReader()) {
			id = ins.insert(OBJ_BLOB
			BundleWriter bw = new BundleWriter(or);
			bw.include(refName
			bw.writeBundle(NullProgressMonitor.INSTANCE
			assertNull(repo.exactRef(refName));
			try {
				repo.open(id
				fail("We should not be able to open the unflushed blob");
			} catch (MissingObjectException e) {
			}
		}

		try (Repository repo = new InMemoryRepository(
					new DfsRepositoryDescription("copy"))) {
			fetchFromBundle(repo
			Ref ref = repo.exactRef(refName);
			assertNotNull(ref);
			assertEquals(id
			assertEquals(data
					new String(repo.open(id
		}
	}

	private static FetchResult fetchFromBundle(final Repository newRepo
			final byte[] bundle) throws URISyntaxException
			NotSupportedException
		final ByteArrayInputStream in = new ByteArrayInputStream(bundle);
		final Set<RefSpec> refs = Collections.singleton(rs);
		try (TransportBundleStream transport = new TransportBundleStream(
				newRepo
			return transport.fetch(NullProgressMonitor.INSTANCE
		}
	}

	private byte[] makeBundle(final String name
			final String anObjectToInclude
			throws FileNotFoundException
		return makeBundleWithCallback(name
	}

	private byte[] makeBundleWithCallback(final String name
			final String anObjectToInclude
			boolean value)
			throws FileNotFoundException
		final BundleWriter bw;

		bw = new BundleWriter(db);
		bw.setObjectCountCallback(new NaiveObjectCountCallback(value));
		bw.include(name
		if (assume != null)
			bw.assume(assume);
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		bw.writeBundle(NullProgressMonitor.INSTANCE
		return out.toByteArray();
	}

	private static class NaiveObjectCountCallback
			implements ObjectCountCallback {
		private final boolean value;

		NaiveObjectCountCallback(boolean value) {
			this.value = value;
		}

		@Override
		public void setObjectCount(long unused) throws WriteAbortedException {
			if (!value)
				throw new WriteAbortedException();
		}
	}

}

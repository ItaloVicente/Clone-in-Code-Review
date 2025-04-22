package org.eclipse.jgit.transport;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.storage.dfs.DfsGarbageCollector;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UploadPack.RequestPolicy;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.hamcrest.Matchers;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class UploadPackReachabilityTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private URIish uri;

	private TestProtocol<Object> testProtocol;

	private Object ctx = new Object();

	private InMemoryRepository server;

	private InMemoryRepository client;

	private TestRepository<InMemoryRepository> remote;

	@Before
	public void setUp() throws Exception {
		server = newRepo("server");
		client = newRepo("client");

		remote = new TestRepository<>(server);
	}

	@After
	public void tearDown() {
		Transport.unregister(testProtocol);
	}

	@Test
	public void testFetchUnreachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		remote.commit(remote.tree(remote.file("foo"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

	@Test
	public void testFetchUnreachableBlobWithoutBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		remote.commit(remote.tree(remote.file("foo"

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers
					.containsString("want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

	@Test
	public void testFetchReachableBlobWithBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
			assertTrue(client.getObjectDatabase().has(blob.toObjectId()));
		}
	}

	@Test
	public void testFetchReachableBlobWithoutBitmap() throws Exception {
		RevBlob blob = remote.blob("foo");
		RevCommit commit = remote.commit(remote.tree(remote.file("foo"
		remote.update("master"

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(blob.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
						"want " + blob.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(blob.name())));
		}
	}

	@Test
	public void testFetchReachableCommitWithBitmap() throws Exception {
		RevCommit commit = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("master"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(commit.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertTrue(client.getObjectDatabase().has(commit.toObjectId()));
		}
	}

	@Test
	public void testFetchReachableCommitWithoutBitmap() throws Exception {
		RevCommit commit = remote
				.commit(remote.tree(remote.file("foo"
		remote.update("master"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(commit.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
			assertTrue(client.getObjectDatabase().has(commit.toObjectId()));
		}
	}

	@Test
	public void testFetchUnreachableCommitWithBitmap() throws Exception {
		RevCommit commit = remote
				.commit(remote.tree(remote.file("foo"
		generateBitmaps(server);

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(commit.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers
					.containsString("want " + commit.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
		}
	}

	@Test
	public void testFetchUnreachableCommitWithoutBitmap() throws Exception {
		RevCommit commit = remote
				.commit(remote.tree(remote.file("foo"

		testProtocol = generateReachableCommitUploadPackProtocol();
		uri = testProtocol.register(ctx

		assertFalse(client.getObjectDatabase().has(commit.toObjectId()));

		try (Transport tn = testProtocol.open(uri
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers
					.containsString("want " + commit.name() + " not valid"));
			tn.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(commit.name())));
		}
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	private void generateBitmaps(InMemoryRepository repo) throws Exception {
		new DfsGarbageCollector(repo).pack(null);
		repo.scanForRepoChanges();
	}

	private static TestProtocol<Object> generateReachableCommitUploadPackProtocol() {
		return new TestProtocol<>(new UploadPackFactory<Object>() {
			@Override
			public UploadPack create(Object req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				UploadPack up = new UploadPack(db);
				up.setRequestPolicy(RequestPolicy.REACHABLE_COMMIT);
				return up;
			}
		}
	}
}

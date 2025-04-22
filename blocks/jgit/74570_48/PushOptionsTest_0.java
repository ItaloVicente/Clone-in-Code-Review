
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushOptionsTest extends RepositoryTestCase {
	private URIish uri;
	private TestProtocol<Object> testProtocol;
	private Object ctx = new Object();
	private InMemoryRepository server;
	private InMemoryRepository client;
	private ObjectId obj1;
	private ObjectId obj2;
	private BaseReceivePack baseReceivePack;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		server = newRepo("server");
		client = newRepo("client");

		testProtocol = new TestProtocol<>(null
				new ReceivePackFactory<Object>() {
					@Override
					public ReceivePack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						ReceivePack receivePack = new ReceivePack(database);
						receivePack.setAllowPushOptions(true);
						receivePack.setAtomic(true);
						baseReceivePack = receivePack;
						return receivePack;
					}
				});

		uri = testProtocol.register(ctx

		try (ObjectInserter ins = client.newObjectInserter()) {
			obj1 = ins.insert(Constants.OBJ_BLOB
			obj2 = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}
	}

	@After
	public void tearDown() {
		baseReceivePack = null;
		Transport.unregister(testProtocol);
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	private List<RemoteRefUpdate> commands(boolean atomicSafe)
			throws IOException {
		List<RemoteRefUpdate> cmds = new ArrayList<>();
		cmds.add(new RemoteRefUpdate(null
				ObjectId.zeroId()));
		cmds.add(new RemoteRefUpdate(null
				atomicSafe ? ObjectId.zeroId() : obj1));
		return cmds;
	}

	private void connectLocalToRemote(Git local
			throws URISyntaxException
		StoredConfig config = local.getRepository().getConfig();
		RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addURI(new URIish(
				remote.getRepository().getDirectory().toURI().toURL()));
		remoteConfig.addFetchRefSpec(
		remoteConfig.update(config);
		config.save();
	}

	private RevCommit addCommit(Git git)
			throws IOException
		writeTrashFile("f"
		git.add().addFilepattern("f").call();
		return git.commit().setMessage("adding f").call();
	}

	@Test
	public void testNonAtomicPushWithOptions() throws Exception {
		PushResult r;
		server.setPerformsAtomicTransactions(false);
		List<String> pushOptions = Arrays.asList("Hello"

		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(false);
			tn.setPushOptions(pushOptions);

			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");

		assertSame(RemoteRefUpdate.Status.OK
		assertSame(RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED
				two.getStatus());
		assertEquals(pushOptions
	}

	@Test
	public void testAtomicPushWithOptions() throws Exception {
		PushResult r;
		server.setPerformsAtomicTransactions(true);
		List<String> pushOptions = Arrays.asList("Hello"

		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(true);
			tn.setPushOptions(pushOptions);

			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");

		assertSame(RemoteRefUpdate.Status.OK
		assertSame(RemoteRefUpdate.Status.OK
		assertEquals(pushOptions
	}

	@Test
	public void testFailedAtomicPushWithOptions() throws Exception {
		PushResult r;
		server.setPerformsAtomicTransactions(true);
		List<String> pushOptions = Arrays.asList("Hello"

		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(true);
			tn.setPushOptions(pushOptions);

			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");

		assertSame(RemoteRefUpdate.Status.REJECTED_OTHER_REASON
				one.getStatus());
		assertSame(RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED
				two.getStatus());
		assertEquals(new ArrayList<String>()
	}

	@Test
	public void testThinPushWithOptions() throws Exception {
		PushResult r;
		List<String> pushOptions = Arrays.asList("Hello"

		try (Transport tn = testProtocol.open(uri
			tn.setPushThin(true);
			tn.setPushOptions(pushOptions);

			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");

		assertSame(RemoteRefUpdate.Status.OK
		assertSame(RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED
				two.getStatus());
		assertEquals(pushOptions
	}

	@Test
	public void testPushWithoutOptions() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			connectLocalToRemote(git

			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive"
			config2.save();

			RevCommit commit = addCommit(git);

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertNull(git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));

			PushCommand pushCommand = git.push().setRemote("test");
			pushCommand.call();

			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));
		}
	}

	@Test
	public void testPushWithEmptyOptions() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			connectLocalToRemote(git

			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive"
			config2.save();

			RevCommit commit = addCommit(git);

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();
			assertNull(git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));

			List<String> pushOptions = new ArrayList<>();
			PushCommand pushCommand = git.push().setRemote("test")
					.setPushOptions(pushOptions);
			pushCommand.call();

			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));
		}
	}

	@Test
	public void testAdvertisedButUnusedPushOptions() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			connectLocalToRemote(git

			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive"
			config2.save();

			RevCommit commit = addCommit(git);

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertNull(git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));

			PushCommand pushCommand = git.push().setRemote("test")
					.setPushOptions(null);
			pushCommand.call();

			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));
		}
	}

	@Test(expected = TransportException.class)
	public void testPushOptionsNotSupported() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			connectLocalToRemote(git

			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive"
			config2.save();

			addCommit(git);

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertNull(git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(git2.getRepository().resolve("refs/heads/not-pushed"));
			assertNull(git2.getRepository().resolve("refs/heads/master"));

			List<String> pushOptions = new ArrayList<>();
			PushCommand pushCommand = git.push().setRemote("test")
					.setPushOptions(pushOptions);
			pushCommand.call();

			fail("should already have thrown TransportException");
		}
	}
}

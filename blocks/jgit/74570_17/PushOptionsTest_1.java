
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.RemoteRefUpdate.Status.REJECTED_OTHER_REASON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

	private URIish uri;
	private TestProtocol<Object> testProtocol;
	private Object ctx = new Object();
	private InMemoryRepository server;
	private InMemoryRepository client;
	private ObjectId obj1;
	private ObjectId obj2;
	private ObjectId obj3;
	private String refName = "refs/tags/blob";

	private static final NullProgressMonitor PM = NullProgressMonitor.INSTANCE;
	private static final String R_MASTER = Constants.R_HEADS + Constants.MASTER;
	private static final String R_PRIVATE = Constants.R_HEADS + "private";
	private Repository src;
	private Repository dst;
	private RevCommit A
	private RevBlob a

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
						return new ReceivePack(database);
					}
				});
		uri = testProtocol.register(ctx

		try (ObjectInserter ins = server.newObjectInserter()) {
			obj1 = ins.insert(Constants.OBJ_BLOB
			obj3 = ins.insert(Constants.OBJ_BLOB
			ins.flush();

			RefUpdate u = server.updateRef(refName);
			u.setNewObjectId(obj1);
			assertEquals(RefUpdate.Result.NEW
		}

		try (ObjectInserter ins = client.newObjectInserter()) {
			obj2 = ins.insert(Constants.OBJ_BLOB
			ins.flush();
		}


		src = createBareRepository();
		dst = createBareRepository();

		TestRepository<Repository> d = new TestRepository<Repository>(dst);
		a = d.blob("a");
		A = d.commit(d.tree(d.file("a"
		B = d.commit().parent(A).create();
		d.update(R_MASTER

		try (Transport t = Transport.open(src
				new URIish(dst.getDirectory().getAbsolutePath()))) {
			t.fetch(PM
			assertEquals(B
		}

		b = d.blob("b");
		P = d.commit(d.tree(d.file("b"
		d.update(R_PRIVATE
	}

	@After
	public void tearDown() {
		Transport.unregister(testProtocol);
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	@Test
	public void testPushWithoutOptions() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			remoteConfig.addURI(new URIish(
					git2.getRepository().getDirectory().toURI().toURL()));
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.save();

			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive"
			config2.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			assertEquals(null
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));

			PushCommand pushCommand = git.push().setRemote("test");
			pushCommand.call();

			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/not-pushed"));
			assertEquals(null
					git2.getRepository().resolve("refs/heads/master"));
		}
	}

	@Test
	public void testPushWithOptions() throws Exception {
		try (Git git = new Git(db);
				Git git2 = new Git(createBareRepository())) {
			final StoredConfig config = git.getRepository().getConfig();
			RemoteConfig remoteConfig = new RemoteConfig(config
			remoteConfig.addURI(new URIish(
					git2.getRepository().getDirectory().toURI().toURL()));
			remoteConfig.addFetchRefSpec(
			remoteConfig.update(config);
			config.save();

			ReceivePack receivePack = new ReceivePack(git2.getRepository());
			final StoredConfig config2 = git2.getRepository().getConfig();
			config2.setBoolean("receive"
			config2.save();

			writeTrashFile("f"
			git.add().addFilepattern("f").call();
			RevCommit commit = git.commit().setMessage("adding f").call();

			git.checkout().setName("not-pushed").setCreateBranch(true).call();
			git.checkout().setName("branchtopush").setCreateBranch(true).call();

			List<String> pushOptions = Arrays.asList("Hello"
			PushCommand pushCommand = git.push().setRemote("test")
					.setPushOptions(pushOptions);
			pushCommand.call();
			assertEquals(commit.getId()
					git2.getRepository().resolve("refs/heads/branchtopush"));
			assertNull(receivePack.getPushOptions());
		}
	}

	public static class NullOutputStream extends OutputStream {
		@Override
		public void write(int b) throws IOException {
		}
	}

	public void testWrongOldIdDoesNotReplace() throws IOException {
		RemoteRefUpdate rru = new RemoteRefUpdate(null
				false

		Map<String
		updates.put(rru.getRemoteName()

		Transport tn = testProtocol.open(uri
		try {
			PushConnection connection = tn.openPush();
			try {
				connection.push(NullProgressMonitor.INSTANCE
			} finally {
				connection.close();
			}
		} finally {
			tn.close();
		}

		assertEquals(REJECTED_OTHER_REASON
		assertEquals("invalid old id sent"
	}

	public static PacketLineIn newPacketLineIn(String input) {
		return new PacketLineIn(
				new ByteArrayInputStream(Constants.encode(input)));
	}
}

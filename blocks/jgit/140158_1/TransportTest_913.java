
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.pack.PackStatistics;
import org.eclipse.jgit.transport.BasePackFetchConnection.FetchConfig;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestProtocolTest {

	private static final RefSpec MASTER = new RefSpec(
			"+refs/heads/master:refs/heads/master");

	private static final int HAVES_PER_ROUND = 32;
	private static final int MAX_HAVES = 256;

	private static class User {
		private final String name;

		private User(String name) {
			this.name = name;
		}
	}

	private static class DefaultUpload implements UploadPackFactory<User> {
		@Override
		public UploadPack create(User req
			UploadPack up = new UploadPack(db);
			up.setPostUploadHook(new PostUploadHook() {
				@Override
				public void onPostUpload(PackStatistics stats) {
					havesCount = stats.getHaves();
				}
			});
			return up;
		}
	}

	private static class DefaultReceive implements ReceivePackFactory<User> {
		@Override
		public ReceivePack create(User req
			return new ReceivePack(db);
		}
	}

	private static long havesCount;

	private List<TransportProtocol> protos;
	private TestRepository<InMemoryRepository> local;
	private TestRepository<InMemoryRepository> remote;

  @Before
	public void setUp() throws Exception {
		protos = new ArrayList<>();
		local = new TestRepository<>(
				new InMemoryRepository(new DfsRepositoryDescription("local")));
		remote = new TestRepository<>(
				new InMemoryRepository(new DfsRepositoryDescription("remote")));
  }

	@After
	public void tearDown() {
		for (TransportProtocol proto : protos) {
			Transport.unregister(proto);
		}
	}

	@Test
	public void testFetch() throws Exception {
		ObjectId master = remote.branch("master").commit().create();

		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		try (Git git = new Git(local.getRepository())) {
			git.fetch()
					.setRemote(uri.toString())
					.setRefSpecs(HEADS)
					.call();
			assertEquals(master
					local.getRepository().exactRef("refs/heads/master").getObjectId());
		}
	}

	@Test
	public void testPush() throws Exception {
		ObjectId master = local.branch("master").commit().create();

		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		try (Git git = new Git(local.getRepository())) {
			git.push()
					.setRemote(uri.toString())
					.setRefSpecs(HEADS)
					.call();
			assertEquals(master
					remote.getRepository().exactRef("refs/heads/master").getObjectId());
		}
	}

	@Test
	public void testFullNegotiation() throws Exception {
		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		for (int i = 0; i < 10 * HAVES_PER_ROUND; i++) {
			local.branch("local-branch-" + i).commit().create();
		}
		remote.tick(11 * HAVES_PER_ROUND);
		RevCommit master = remote.branch("master").commit()
				.add("readme.txt"

		try (Git git = new Git(local.getRepository())) {
			assertNull(local.getRepository().exactRef("refs/heads/master"));
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
			assertEquals(10 * HAVES_PER_ROUND
		}
	}

	@Test
	public void testMaxHaves() throws Exception {
		TestProtocol<User> proto = registerDefault();
		URIish uri = proto.register(new User("user")

		for (int i = 0; i < 10 * HAVES_PER_ROUND; i++) {
			local.branch("local-branch-" + i).commit().create();
		}
		remote.tick(11 * HAVES_PER_ROUND);
		RevCommit master = remote.branch("master").commit()
				.add("readme.txt"

		TestProtocol.setFetchConfig(new FetchConfig(true
		try (Git git = new Git(local.getRepository())) {
			assertNull(local.getRepository().exactRef("refs/heads/master"));
			git.fetch().setRemote(uri.toString()).setRefSpecs(MASTER).call();
			assertEquals(master
					.exactRef("refs/heads/master").getObjectId());
			assertTrue(havesCount <= MAX_HAVES);
		}
	}

	@Test
	public void testUploadPackFactory() throws Exception {
		ObjectId master = remote.branch("master").commit().create();

		final AtomicInteger rejected = new AtomicInteger();
		TestProtocol<User> proto = registerProto(new UploadPackFactory<User>() {
			@Override
			public UploadPack create(User req
					throws ServiceNotAuthorizedException {
				if (!"user2".equals(req.name)) {
					rejected.incrementAndGet();
					throw new ServiceNotAuthorizedException();
				}
				return new UploadPack(db);
			}
		}

		URIish user1Uri = proto.register(new User("user1")
		URIish user2Uri = proto.register(new User("user2")

		try (Git git = new Git(local.getRepository())) {
			try {
				git.fetch()
						.setRemote(user1Uri.toString())
						.setRefSpecs(MASTER)
						.call();
				fail("accepted not permitted fetch");
			} catch (InvalidRemoteException expected) {
			}
			assertEquals(1
			assertNull(local.getRepository().exactRef("refs/heads/master"));

			git.fetch()
					.setRemote(user2Uri.toString())
					.setRefSpecs(MASTER)
					.call();
			assertEquals(1
			assertEquals(master
					local.getRepository().exactRef("refs/heads/master").getObjectId());
		}
	}

	@Test
	public void testReceivePackFactory() throws Exception {
		ObjectId master = local.branch("master").commit().create();

		final AtomicInteger rejected = new AtomicInteger();
		TestProtocol<User> proto = registerProto(new DefaultUpload()
				new ReceivePackFactory<User>() {
					@Override
					public ReceivePack create(User req
							throws ServiceNotAuthorizedException {
						if (!"user2".equals(req.name)) {
							rejected.incrementAndGet();
							throw new ServiceNotAuthorizedException();
						}
						return new ReceivePack(db);
					}
				});

		URIish user1Uri = proto.register(new User("user1")
		URIish user2Uri = proto.register(new User("user2")

		try (Git git = new Git(local.getRepository())) {
			try {
				git.push()
						.setRemote(user1Uri.toString())
						.setRefSpecs(HEADS)
						.call();
				fail("accepted not permitted push");
			} catch (TransportException expected) {
				assertTrue(expected.getMessage().contains(
						JGitText.get().pushNotPermitted));
			}
			assertEquals(1
			assertNull(remote.getRepository().exactRef("refs/heads/master"));

			git.push()
					.setRemote(user2Uri.toString())
					.setRefSpecs(HEADS)
					.call();
			assertEquals(1
			assertEquals(master
					remote.getRepository().exactRef("refs/heads/master").getObjectId());
		}
	}

	private TestProtocol<User> registerDefault() {
		return registerProto(new DefaultUpload()
	}

	private TestProtocol<User> registerProto(UploadPackFactory<User> upf
			ReceivePackFactory<User> rpf) {
		TestProtocol<User> proto = new TestProtocol<>(upf
		protos.add(proto);
		Transport.register(proto);
		return proto;
	}
}

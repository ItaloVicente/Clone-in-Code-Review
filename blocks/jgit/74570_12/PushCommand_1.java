
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.RemoteRefUpdate.Status.REJECTED_OTHER_REASON;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.PushCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.junit.RepositoryTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
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

	private ObjectId obj3;
	private String refName = "refs/tags/blob";

	@Before
	public void setUp() throws Exception {
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
	}

	@After
	public void tearDown() {
		Transport.unregister(testProtocol);
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	@Test
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

	@Test
	public void testPush() throws JGitInternalException
			GitAPIException

		Repository db2 = createWorkRepository();

		final StoredConfig config = db.getConfig();
		config.setBoolean("receive"
		RemoteConfig remoteConfig = new RemoteConfig(config
		remoteConfig.addURI(new URIish(db2.getDirectory().toURI().toURL()));
		remoteConfig.update(config);
		config.save();


		try (Git git1 = new Git(db)) {
			RevCommit commit = git1.commit().setMessage("initial commit")
					.call();
			Ref tagRef = git1.tag().setName("tag").call();

			try {
				db2.resolve(commit.getId().getName() + "^{commit}");
				fail("id shouldn't exist yet");
			} catch (MissingObjectException e) {
			}

			RefSpec spec = new RefSpec("refs/heads/master:refs/heads/x");
			List<String> pushOptions = Arrays.asList("Hello"
			BaseReceivePack receivePack = new ReceivePack(db2);
			PushCommand pushCommand = git1.push().setRemote("test")
					.setRefSpecs(spec).setPushOptions(pushOptions);
			pushCommand.call();

			assertEquals(commit.getId()
					db2.resolve(commit.getId().getName() + "^{commit}"));
			assertEquals(tagRef.getObjectId()
					db2.resolve(tagRef.getObjectId().getName()));

			assertEquals(receivePack.getPushOptions()
		}
	}
}

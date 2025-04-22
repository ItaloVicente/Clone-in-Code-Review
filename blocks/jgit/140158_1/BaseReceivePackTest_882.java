
package org.eclipse.jgit.transport;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AtomicPushTest {
	private URIish uri;
	private TestProtocol<Object> testProtocol;
	private Object ctx = new Object();
	private InMemoryRepository server;
	private InMemoryRepository client;
	private ObjectId obj1;
	private ObjectId obj2;

	@Before
	public void setUp() throws Exception {
		server = newRepo("server");
		client = newRepo("client");
		testProtocol = new TestProtocol<>(
				null
				new ReceivePackFactory<Object>() {
					@Override
					public ReceivePack create(Object req
							throws ServiceNotEnabledException
							ServiceNotAuthorizedException {
						return new ReceivePack(db);
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
		Transport.unregister(testProtocol);
	}

	private static InMemoryRepository newRepo(String name) {
		return new InMemoryRepository(new DfsRepositoryDescription(name));
	}

	@Test
	public void pushNonAtomic() throws Exception {
		PushResult r;
		server.setPerformsAtomicTransactions(false);
		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(false);
			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");
		assertSame(RemoteRefUpdate.Status.OK
		assertSame(
				RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED
				two.getStatus());
	}

	@Test
	public void pushAtomicClientGivesUpEarly() throws Exception {
		PushResult r;
		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(true);
			r = tn.push(NullProgressMonitor.INSTANCE
		}

		RemoteRefUpdate one = r.getRemoteUpdate("refs/heads/one");
		RemoteRefUpdate two = r.getRemoteUpdate("refs/heads/two");
		assertSame(
				RemoteRefUpdate.Status.REJECTED_OTHER_REASON
				one.getStatus());
		assertSame(
				RemoteRefUpdate.Status.REJECTED_REMOTE_CHANGED
				two.getStatus());
		assertEquals(JGitText.get().transactionAborted
	}

	@Test
	public void pushAtomicDisabled() throws Exception {
		List<RemoteRefUpdate> cmds = new ArrayList<>();
		cmds.add(new RemoteRefUpdate(
				null
				obj1
				ObjectId.zeroId()));
		cmds.add(new RemoteRefUpdate(
				null
				obj2
				ObjectId.zeroId()));

		server.setPerformsAtomicTransactions(false);
		try (Transport tn = testProtocol.open(uri
			tn.setPushAtomic(true);
			tn.push(NullProgressMonitor.INSTANCE
			fail("did not throw TransportException");
		} catch (TransportException e) {
			assertEquals(
					uri + ": " + JGitText.get().atomicPushNotSupported
					e.getMessage());
		}
	}

	private List<RemoteRefUpdate> commands() throws IOException {
		List<RemoteRefUpdate> cmds = new ArrayList<>();
		cmds.add(new RemoteRefUpdate(
				null
				obj1
				ObjectId.zeroId()));
		cmds.add(new RemoteRefUpdate(
				null
				obj2
				obj1));
		return cmds;
	}
}

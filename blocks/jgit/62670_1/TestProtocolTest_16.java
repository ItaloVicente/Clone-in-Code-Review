
package org.eclipse.jgit.transport;

import static org.eclipse.jgit.transport.RemoteRefUpdate.Status.REJECTED_OTHER_REASON;
import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.internal.storage.dfs.InMemoryRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.transport.resolver.ReceivePackFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class PushConnectionTest {
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
}


package org.eclipse.jgit.http.test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.DefaultReceivePackFactory;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.test.util.AccessEvent;
import org.eclipse.jgit.http.test.util.HttpTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.PushResult;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;

public class HookMessageTest extends HttpTestCase {
	private Repository remoteRepository;

	private URIish remoteURI;

	protected void setUp() throws Exception {
		super.setUp();

		final TestRepository src = createTestRepository();
		final String srcName = src.getRepository().getDirectory().getName();

		ServletContextHandler app = server.addContext("/git");
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver() {
			public Repository open(HttpServletRequest req
					throws RepositoryNotFoundException
					ServiceNotEnabledException {
				if (!name.equals(srcName))
					throw new RepositoryNotFoundException(name);

				final Repository db = src.getRepository();
				db.incrementOpen();
				return db;
			}
		});
		gs.setReceivePackFactory(new DefaultReceivePackFactory() {
			public ReceivePack create(HttpServletRequest req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack recv = super.create(req
				recv.setPreReceiveHook(new PreReceiveHook() {
					public void onPreReceive(ReceivePack rp
							Collection<ReceiveCommand> commands) {
						rp.sendMessage("message line 1");
						rp.sendError("no soup for you!");
						rp.sendMessage("come back next year!");
					}
				});
				return recv;
			}

		});
		app.addServlet(new ServletHolder(gs)

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app

		RepositoryConfig cfg = remoteRepository.getConfig();
		cfg.setBoolean("http"
		cfg.save();
	}

	public void testPush_CreateBranch() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
		Transport t;
		PushResult result;

		t = Transport.open(db
		try {
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate update = new RemoteRefUpdate(src.getRepository()
					srcExpr
			result = t.push(NullProgressMonitor.INSTANCE
					.singleton(update));
		} finally {
			t.close();
		}

		assertTrue(remoteRepository.hasObject(Q_txt));
		assertNotNull("has " + dstName
		assertEquals(Q
		fsck(remoteRepository

		List<AccessEvent> requests = getRequests();
		assertEquals(2

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(200

				+ "come back next year!\n"
				result.getMessages());
	}
}

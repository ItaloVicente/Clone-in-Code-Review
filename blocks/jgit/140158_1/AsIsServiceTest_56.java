
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RemoteRepositoryException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.DefaultReceivePackFactory;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.Before;
import org.junit.Test;

public class AdvertiseErrorTest extends HttpTestCase {
	private Repository remoteRepository;

	private URIish remoteURI;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final TestRepository<Repository> src = createTestRepository();
		final String srcName = src.getRepository().getDirectory().getName();

		ServletContextHandler app = server.addContext("/git");
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
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
			@Override
			public ReceivePack create(HttpServletRequest req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack rp = super.create(req
				rp.sendError("message line 1");
				rp.sendError("no soup for you!");
				rp.sendError("come back next year!");
				return rp;
			}

		});
		app.addServlet(new ServletHolder(gs)

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app

		StoredConfig cfg = remoteRepository.getConfig();
		cfg.setBoolean("http"
		cfg.save();
	}

	@Test
	public void testPush_CreateBranch() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
		try (Transport t = Transport.open(db
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate update = new RemoteRefUpdate(src.getRepository()
					srcExpr
			try {
				t.push(NullProgressMonitor.INSTANCE
						.singleton(update));
				fail("push completed without throwing exception");
			} catch (RemoteRepositoryException error) {
						+ "come back next year!"
						error.getMessage());
			}
		}
	}
}

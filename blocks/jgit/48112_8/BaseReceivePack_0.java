package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.errors.TooLargePackException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.DefaultReceivePackFactory;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectChecker;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.PostReceiveHook;
import org.eclipse.jgit.transport.PreReceiveHook;
import org.eclipse.jgit.transport.ReceiveCommand;
import org.eclipse.jgit.transport.ReceivePack;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.Before;
import org.junit.Test;

public class GitServletResponseTests extends HttpTestCase {
	private Repository srvRepo;

	private URIish srvURI;

	private GitServlet gs;


	private PostReceiveHook postHook = null;

	private PreReceiveHook preHook = null;

	private ObjectChecker oc = null;

	@Before
	public void setUp() throws Exception {
		super.setUp();

		final TestRepository<Repository> srv = createTestRepository();
		final String repoName = srv.getRepository().getDirectory().getName();

		ServletContextHandler app = server.addContext("/git");
		gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			public Repository open(HttpServletRequest req
					throws RepositoryNotFoundException
					ServiceNotEnabledException {
				if (!name.equals(repoName))
					throw new RepositoryNotFoundException(name);

				final Repository db = srv.getRepository();
				db.incrementOpen();
				return db;
			}
		});
		gs.setReceivePackFactory(new DefaultReceivePackFactory() {
			public ReceivePack create(HttpServletRequest req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				ReceivePack recv = super.create(req
				if (maxPackSize > 0)
					recv.setMaxPackSizeLimit(maxPackSize);
				if (postHook != null)
					recv.setPostReceiveHook(postHook);
				if (preHook != null)
					recv.setPreReceiveHook(preHook);
				if (oc != null)
					recv.setObjectChecker(oc);
				return recv;
			}

		});
		app.addServlet(new ServletHolder(gs)

		server.setUp();

		srvRepo = srv.getRepository();
		srvURI = toURIish(app

		StoredConfig cfg = srvRepo.getConfig();
		cfg.setBoolean("http"
		cfg.save();
	}

	@Test
	public void testRuntimeExceptionInPreReceiveHook() throws Exception {
		final TestRepository client = createTestRepository();
		final RevBlob Q_txt = client
				.blob("some blob content to measure pack size");
		final RevCommit Q = client.commit().add("Q"
		final Repository clientRepo = client.getRepository();
		final String srvBranchName = Constants.R_HEADS + "new.branch";
		Transport t;

		maxPackSize = 0;
		postHook = null;
		preHook = new PreReceiveHook() {
			@Override
			public void onPreReceive(ReceivePack rp
					Collection<ReceiveCommand> commands) {
				throw new IllegalStateException();
			}
		};

		t = Transport.open(clientRepo
		try {
			RemoteRefUpdate update = new RemoteRefUpdate(clientRepo
					srvBranchName
			try {
				t.push(NullProgressMonitor.INSTANCE
						Collections.singleton(update));
				fail("should not reach this line");
			} catch (Exception e) {
				assertTrue(e instanceof TransportException);
			}
		} finally {
			t.close();
		}
	}

	@Test
	public void testObjectCheckerException() throws Exception {
		final TestRepository client = createTestRepository();
		final RevBlob Q_txt = client
				.blob("some blob content to measure pack size");
		final RevCommit Q = client.commit().add("Q"
		final Repository clientRepo = client.getRepository();
		final String srvBranchName = Constants.R_HEADS + "new.branch";
		Transport t;

		maxPackSize = 0;
		postHook = null;
		preHook = null;
		oc = new ObjectChecker() {
			@Override
			public void checkCommit(byte[] raw) throws CorruptObjectException {
				throw new IllegalStateException();
			}
		};

		t = Transport.open(clientRepo
		try {
			RemoteRefUpdate update = new RemoteRefUpdate(clientRepo
					srvBranchName
			try {
				t.push(NullProgressMonitor.INSTANCE
						Collections.singleton(update));
				fail("should not reach this line");
			} catch (Exception e) {
				assertTrue(e instanceof TransportException);
			}
		} finally {
			t.close();
		}
	}

	@Test
	public void testUnpackErrorWithSubsequentExceptionInPostReceiveHook()
			throws Exception {
		final TestRepository client = createTestRepository();
		final RevBlob Q_txt = client
				.blob("some blob content to measure pack size");
		final RevCommit Q = client.commit().add("Q"
		final Repository clientRepo = client.getRepository();
		final String srvBranchName = Constants.R_HEADS + "new.branch";
		Transport t;

		maxPackSize = 400;
		postHook = new PostReceiveHook() {
			public void onPostReceive(ReceivePack rp
					Collection<ReceiveCommand> commands) {
				rp.getPackSize();
			}
		};

		t = Transport.open(clientRepo
		try {
			RemoteRefUpdate update = new RemoteRefUpdate(clientRepo
					srvBranchName
			try {
				t.push(NullProgressMonitor.INSTANCE
						Collections.singleton(update));
				fail("should not reach this line");
			} catch (Exception e) {
				assertTrue(e instanceof TooLargePackException);
			}
		} finally {
			t.close();
		}
	}
}

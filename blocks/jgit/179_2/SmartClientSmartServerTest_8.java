
package org.eclipse.jgit.http.test;

import java.io.File;
import java.net.URI;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.test.util.AccessEvent;
import org.eclipse.jgit.http.test.util.HttpTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;

public class HttpClientTests extends HttpTestCase {
	private TestRepository remoteRepository;

	private URIish dumbAuthNoneURI;

	private URIish dumbAuthBasicURI;

	private URIish smartAuthNoneURI;

	private URIish smartAuthBasicURI;

	protected void setUp() throws Exception {
		super.setUp();

		remoteRepository = createTestRepository();
		remoteRepository.update(master

		ServletContextHandler dNone = dumb("/dnone");
		ServletContextHandler dBasic = server.authBasic(dumb("/dbasic"));

		ServletContextHandler sNone = smart("/snone");
		ServletContextHandler sBasic = server.authBasic(smart("/sbasic"));

		server.setUp();

		final String srcName = nameOf(remoteRepository);
		dumbAuthNoneURI = toURIish(dNone
		dumbAuthBasicURI = toURIish(dBasic

		smartAuthNoneURI = toURIish(sNone
		smartAuthBasicURI = toURIish(sBasic
	}

	private ServletContextHandler dumb(final String path) {
		final File srcGit = remoteRepository.getRepository().getDirectory();
		final URI base = srcGit.getParentFile().toURI();

		ServletContextHandler ctx = server.addContext(path);
		ctx.setResourceBase(base.toString());
		ctx.addServlet(DefaultServlet.class
		return ctx;
	}

	private ServletContextHandler smart(final String path) {
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver() {
			public Repository open(HttpServletRequest req
					throws RepositoryNotFoundException
					ServiceNotEnabledException {
				if (!name.equals(nameOf(remoteRepository)))
					throw new RepositoryNotFoundException(name);

				final Repository db = remoteRepository.getRepository();
				db.incrementOpen();
				return db;
			}
		});

		ServletContextHandler ctx = server.addContext(path);
		ctx.addServlet(new ServletHolder(gs)
		return ctx;
	}

	private static String nameOf(final TestRepository db) {
		return db.getRepository().getDirectory().getName();
	}

	public void testRepositoryNotFound_Dumb() throws Exception {
		URIish uri = toURIish("/dumb.none/not-found");
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("connection opened to not found repository");
			} catch (NoRemoteRepositoryException err) {
				String exp = uri + ": " + uri
						+ "/info/refs?service=git-upload-pack not found";
				assertEquals(exp
			}
		} finally {
			t.close();
		}
	}

	public void testRepositoryNotFound_Smart() throws Exception {
		URIish uri = toURIish("/smart.none/not-found");
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("connection opened to not found repository");
			} catch (NoRemoteRepositoryException err) {
				String exp = uri + ": " + uri
						+ "/info/refs?service=git-upload-pack not found";
				assertEquals(exp
			}
		} finally {
			t.close();
		}
	}

	public void testListRemote_Dumb_DetachedHEAD() throws Exception {
		Repository src = remoteRepository.getRepository();
		RefUpdate u = src.updateRef(Constants.HEAD
		RevCommit Q = remoteRepository.commit().message("Q").create();
		u.setNewObjectId(Q);
		assertEquals(RefUpdate.Result.FORCED

		Repository dst = createBareRepository();
		Ref head;
		Transport t = Transport.open(dst
		try {
			FetchConnection c = t.openFetch();
			try {
				head = c.getRef(Constants.HEAD);
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}
		assertNotNull("has " + Constants.HEAD
		assertEquals(Q
	}

	public void testListRemote_Dumb_NoHEAD() throws Exception {
		Repository src = remoteRepository.getRepository();
		File headref = new File(src.getDirectory()
		assertTrue("HEAD used to be present"
		assertFalse("HEAD is gone"

		Repository dst = createBareRepository();
		Ref head;
		Transport t = Transport.open(dst
		try {
			FetchConnection c = t.openFetch();
			try {
				head = c.getRef(Constants.HEAD);
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}
		assertNull("has no " + Constants.HEAD
	}

	public void testListRemote_Smart_DetachedHEAD() throws Exception {
		Repository src = remoteRepository.getRepository();
		RefUpdate u = src.updateRef(Constants.HEAD
		RevCommit Q = remoteRepository.commit().message("Q").create();
		u.setNewObjectId(Q);
		assertEquals(RefUpdate.Result.FORCED

		Repository dst = createBareRepository();
		Ref head;
		Transport t = Transport.open(dst
		try {
			FetchConnection c = t.openFetch();
			try {
				head = c.getRef(Constants.HEAD);
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}
		assertNotNull("has " + Constants.HEAD
		assertEquals(Q
	}

	public void testListRemote_Smart_WithQueryParameters() throws Exception {
		URIish myURI = toURIish("/snone/do?r=1&p=test.git");
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("test did not fail to find repository as expected");
			} catch (NoRemoteRepositoryException err) {
			}
		} finally {
			t.close();
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals("/snone/do"
		assertEquals(3
		assertEquals("1"
		assertEquals("test.git/info/refs"
		assertEquals("git-upload-pack"
		assertEquals(404
	}

	public void testListRemote_Dumb_NeedsAuth() throws Exception {
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("connection opened even info/refs needs auth basic");
			} catch (TransportException err) {
				String status = "401 Unauthorized";
				String exp = dumbAuthBasicURI + ": " + status;
				assertEquals(exp
			}
		} finally {
			t.close();
		}
	}

	public void testListRemote_Smart_UploadPackNeedsAuth() throws Exception {
		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("connection opened even though service disabled");
			} catch (TransportException err) {
				String status = "401 Unauthorized";
				String exp = smartAuthBasicURI + ": " + status;
				assertEquals(exp
			}
		} finally {
			t.close();
		}
	}

	public void testListRemote_Smart_UploadPackDisabled() throws Exception {
		Repository src = remoteRepository.getRepository();
		src.getConfig().setBoolean("http"
		src.getConfig().save();

		Repository dst = createBareRepository();
		Transport t = Transport.open(dst
		try {
			try {
				t.openFetch();
				fail("connection opened even though service disabled");
			} catch (TransportException err) {
				String exp = smartAuthNoneURI
						+ ": git-upload-pack not permitted";
				assertEquals(exp
			}
		} finally {
			t.close();
		}
	}
}

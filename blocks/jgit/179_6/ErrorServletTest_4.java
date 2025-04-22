
package org.eclipse.jgit.http.test;

import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;
import static org.eclipse.jgit.util.HttpSupport.HDR_PRAGMA;
import static org.eclipse.jgit.util.HttpSupport.HDR_USER_AGENT;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.test.util.AccessEvent;
import org.eclipse.jgit.http.test.util.HttpTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportHttp;
import org.eclipse.jgit.transport.URIish;

public class DumbClientSmartServerTest extends HttpTestCase {
	private Repository remoteRepository;

	private URIish remoteURI;

	private RevBlob A_txt;

	private RevCommit A

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
		app.addServlet(new ServletHolder(gs)

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app

		A_txt = src.blob("A");
		A = src.commit().add("A_txt"
		B = src.commit().parent(A).add("A_txt"
		src.update(master
	}

	public void testListRemote() throws IOException {
		Repository dst = createBareRepository();

		assertEquals("http"

		Map<String
		Transport t = Transport.open(dst
		((TransportHttp) t).setUseSmartHttp(false);
		try {
			assertTrue("isa TransportHttp"
			assertTrue("isa HttpTransport"

			FetchConnection c = t.openFetch();
			try {
				map = c.getRefsMap();
			} finally {
				c.close();
			}
		} finally {
			t.close();
		}

		assertNotNull("have map of refs"
		assertEquals(2

		assertNotNull("has " + master
		assertEquals(B

		assertNotNull("has " + Constants.HEAD
		assertEquals(B

		List<AccessEvent> requests = getRequests();
		assertEquals(2
		assertEquals(0

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNull("no service parameter"
		assertEquals("no-cache"
		assertNotNull("has user-agent"
		assertTrue("is jgit agent"
				.startsWith("JGit/"));
		assertEquals(200
		assertEquals("text/plain;charset=UTF-8"
				.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent head = requests.get(1);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(0
		assertEquals(200
		assertEquals("text/plain"
	}

	public void testInitialClone_Small() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		Transport t = Transport.open(dst
		((TransportHttp) t).setUseSmartHttp(false);
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> loose = getRequests(loose(remoteURI
		assertEquals(1
		assertEquals("GET"
		assertEquals(0
		assertEquals(200
		assertEquals("application/x-git-loose-object"
				.getResponseHeader(HDR_CONTENT_TYPE));
	}

	public void testInitialClone_Packed() throws Exception {
		new TestRepository(remoteRepository).packAndPrune();

		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		Transport t = Transport.open(dst
		((TransportHttp) t).setUseSmartHttp(false);
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> req;

		req = getRequests(loose(remoteURI
		assertEquals(1
		assertEquals("GET"
		assertEquals(0
		assertEquals(404

		req = getRequests(join(remoteURI
		assertEquals(1
		assertEquals("GET"
		assertEquals(0
		assertEquals(200
		assertEquals("text/plain;charset=UTF-8"
				HDR_CONTENT_TYPE));
	}

	public void testPushNotSupported() throws Exception {
		final TestRepository src = createTestRepository();
		final RevCommit Q = src.commit().create();
		final Repository db = src.getRepository();

		Transport t = Transport.open(db
		((TransportHttp) t).setUseSmartHttp(false);
		try {
			try {
				t.push(NullProgressMonitor.INSTANCE
				fail("push incorrectly completed against a smart server");
			} catch (NotSupportedException nse) {
				String exp = "smart HTTP push disabled";
				assertEquals(exp
			}
		} finally {
			t.close();
		}
	}
}

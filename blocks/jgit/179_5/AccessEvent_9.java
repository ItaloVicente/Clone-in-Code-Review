
package org.eclipse.jgit.http.test;

import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.FilterMapping;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.RepositoryResolver;
import org.eclipse.jgit.http.server.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.http.test.util.AccessEvent;
import org.eclipse.jgit.http.test.util.HttpTestCase;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.RepositoryConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportHttp;
import org.eclipse.jgit.transport.URIish;

public class SmartClientSmartServerTest extends HttpTestCase {
	private static final String HDR_CONTENT_LENGTH = "Content-Length";

	private static final String HDR_TRANSFER_ENCODING = "Transfer-Encoding";

	private Repository remoteRepository;

	private URIish remoteURI;

	private URIish brokenURI;

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

		ServletContextHandler broken = server.addContext("/bad");
		broken.addFilter(new FilterHolder(new Filter() {
			public void doFilter(ServletRequest request
					ServletResponse response
					throws IOException
				final HttpServletResponse r = (HttpServletResponse) response;
				r.setContentType("text/plain");
				r.setCharacterEncoding("UTF-8");
				PrintWriter w = r.getWriter();
				w.print("OK");
				w.close();
			}

			public void init(FilterConfig filterConfig) throws ServletException {
			}

			public void destroy() {
			}
		})
		broken.addServlet(new ServletHolder(gs)

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app
		brokenURI = toURIish(broken

		A_txt = src.blob("A");
		A = src.commit().add("A_txt"
		B = src.commit().parent(A).add("A_txt"
		src.update(master

		src.update("refs/garbage/a/very/long/ref/name/to/compress"
	}

	public void testListRemote() throws IOException {
		Repository dst = createBareRepository();

		assertEquals("http"

		Map<String
		Transport t = Transport.open(dst
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
		assertEquals(3

		assertNotNull("has " + master
		assertEquals(B

		assertNotNull("has " + Constants.HEAD
		assertEquals(B

		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"
	}

	public void testInitialClone_Small() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		Transport t = Transport.open(dst
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}

		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(2

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				.getRequestHeader(HDR_TRANSFER_ENCODING));
		assertNull("no compression (too small)"
				.getRequestHeader(HDR_CONTENT_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				.getResponseHeader(HDR_CONTENT_TYPE));
		assertNull("no compression (never compressed)"
				.getResponseHeader(HDR_CONTENT_ENCODING));
	}

	public void testFetchUpdateExisting() throws Exception {
		TestRepository dst = createTestRepository();
		Transport t = Transport.open(dst.getRepository()
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}
		assertEquals(B
		List<AccessEvent> cloneRequests = getRequests();

		TestRepository.BranchBuilder b = dst.branch(master);
		for (int i = 0; i < 32 - 1; i++)

		b = new TestRepository(remoteRepository).branch(master);
		RevCommit Z = b.commit().message("Z").create();

		t = Transport.open(dst.getRepository()
		try {
			t.fetch(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}
		assertEquals(Z

		List<AccessEvent> requests = getRequests();
		requests.removeAll(cloneRequests);
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				.getResponseHeader(HDR_CONTENT_TYPE));

		service = requests.get(2);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				.getResponseHeader(HDR_CONTENT_TYPE));
	}

	public void testInitialClone_BrokenServer() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		Transport t = Transport.open(dst
		try {
			try {
				t.fetch(NullProgressMonitor.INSTANCE
				fail("fetch completed despite upload-pack being broken");
			} catch (TransportException err) {
				String exp = brokenURI + ": expected"
						+ " Content-Type application/x-git-upload-pack-result;"
						+ " received Content-Type text/plain;charset=UTF-8";
				assertEquals(exp
			}
		} finally {
			t.close();
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(2

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(brokenURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(brokenURI
		assertEquals(0
		assertEquals(200
		assertEquals("text/plain;charset=UTF-8"
				.getResponseHeader(HDR_CONTENT_TYPE));
	}

	public void testPush_NotAuthorized() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
		Transport t;

		t = Transport.open(db
		try {
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate u = new RemoteRefUpdate(src.getRepository()
					srcExpr
			try {
				t.push(NullProgressMonitor.INSTANCE
				fail("anonymous push incorrectly accepted without error");
			} catch (TransportException e) {
				final String status = "401 Unauthorized";
				final String exp = remoteURI.toString() + ": " + status;
				assertEquals(exp
			}
		} finally {
			t.close();
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-receive-pack"
		assertEquals(401
	}

	public void testPush_CreateBranch() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
		Transport t;

		enableReceivePack();

		t = Transport.open(db
		try {
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate u = new RemoteRefUpdate(src.getRepository()
					srcExpr
			t.push(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}

		assertTrue(remoteRepository.hasObject(Q_txt));
		assertNotNull("has " + dstName
		assertEquals(Q
		fsck(remoteRepository

		final ReflogReader log = remoteRepository.getReflogReader(dstName);
		assertNotNull("has log for " + dstName);

		final ReflogReader.Entry last = log.getLastEntry();
		assertNotNull("has last entry"
		assertEquals(ObjectId.zeroId()
		assertEquals(Q
		assertEquals("anonymous"

		final String clientHost = remoteURI.getHost();
		assertEquals("anonymous@" + clientHost
		assertEquals("push: created"

		List<AccessEvent> requests = getRequests();
		assertEquals(2

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-receive-pack"
		assertEquals(200
		assertEquals("application/x-git-receive-pack-advertisement"
				.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-receive-pack-result"
				.getResponseHeader(HDR_CONTENT_TYPE));
	}

	public void testPush_ChunkedEncoding() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_bin = src.blob(new TestRng("Q").nextBytes(128 * 1024));
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";
		Transport t;

		enableReceivePack();

		db.getConfig().setInt("core"
		db.getConfig().setInt("http"
		db.getConfig().save();

		t = Transport.open(db
		try {
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate u = new RemoteRefUpdate(src.getRepository()
					srcExpr
			t.push(NullProgressMonitor.INSTANCE
		} finally {
			t.close();
		}

		assertTrue(remoteRepository.hasObject(Q_bin));
		assertNotNull("has " + dstName
		assertEquals(Q
		fsck(remoteRepository

		List<AccessEvent> requests = getRequests();
		assertEquals(2

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-receive-pack"
		assertEquals(200
		assertEquals("application/x-git-receive-pack-advertisement"
				.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNull("no content-length"
				.getRequestHeader(HDR_CONTENT_LENGTH));
		assertEquals("chunked"

		assertEquals(200
		assertEquals("application/x-git-receive-pack-result"
				.getResponseHeader(HDR_CONTENT_TYPE));
	}

	private void enableReceivePack() throws IOException {
		final RepositoryConfig cfg = remoteRepository.getConfig();
		cfg.setBoolean("http"
		cfg.save();
	}
}


package org.eclipse.jgit.http.test;

import static org.eclipse.jgit.util.HttpSupport.HDR_ACCEPT;
import static org.eclipse.jgit.util.HttpSupport.HDR_PRAGMA;
import static org.eclipse.jgit.util.HttpSupport.HDR_USER_AGENT;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.NotSupportedException;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.AccessEvent;
import org.eclipse.jgit.junit.http.HttpTestCase;
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
import org.eclipse.jgit.transport.http.HttpConnectionFactory;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class DumbClientDumbServerTest extends HttpTestCase {
	private Repository remoteRepository;

	private URIish remoteURI;

	private RevBlob A_txt;

	private RevCommit A

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ new JDKHttpConnectionFactory() }
				{ new HttpClientConnectionFactory() } });
	}

	public DumbClientDumbServerTest(HttpConnectionFactory cf) {
		HttpTransport.setConnectionFactory(cf);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final TestRepository<Repository> src = createTestRepository();
		final File srcGit = src.getRepository().getDirectory();
		final URI base = srcGit.getParentFile().toURI();

		ServletContextHandler app = server.addContext("/git");
		app.setResourceBase(base.toString());
		ServletHolder holder = app.addServlet(DefaultServlet.class
		holder.setInitParameter("aliases"
		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app

		A_txt = src.blob("A");
		A = src.commit().add("A_txt"
		B = src.commit().parent(A).add("A_txt"
		src.update(master
	}

	@Test
	public void testListRemote() throws IOException {
		Repository dst = createBareRepository();

		assertEquals("http"

		Map<String
		try (Transport t = Transport.open(dst
			assertTrue("isa TransportHttp"
			assertTrue("isa HttpTransport"

			try (FetchConnection c = t.openFetch()) {
				map = c.getRefsMap();
			}
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
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals("no-cache"
		assertNotNull("has user-agent"
		assertTrue("is jgit agent"
				.startsWith("JGit/"));
		assertEquals("application/x-git-upload-pack-advertisement
				.getRequestHeader(HDR_ACCEPT));
		assertEquals(200

		AccessEvent head = requests.get(1);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(0
		assertEquals("no-cache"
		assertNotNull("has user-agent"
		assertTrue("is jgit agent"
				.startsWith("JGit/"));
		assertEquals(200
	}

	@Test
	public void testInitialClone_Loose() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.getObjectDatabase().has(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.getObjectDatabase().has(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> loose = getRequests(loose(remoteURI
		assertEquals(1
		assertEquals("GET"
		assertEquals(0
		assertEquals(200
	}

	@Test
	public void testInitialClone_Packed() throws Exception {
		try (TestRepository<Repository> tr = new TestRepository<>(
				remoteRepository)) {
			tr.packAndPrune();
		}

		Repository dst = createBareRepository();
		assertFalse(dst.getObjectDatabase().has(A_txt));

		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}

		assertTrue(dst.getObjectDatabase().has(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> req;
		AccessEvent event;

		req = getRequests(loose(remoteURI
		assertEquals(1
		event = req.get(0);
		assertEquals("GET"
		assertEquals(0
		assertEquals(404

		req = getRequests(join(remoteURI
		assertEquals(1
		event = req.get(0);
		assertEquals("GET"
		assertEquals(0
		assertEquals("no-cache"
		assertNotNull("has user-agent"
		assertTrue("is jgit agent"
				.startsWith("JGit/"));
		assertEquals(200
	}

	@Test
	public void testPushNotSupported() throws Exception {
		final TestRepository src = createTestRepository();
		final RevCommit Q = src.commit().create();
		final Repository db = src.getRepository();

		try (Transport t = Transport.open(db
			try {
				t.push(NullProgressMonitor.INSTANCE
				fail("push incorrectly completed against a dumb server");
			} catch (NotSupportedException nse) {
				String exp = "remote does not support smart HTTP push";
				assertEquals(exp
			}
		}
	}
}

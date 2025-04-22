
package org.eclipse.jgit.http.test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.theInstance;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.OutputStream;
import java.net.URI;
import java.net.URL;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.eclipse.jetty.servlet.DefaultServlet;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.NoRemoteRepositoryException;
import org.eclipse.jgit.errors.RepositoryNotFoundException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.AccessEvent;
import org.eclipse.jgit.junit.http.AppServer;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.RefUpdate;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.PacketLineIn;
import org.eclipse.jgit.transport.PacketLineOut;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.http.HttpConnection;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.resolver.RepositoryResolver;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.junit.Before;
import org.junit.Test;

public class HttpClientTests extends HttpTestCase {
	private TestRepository<Repository> remoteRepository;

	private URIish dumbAuthNoneURI;

	private URIish dumbAuthBasicURI;

	private URIish smartAuthNoneURI;

	private URIish smartAuthBasicURI;

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		remoteRepository = createTestRepository();
		remoteRepository.update(master

		ServletContextHandler dNone = dumb("/dnone");
		ServletContextHandler dBasic = server.authBasic(dumb("/dbasic"));

		ServletContextHandler sNone = smart("/snone");
		ServletContextHandler sBasic = server.authBasic(smart("/sbasic"));

		server.setUp();

		final String srcName = nameOf(remoteRepository.getRepository());
		dumbAuthNoneURI = toURIish(dNone
		dumbAuthBasicURI = toURIish(dBasic

		smartAuthNoneURI = toURIish(sNone
		smartAuthBasicURI = toURIish(sBasic
	}

	private ServletContextHandler dumb(String path) {
		final File srcGit = remoteRepository.getRepository().getDirectory();
		final URI base = srcGit.getParentFile().toURI();

		ServletContextHandler ctx = server.addContext(path);
		ctx.setResourceBase(base.toString());
		ServletHolder holder = ctx.addServlet(DefaultServlet.class
		holder.setInitParameter("aliases"
		return ctx;
	}

	private ServletContextHandler smart(String path) {
		GitServlet gs = new GitServlet();
		gs.setRepositoryResolver(new RepositoryResolver<HttpServletRequest>() {
			@Override
			public Repository open(HttpServletRequest req
					throws RepositoryNotFoundException
					ServiceNotEnabledException {
				final Repository db = remoteRepository.getRepository();
				if (!name.equals(nameOf(db)))
					throw new RepositoryNotFoundException(name);

				db.incrementOpen();
				return db;
			}
		});

		ServletContextHandler ctx = server.addContext(path);
		ctx.addServlet(new ServletHolder(gs)
		return ctx;
	}

	private static String nameOf(Repository db) {
		return db.getDirectory().getName();
	}

	@Test
	public void testRepositoryNotFound_Dumb() throws Exception {
		URIish uri = toURIish("/dumb.none/not-found");
		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("connection opened to not found repository");
			} catch (NoRemoteRepositoryException err) {
				String exp = uri + ": " + uri
						+ "/info/refs?service=git-upload-pack not found";
				assertNotNull(err.getMessage());
				assertTrue("Unexpected error message"
						err.getMessage().startsWith(exp));
			}
		}
	}

	@Test
	public void testRepositoryNotFound_Smart() throws Exception {
		URIish uri = toURIish("/smart.none/not-found");
		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("connection opened to not found repository");
			} catch (NoRemoteRepositoryException err) {
				String exp = uri + ": " + uri
						+ "/info/refs?service=git-upload-pack not found";
				assertNotNull(err.getMessage());
				assertTrue("Unexpected error message"
						err.getMessage().startsWith(exp));
			}
		}
	}

	@Test
	public void testListRemote_Dumb_DetachedHEAD() throws Exception {
		Repository src = remoteRepository.getRepository();
		RefUpdate u = src.updateRef(Constants.HEAD
		RevCommit Q = remoteRepository.commit().message("Q").create();
		u.setNewObjectId(Q);
		assertEquals(RefUpdate.Result.FORCED

		Repository dst = createBareRepository();
		Ref head;
		try (Transport t = Transport.open(dst
				FetchConnection c = t.openFetch()) {
			head = c.getRef(Constants.HEAD);
		}
		assertNotNull("has " + Constants.HEAD
		assertEquals(Q
	}

	@Test
	public void testListRemote_Dumb_NoHEAD() throws Exception {
		Repository src = remoteRepository.getRepository();
		File headref = new File(src.getDirectory()
		assertTrue("HEAD used to be present"
		assertFalse("HEAD is gone"

		Repository dst = createBareRepository();
		Ref head;
		try (Transport t = Transport.open(dst
				FetchConnection c = t.openFetch()) {
			head = c.getRef(Constants.HEAD);
		}
		assertNull("has no " + Constants.HEAD
	}

	@Test
	public void testListRemote_Smart_DetachedHEAD() throws Exception {
		Repository src = remoteRepository.getRepository();
		RefUpdate u = src.updateRef(Constants.HEAD
		RevCommit Q = remoteRepository.commit().message("Q").create();
		u.setNewObjectId(Q);
		assertEquals(RefUpdate.Result.FORCED

		Repository dst = createBareRepository();
		Ref head;
		try (Transport t = Transport.open(dst
				FetchConnection c = t.openFetch()) {
			head = c.getRef(Constants.HEAD);
		}
		assertNotNull("has " + Constants.HEAD
		assertEquals(Q
	}

	@Test
	public void testListRemote_Smart_WithQueryParameters() throws Exception {
		URIish myURI = toURIish("/snone/do?r=1&p=test.git");
		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("test did not fail to find repository as expected");
			} catch (NoRemoteRepositoryException err) {
			}
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

	@Test
	public void testListRemote_Dumb_NeedsAuth() throws Exception {
		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("connection opened even info/refs needs auth basic");
			} catch (TransportException err) {
				String exp = dumbAuthBasicURI + ": "
						+ JGitText.get().noCredentialsProvider;
				assertEquals(exp
			}
		}
	}

	@Test
	public void testListRemote_Dumb_Auth() throws Exception {
		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
					AppServer.username
			t.openFetch().close();
		}
		try (Transport t = Transport.open(dst
			t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
					AppServer.username
			try {
				t.openFetch();
				fail("connection opened even info/refs needs auth basic and we provide wrong password");
			} catch (TransportException err) {
				String exp = dumbAuthBasicURI + ": "
						+ JGitText.get().notAuthorized;
				assertEquals(exp
			}
		}
	}

	@Test
	public void testListRemote_Smart_UploadPackNeedsAuth() throws Exception {
		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("connection opened even though service disabled");
			} catch (TransportException err) {
				String exp = smartAuthBasicURI + ": "
						+ JGitText.get().noCredentialsProvider;
				assertEquals(exp
			}
		}
	}

	@Test
	public void testListRemote_Smart_UploadPackDisabled() throws Exception {
		Repository src = remoteRepository.getRepository();
		final StoredConfig cfg = src.getConfig();
		cfg.setBoolean("http"
		cfg.save();

		Repository dst = createBareRepository();
		try (Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("connection opened even though service disabled");
			} catch (TransportException err) {
				String exp = smartAuthNoneURI + ": "
						+ JGitText.get().serviceNotEnabledNoName;
				assertEquals(exp
			}
		}
	}

	@Test
	public void testListRemoteWithoutLocalRepository() throws Exception {
		try (Transport t = Transport.open(smartAuthNoneURI);
				FetchConnection c = t.openFetch()) {
			Ref head = c.getRef(Constants.HEAD);
			assertNotNull(head);
		}
	}

	@Test
	public void testHttpClientWantsV2ButServerNotConfigured() throws Exception {
		JDKHttpConnectionFactory f = new JDKHttpConnectionFactory();
		String url = smartAuthNoneURI.toString() + "/info/refs?service=git-upload-pack";
		HttpConnection c = f.create(new URL(url));
		c.setRequestMethod("GET");
		c.setRequestProperty("Git-Protocol"
		c.connect();
		assertEquals(200

		PacketLineIn pckIn = new PacketLineIn(c.getInputStream());

		assertThat(pckIn.readString()
		assertThat(pckIn.readString()
		assertTrue(pckIn.readString().matches("[0-9a-f]{40} HEAD.*"));
	}

	@Test
	public void testV2HttpFirstResponse() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"

		JDKHttpConnectionFactory f = new JDKHttpConnectionFactory();
		String url = smartAuthNoneURI.toString() + "/info/refs?service=git-upload-pack";
		HttpConnection c = f.create(new URL(url));
		c.setRequestMethod("GET");
		c.setRequestProperty("Git-Protocol"
		c.connect();
		assertEquals(200

		PacketLineIn pckIn = new PacketLineIn(c.getInputStream());
		assertThat(pckIn.readString()

		String s;
		while ((s = pckIn.readString()) != PacketLineIn.END) {
			assertTrue(!s.isEmpty());
		}
	}

	@Test
	public void testV2HttpSubsequentResponse() throws Exception {
		remoteRepository.getRepository().getConfig().setInt(
				"protocol"

		JDKHttpConnectionFactory f = new JDKHttpConnectionFactory();
		String url = smartAuthNoneURI.toString() + "/git-upload-pack";
		HttpConnection c = f.create(new URL(url));
		c.setRequestMethod("POST");
		c.setRequestProperty("Content-Type"
		c.setRequestProperty("Git-Protocol"
		c.setDoOutput(true);
		c.connect();


		try (OutputStream os = c.getOutputStream()) {
			PacketLineOut pckOut = new PacketLineOut(os);
			pckOut.writeString("command=ls-refs");
			pckOut.writeDelim();
			pckOut.end();
		}

		PacketLineIn pckIn = new PacketLineIn(c.getInputStream());

		String s;
		while ((s = pckIn.readString()) != PacketLineIn.END) {
			assertTrue(s.matches("[0-9a-f]{40} [A-Za-z/]*"));
		}

		assertEquals(200
	}
}

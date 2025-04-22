
package org.eclipse.jgit.http.test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_ENCODING;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_LENGTH;
import static org.eclipse.jgit.util.HttpSupport.HDR_CONTENT_TYPE;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URISyntaxException;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.RemoteRepositoryException;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.errors.UnsupportedCredentialItem;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.server.resolver.DefaultUploadPackFactory;
import org.eclipse.jgit.internal.JGitText;
import org.eclipse.jgit.internal.storage.dfs.DfsRepositoryDescription;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.TestRng;
import org.eclipse.jgit.junit.http.AccessEvent;
import org.eclipse.jgit.junit.http.AppServer;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.Constants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectIdRef;
import org.eclipse.jgit.lib.ObjectInserter;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.ReflogEntry;
import org.eclipse.jgit.lib.ReflogReader;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.StoredConfig;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.AbstractAdvertiseRefsHook;
import org.eclipse.jgit.transport.AdvertiseRefsHook;
import org.eclipse.jgit.transport.CredentialItem;
import org.eclipse.jgit.transport.CredentialsProvider;
import org.eclipse.jgit.transport.FetchConnection;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.RefSpec;
import org.eclipse.jgit.transport.RemoteRefUpdate;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.TransportHttp;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.UploadPack;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.transport.http.HttpConnectionFactory;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.eclipse.jgit.transport.resolver.ServiceNotAuthorizedException;
import org.eclipse.jgit.transport.resolver.ServiceNotEnabledException;
import org.eclipse.jgit.transport.resolver.UploadPackFactory;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.SystemReader;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SmartClientSmartServerTest extends HttpTestCase {
	private static final String HDR_TRANSFER_ENCODING = "Transfer-Encoding";

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private AdvertiseRefsHook advertiseRefsHook;

	private Repository remoteRepository;

	private CredentialsProvider testCredentials = new UsernamePasswordCredentialsProvider(
			AppServer.username

	private URIish remoteURI;

	private URIish brokenURI;

	private URIish redirectURI;

	private URIish authURI;

	private URIish authOnPostURI;

	private RevBlob A_txt;

	private RevCommit A

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ new JDKHttpConnectionFactory() }
				{ new HttpClientConnectionFactory() } });
	}

	public SmartClientSmartServerTest(HttpConnectionFactory cf) {
		HttpTransport.setConnectionFactory(cf);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		final TestRepository<Repository> src = createTestRepository();
		final String srcName = src.getRepository().getDirectory().getName();
		src.getRepository()
				.getConfig()
				.setBoolean(ConfigConstants.CONFIG_CORE_SECTION
						ConfigConstants.CONFIG_KEY_LOGALLREFUPDATES

		GitServlet gs = new GitServlet();
		gs.setUploadPackFactory(new UploadPackFactory<HttpServletRequest>() {
			@Override
			public UploadPack create(HttpServletRequest req
					throws ServiceNotEnabledException
					ServiceNotAuthorizedException {
				DefaultUploadPackFactory f = new DefaultUploadPackFactory();
				UploadPack up = f.create(req
				if (advertiseRefsHook != null) {
					up.setAdvertiseRefsHook(advertiseRefsHook);
				}
				return up;
			}
		});

		ServletContextHandler app = addNormalContext(gs

		ServletContextHandler broken = addBrokenContext(gs

		ServletContextHandler redirect = addRedirectContext(gs);

		ServletContextHandler auth = addAuthContext(gs

		ServletContextHandler authOnPost = addAuthContext(gs

		server.setUp();

		remoteRepository = src.getRepository();
		remoteURI = toURIish(app
		brokenURI = toURIish(broken
		redirectURI = toURIish(redirect
		authURI = toURIish(auth
		authOnPostURI = toURIish(authOnPost

		A_txt = src.blob("A");
		A = src.commit().add("A_txt"
		B = src.commit().parent(A).add("A_txt"
		src.update(master

		unreachableCommit = src.commit().add("A_txt"

		src.update("refs/garbage/a/very/long/ref/name/to/compress"
	}

	private ServletContextHandler addNormalContext(GitServlet gs
		ServletContextHandler app = server.addContext("/git");
		app.addFilter(new FilterHolder(new Filter() {

			@Override
			public void init(FilterConfig filterConfig)
					throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request
					ServletResponse response
					throws IOException
				final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				final StringBuffer fullUrl = httpServletRequest.getRequestURL();
				if (httpServletRequest.getQueryString() != null) {
					fullUrl.append("?")
							.append(httpServletRequest.getQueryString());
				}
				String urlString = fullUrl.toString();
				if ("POST".equalsIgnoreCase(httpServletRequest.getMethod())) {
					httpServletResponse.setStatus(
							HttpServletResponse.SC_MOVED_PERMANENTLY);
					httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
							urlString.replace("/post/"
				} else {
					String path = httpServletRequest.getPathInfo();
					path = path.replace("/post/"
					if (httpServletRequest.getQueryString() != null) {
						path += '?' + httpServletRequest.getQueryString();
					}
					RequestDispatcher dispatcher = httpServletRequest
							.getRequestDispatcher(path);
					dispatcher.forward(httpServletRequest
				}
			}

			@Override
			public void destroy() {
			}
		})
		gs.setRepositoryResolver(new TestRepositoryResolver(src
		app.addServlet(new ServletHolder(gs)
		return app;
	}

	private ServletContextHandler addBrokenContext(GitServlet gs
			String srcName) {
		ServletContextHandler broken = server.addContext("/bad");
		broken.addFilter(new FilterHolder(new Filter() {

			@Override
			public void doFilter(ServletRequest request
					ServletResponse response
					throws IOException
				final HttpServletResponse r = (HttpServletResponse) response;
				r.setContentType("text/plain");
				r.setCharacterEncoding(UTF_8.name());
				try (PrintWriter w = r.getWriter()) {
					w.print("OK");
				}
			}

			@Override
			public void init(FilterConfig filterConfig)
					throws ServletException {
			}

			@Override
			public void destroy() {
			}
		})
				EnumSet.of(DispatcherType.REQUEST));
		broken.addServlet(new ServletHolder(gs)
		return broken;
	}

	private ServletContextHandler addAuthContext(GitServlet gs
			String contextPath
		ServletContextHandler auth = server.addContext('/' + contextPath);
		auth.addServlet(new ServletHolder(gs)
		return server.authBasic(auth
	}

	private ServletContextHandler addRedirectContext(GitServlet gs) {
		ServletContextHandler redirect = server.addContext("/redirect");
		redirect.addFilter(new FilterHolder(new Filter() {

			private Pattern responsePattern = Pattern
					.compile("/response/(\\d+)/(30[1237])/");

			private Pattern targetPattern = Pattern.compile("/target(/\\w+)/");

			@Override
			public void init(FilterConfig filterConfig)
					throws ServletException {
			}

			@Override
			public void doFilter(ServletRequest request
					ServletResponse response
					throws IOException
				final HttpServletResponse httpServletResponse = (HttpServletResponse) response;
				final HttpServletRequest httpServletRequest = (HttpServletRequest) request;
				final StringBuffer fullUrl = httpServletRequest.getRequestURL();
				if (httpServletRequest.getQueryString() != null) {
					fullUrl.append("?")
							.append(httpServletRequest.getQueryString());
				}
				String urlString = fullUrl.toString();
				if (urlString.contains("/loop/")) {
					urlString = urlString.replace("/loop/"
					if (urlString.contains("/loop/x/x/x/x/x/x/x/x/")) {
						urlString = urlString.replace("/loop/x/x/x/x/x/x/x/x/"
								"/loop/");
					}
					httpServletResponse.setStatus(
							HttpServletResponse.SC_MOVED_TEMPORARILY);
					httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
							urlString);
					return;
				}
				int responseCode = HttpServletResponse.SC_MOVED_PERMANENTLY;
				int nofRedirects = 0;
				Matcher matcher = responsePattern.matcher(urlString);
				if (matcher.find()) {
					nofRedirects = Integer
							.parseUnsignedInt(matcher.group(1));
					responseCode = Integer.parseUnsignedInt(matcher.group(2));
					if (--nofRedirects <= 0) {
						urlString = urlString.substring(0
								+ '/' + urlString.substring(matcher.end());
					} else {
						urlString = urlString.substring(0
								+ "/response/" + nofRedirects + "/"
								+ responseCode + '/'
								+ urlString.substring(matcher.end());
					}
				}
				httpServletResponse.setStatus(responseCode);
				if (nofRedirects <= 0) {
					String targetContext = "/git";
					matcher = targetPattern.matcher(urlString);
					if (matcher.find()) {
						urlString = urlString.substring(0
								+ '/' + urlString.substring(matcher.end());
						targetContext = matcher.group(1);
					}
					urlString = urlString.replace("/redirect"
				}
				httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
						urlString);
			}

			@Override
			public void destroy() {
			}
		})
		redirect.addServlet(new ServletHolder(gs)
		return redirect;
	}

	@Test
	public void testListRemote() throws IOException {
		assertEquals("http"

		Map<String
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertTrue("isa TransportHttp"
			assertTrue("isa HttpTransport"

			try (FetchConnection c = t.openFetch()) {
				map = c.getRefsMap();
			}
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

	@Test
	public void testListRemote_BadName() throws IOException
		URIish uri = new URIish(this.remoteURI.toString() + ".invalid");
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			try {
				t.openFetch();
				fail("fetch connection opened");
			} catch (RemoteRepositoryException notFound) {
				assertEquals(uri + ": Git repository not found"
						notFound.getMessage());
			}
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(uri
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testFetchBySHA1() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
					Collections.singletonList(new RefSpec(B.name())));
			assertTrue(dst.getObjectDatabase().has(A_txt));
		}
	}

	@Test
	public void testFetchBySHA1Unreachable() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + unreachableCommit.name() + " not valid"));
			t.fetch(NullProgressMonitor.INSTANCE
					.singletonList(new RefSpec(unreachableCommit.name())));
		}
	}

	@Test
	public void testFetchBySHA1UnreachableByAdvertiseRefsHook()
			throws Exception {
		advertiseRefsHook = new AbstractAdvertiseRefsHook() {
			@Override
			protected Map<String
					RevWalk revWalk) {
				return Collections.emptyMap();
			}
		};

		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			thrown.expect(TransportException.class);
			thrown.expectMessage(Matchers.containsString(
					"want " + A.name() + " not valid"));
			t.fetch(NullProgressMonitor.INSTANCE
					.singletonList(new RefSpec(A.name())));
		}
	}

	@Test
	public void testInitialClone_Small() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

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

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				.getResponseHeader(HDR_CONTENT_TYPE));
	}

	private void initialClone_Redirect(int nofRedirects
			throws Exception {
		URIish cloneFrom = redirectURI;
		if (code != 301 || nofRedirects > 1) {
			cloneFrom = extendPath(cloneFrom
					"/response/" + nofRedirects + "/" + code);
		}

		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(2 + nofRedirects

		int n = 0;
		while (n < nofRedirects) {
			AccessEvent redirect = requests.get(n++);
			assertEquals(code
		}

		AccessEvent info = requests.get(n++);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(n++);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInitialClone_Redirect301Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect302Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect303Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_Redirect307Small() throws Exception {
		initialClone_Redirect(1
	}

	@Test
	public void testInitialClone_RedirectMultiple() throws Exception {
		initialClone_Redirect(4
	}

	@Test
	public void testInitialClone_RedirectMax() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setInt("http"
		userConfig.save();
		initialClone_Redirect(4
	}

	@Test
	public void testInitialClone_RedirectTooOften() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setInt("http"
		userConfig.save();

		URIish cloneFrom = extendPath(redirectURI
		String remoteUri = cloneFrom.toString();
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (too many redirects)");
		} catch (TransportException e) {
			String expectedMessageBegin = remoteUri.toString() + ": "
					+ MessageFormat.format(JGitText.get().redirectLimitExceeded
							"3"
			String message = e.getMessage();
			if (message.length() > expectedMessageBegin.length()) {
				message = message.substring(0
			}
			assertEquals(expectedMessageBegin
		}
	}

	@Test
	public void testInitialClone_RedirectLoop() throws Exception {
		URIish cloneFrom = extendPath(redirectURI
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirect loop)");
		} catch (TransportException e) {
			assertTrue(e.getMessage().contains("Redirected more than"));
		}
	}

	@Test
	public void testInitialClone_RedirectOnPostAllowed() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setString("http"
		userConfig.save();

		URIish cloneFrom = extendPath(remoteURI
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(cloneFrom
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent redirect = requests.get(1);
		assertEquals("POST"
		assertEquals(301

		AccessEvent service = requests.get(2);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInitialClone_RedirectOnPostForbidden() throws Exception {
		URIish cloneFrom = extendPath(remoteURI
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirect on POST)");
		} catch (TransportException e) {
			assertTrue(e.getMessage().contains("301"));
		}
	}

	@Test
	public void testInitialClone_RedirectForbidden() throws Exception {
		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setString("http"
		userConfig.save();

		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirects forbidden)");
		} catch (TransportException e) {
			assertTrue(
					e.getMessage().contains("http.followRedirects is false"));
		}
	}

	@Test
	public void testInitialClone_WithAuthentication() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(401

		info = requests.get(1);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(2);
		assertEquals("POST"
		assertEquals(join(authURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInitialClone_WithAuthenticationNoCredentials()
			throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should not have succeeded -- no authentication");
		} catch (TransportException e) {
			String msg = e.getMessage();
			assertTrue("Unexpected exception message: " + msg
					msg.contains("no CredentialsProvider"));
		}
		List<AccessEvent> requests = getRequests();
		assertEquals(1

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(401
	}

	@Test
	public void testInitialClone_WithAuthenticationWrongCredentials()
			throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.setCredentialsProvider(new UsernamePasswordCredentialsProvider(
					AppServer.username
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should not have succeeded -- wrong password");
		} catch (TransportException e) {
			String msg = e.getMessage();
			assertTrue("Unexpected exception message: " + msg
					msg.contains("auth"));
		}
		List<AccessEvent> requests = getRequests();
		assertEquals(4

		for (AccessEvent event : requests) {
			assertEquals("GET"
			assertEquals(401
		}
	}

	@Test
	public void testInitialClone_WithAuthenticationAfterRedirect()
			throws Exception {
		URIish cloneFrom = extendPath(redirectURI
		CredentialsProvider uriSpecificCredentialsProvider = new UsernamePasswordCredentialsProvider(
				"unknown"
			@Override
			public boolean get(URIish uri
					throws UnsupportedCredentialItem {
				if (uri.getPath().startsWith("/auth")) {
					return testCredentials.get(uri
				}
				return super.get(uri
			}
		};
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.setCredentialsProvider(uriSpecificCredentialsProvider);
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(4

		AccessEvent redirect = requests.get(0);
		assertEquals("GET"
		assertEquals(join(cloneFrom
		assertEquals(301

		AccessEvent info = requests.get(1);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(401

		info = requests.get(2);
		assertEquals("GET"
		assertEquals(join(authURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(3);
		assertEquals("POST"
		assertEquals(join(authURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInitialClone_WithAuthenticationOnPostOnly()
			throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			t.setCredentialsProvider(testCredentials);
			t.fetch(NullProgressMonitor.INSTANCE
			assertTrue(dst.getObjectDatabase().has(A_txt));
			assertEquals(B
			fsck(dst
		}

		List<AccessEvent> requests = getRequests();
		assertEquals(3

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(authOnPostURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));
		assertEquals("gzip"

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(authOnPostURI
		assertEquals(401

		service = requests.get(2);
		assertEquals("POST"
		assertEquals(join(authOnPostURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testFetch_FewLocalCommits() throws Exception {
		TestRepository dst = createTestRepository();
		try (Transport t = Transport.open(dst.getRepository()
			t.fetch(NullProgressMonitor.INSTANCE
		}
		assertEquals(B
		List<AccessEvent> cloneRequests = getRequests();

		TestRepository.BranchBuilder b = dst.branch(master);
		for (int i = 0; i < 4; i++)

		RevCommit Z;
		try (TestRepository<Repository> tr = new TestRepository<>(
				remoteRepository)) {
			b = tr.branch(master);
			Z = b.commit().message("Z").create();
		}

		try (Transport t = Transport.open(dst.getRepository()
			t.fetch(NullProgressMonitor.INSTANCE
		}
		assertEquals(Z

		List<AccessEvent> requests = getRequests();
		requests.removeAll(cloneRequests);
		assertEquals(2

		AccessEvent info = requests.get(0);
		assertEquals("GET"
		assertEquals(join(remoteURI
		assertEquals(1
		assertEquals("git-upload-pack"
		assertEquals(200
		assertEquals("application/x-git-upload-pack-advertisement"
				info.getResponseHeader(HDR_CONTENT_TYPE));

		AccessEvent service = requests.get(1);
		assertEquals("POST"
		assertEquals(join(remoteURI
		assertEquals(0
		assertNotNull("has content-length"
				service.getRequestHeader(HDR_CONTENT_LENGTH));
		assertNull("not chunked"
				service.getRequestHeader(HDR_TRANSFER_ENCODING));

		assertEquals(200
		assertEquals("application/x-git-upload-pack-result"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testFetch_TooManyLocalCommits() throws Exception {
		TestRepository dst = createTestRepository();
		try (Transport t = Transport.open(dst.getRepository()
			t.fetch(NullProgressMonitor.INSTANCE
		}
		assertEquals(B
		List<AccessEvent> cloneRequests = getRequests();

		TestRepository.BranchBuilder b = dst.branch(master);
		for (int i = 0; i < 32 - 1; i++)

		RevCommit Z;
		try (TestRepository<Repository> tr = new TestRepository<>(
				remoteRepository)) {
			b = tr.branch(master);
			Z = b.commit().message("Z").create();
		}

		try (Transport t = Transport.open(dst.getRepository()
			t.fetch(NullProgressMonitor.INSTANCE
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

	@Test
	public void testInitialClone_BrokenServer() throws Exception {
		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
			assertFalse(dst.getObjectDatabase().has(A_txt));
			try {
				t.fetch(NullProgressMonitor.INSTANCE
				fail("fetch completed despite upload-pack being broken");
			} catch (TransportException err) {
				String exp = brokenURI + ": expected"
						+ " Content-Type application/x-git-upload-pack-result;"
						+ " received Content-Type text/plain;charset=utf-8";
				assertEquals(exp
			}
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
		assertEquals("text/plain;charset=utf-8"
				service.getResponseHeader(HDR_CONTENT_TYPE));
	}

	@Test
	public void testInvalidWant() throws Exception {
		ObjectId id;
		try (ObjectInserter.Formatter formatter = new ObjectInserter.Formatter()) {
			id = formatter.idFor(Constants.OBJ_BLOB
					"testInvalidWant".getBytes(UTF_8));
		}

		try (Repository dst = createBareRepository();
				Transport t = Transport.open(dst
				FetchConnection c = t.openFetch()) {
			Ref want = new ObjectIdRef.Unpeeled(Ref.Storage.NETWORK
					id);
			c.fetch(NullProgressMonitor.INSTANCE
					Collections.<ObjectId> emptySet());
			fail("Server accepted want " + id.name());
		} catch (TransportException err) {
			assertEquals("want " + id.name() + " not valid"
		}
	}

	@Test
	public void testFetch_RefsUnreadableOnUpload() throws Exception {
		AppServer noRefServer = new AppServer();
		try {
			final String repoName = "refs-unreadable";
			RefsUnreadableInMemoryRepository badRefsRepo = new RefsUnreadableInMemoryRepository(
					new DfsRepositoryDescription(repoName));
			final TestRepository<Repository> repo = new TestRepository<>(
					badRefsRepo);

			ServletContextHandler app = noRefServer.addContext("/git");
			GitServlet gs = new GitServlet();
			gs.setRepositoryResolver(new TestRepositoryResolver(repo
			app.addServlet(new ServletHolder(gs)
			noRefServer.setUp();

			RevBlob A2_txt = repo.blob("A2");
			RevCommit A2 = repo.commit().add("A2_txt"
			RevCommit B2 = repo.commit().parent(A2).add("A2_txt"
					.add("B2"
			repo.update(master

			URIish badRefsURI = new URIish(noRefServer.getURI()
					.resolve(app.getContextPath() + "/" + repoName).toString());

			try (Repository dst = createBareRepository();
					Transport t = Transport.open(dst
					FetchConnection c = t.openFetch()) {
				badRefsRepo.startFailing();
				badRefsRepo.getRefDatabase().refresh();
				c.fetch(NullProgressMonitor.INSTANCE
						Collections.singleton(c.getRef(master))
						Collections.<ObjectId> emptySet());
				fail("Successfully served ref with value " + c.getRef(master));
			} catch (TransportException err) {
				assertEquals("internal server error"
			}
		} finally {
			noRefServer.tearDown();
		}
	}

	@Test
	public void testPush_NotAuthorized() throws Exception {
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

			RemoteRefUpdate u = new RemoteRefUpdate(src.getRepository()
					srcExpr
			try {
				t.push(NullProgressMonitor.INSTANCE
				fail("anonymous push incorrectly accepted without error");
			} catch (TransportException e) {
				final String exp = remoteURI + ": "
						+ JGitText.get().authenticationNotSupported;
				assertEquals(exp
			}
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

	@Test
	public void testPush_CreateBranch() throws Exception {
		final TestRepository src = createTestRepository();
		final RevBlob Q_txt = src.blob("new text");
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";

		enableReceivePack();

		try (Transport t = Transport.open(db
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate u = new RemoteRefUpdate(src.getRepository()
					srcExpr
			t.push(NullProgressMonitor.INSTANCE
		}

		assertTrue(remoteRepository.getObjectDatabase().has(Q_txt));
		assertNotNull("has " + dstName
		assertEquals(Q
		fsck(remoteRepository

		final ReflogReader log = remoteRepository.getReflogReader(dstName);
		assertNotNull("has log for " + dstName

		final ReflogEntry last = log.getLastEntry();
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

	@Test
	public void testPush_ChunkedEncoding() throws Exception {
		final TestRepository<Repository> src = createTestRepository();
		final RevBlob Q_bin = src.blob(new TestRng("Q").nextBytes(128 * 1024));
		final RevCommit Q = src.commit().add("Q"
		final Repository db = src.getRepository();
		final String dstName = Constants.R_HEADS + "new.branch";

		enableReceivePack();

		final StoredConfig cfg = db.getConfig();
		cfg.setInt("core"
		cfg.setInt("http"
		cfg.save();

		try (Transport t = Transport.open(db
			final String srcExpr = Q.name();
			final boolean forceUpdate = false;
			final String localName = null;
			final ObjectId oldId = null;

			RemoteRefUpdate u = new RemoteRefUpdate(src.getRepository()
					srcExpr
			t.push(NullProgressMonitor.INSTANCE
		}

		assertTrue(remoteRepository.getObjectDatabase().has(Q_bin));
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
		final StoredConfig cfg = remoteRepository.getConfig();
		cfg.setBoolean("http"
		cfg.save();
	}

}

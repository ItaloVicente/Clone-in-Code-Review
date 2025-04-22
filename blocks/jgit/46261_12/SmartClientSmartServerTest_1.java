
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumSet;
import java.util.List;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.FilterHolder;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.errors.TransportException;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.junit.TestRepository;
import org.eclipse.jgit.junit.http.AccessEvent;
import org.eclipse.jgit.junit.http.AppServer;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.eclipse.jgit.lib.ConfigConstants;
import org.eclipse.jgit.lib.NullProgressMonitor;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevBlob;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileBasedConfig;
import org.eclipse.jgit.transport.HttpTransport;
import org.eclipse.jgit.transport.Transport;
import org.eclipse.jgit.transport.URIish;
import org.eclipse.jgit.transport.http.HttpConnectionFactory;
import org.eclipse.jgit.transport.http.JDKHttpConnectionFactory;
import org.eclipse.jgit.transport.http.apache.HttpClientConnectionFactory;
import org.eclipse.jgit.util.FS;
import org.eclipse.jgit.util.HttpSupport;
import org.eclipse.jgit.util.SystemReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SmartClientSmartServerSslTest extends HttpTestCase {

	private URIish remoteURI;

	private RevBlob A_txt;

	private RevCommit A

	@Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(new Object[][] {
				{ new JDKHttpConnectionFactory() }
				{ new HttpClientConnectionFactory() } });
	}

	public SmartClientSmartServerSslTest(HttpConnectionFactory cf) {
		HttpTransport.setConnectionFactory(cf);
	}

	@Override
	protected AppServer createServer() {
		return new AppServer(0
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

		ServletContextHandler app = addNormalContext(gs

		server.setUp();

		remoteURI = toURIish(app

		A_txt = src.blob("A");
		A = src.commit().add("A_txt"
		B = src.commit().parent(A).add("A_txt"
		src.update(master

		src.update("refs/garbage/a/very/long/ref/name/to/compress"

		FileBasedConfig userConfig = SystemReader.getInstance()
				.openUserConfig(null
		userConfig.setBoolean("http"
		userConfig.save();
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
				urlString = "https" + urlString.substring(schemeEnd);
				String port = ":" + server.getPort();
				urlString = urlString.replace(port
						":" + server.getSecurePort());
				httpServletResponse
						.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
						urlString.replace("/https/"
			}

			@Override
			public void destroy() {
			}
		})
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
				urlString = "http" + urlString.substring(schemeEnd);
				String port = ":" + server.getSecurePort();
				urlString = urlString.replace(port
				httpServletResponse
						.setStatus(HttpServletResponse.SC_MOVED_PERMANENTLY);
				httpServletResponse.setHeader(HttpSupport.HDR_LOCATION
						urlString.replace("/back/"
			}

			@Override
			public void destroy() {
			}
		})
		gs.setRepositoryResolver(new TestRepositoryResolver(src
		app.addServlet(new ServletHolder(gs)
		return app;
	}

	@Test
	public void testInitialClone_ViaHttps() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		String remoteUri = remoteURI.toString();
		remoteUri = "https" + remoteUri.substring(schemeEnd);
		String port = ":" + server.getPort();
		remoteUri = remoteUri.replace(port
		URIish cloneFrom = new URIish(remoteUri);
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}
		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(2
	}

	@Test
	public void testInitialClone_RedirectToHttps() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		String remoteUri = remoteURI.toString();
		int i = remoteUri.lastIndexOf('/');
		remoteUri = remoteUri.substring(0
				+ remoteUri.substring(i);
		URIish cloneFrom = new URIish(remoteUri);
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
		}
		assertTrue(dst.hasObject(A_txt));
		assertEquals(B
		fsck(dst

		List<AccessEvent> requests = getRequests();
		assertEquals(3
	}

	@Test
	public void testInitialClone_RedirectBackToHttp() throws Exception {
		Repository dst = createBareRepository();
		assertFalse(dst.hasObject(A_txt));

		String remoteUri = remoteURI.toString();
		remoteUri = "https" + remoteUri.substring(schemeEnd);
		String port = ":" + server.getPort();
		remoteUri = remoteUri.replace(port
		int i = remoteUri.lastIndexOf('/');
		remoteUri = remoteUri.substring(0
				+ remoteUri.substring(i);
		URIish cloneFrom = new URIish(remoteUri);
		try (Transport t = Transport.open(dst
			t.fetch(NullProgressMonitor.INSTANCE
			fail("Should have failed (redirect from https to http)");
		} catch (TransportException e) {
			assertTrue(e.getMessage().contains("not allowed"));
		}
	}

}

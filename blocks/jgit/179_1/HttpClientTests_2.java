
package org.eclipse.jgit.http.test;

import java.util.List;

import javax.servlet.ServletException;

import junit.framework.TestCase;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.GitServlet;
import org.eclipse.jgit.http.test.util.AppServer;
import org.eclipse.jgit.http.test.util.MockServletConfig;
import org.eclipse.jgit.http.test.util.RecordingLogger;

public class GitServletInitTest extends TestCase {
	private AppServer server;

	protected void tearDown() throws Exception {
		if (server != null) {
			server.tearDown();
			server = null;
		}
		super.tearDown();
	}

	public void testDefaultConstructor_NoBasePath() throws Exception {
		GitServlet s = new GitServlet();
		try {
			s.init(new MockServletConfig());
			fail("Init did not crash due to missing parameter");
		} catch (ServletException e) {
			assertTrue(e.getMessage().contains("base-path"));
		}
	}

	public void testDefaultConstructor_WithBasePath() throws Exception {
		MockServletConfig c = new MockServletConfig();
		c.setInitParameter("base-path"

		GitServlet s = new GitServlet();
		s.init(c);
		s.destroy();
	}

	public void testInitUnderContainer_NoBasePath() throws Exception {
		server = new AppServer();

		ServletContextHandler app = server.addContext("/");
		ServletHolder s = app.addServlet(GitServlet.class
		s.setInitOrder(1);

		server.setUp();

		List<RecordingLogger.Warning> events = RecordingLogger.getWarnings();
		assertFalse("Servlet started without base-path"

		Throwable why = events.get(0).getCause();
		assertTrue("Caught ServletException"
		assertTrue("Wanted base-path"
	}

	public void testInitUnderContainer_WithBasePath() throws Exception {
		server = new AppServer();

		ServletContextHandler app = server.addContext("/");
		ServletHolder s = app.addServlet(GitServlet.class
		s.setInitOrder(1);
		s.setInitParameter("base-path"

		server.setUp();
	}
}

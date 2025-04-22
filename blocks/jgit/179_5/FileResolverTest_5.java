
package org.eclipse.jgit.http.test;

import java.net.HttpURLConnection;
import java.net.URI;

import junit.framework.TestCase;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.glue.ErrorServlet;
import org.eclipse.jgit.http.test.util.AppServer;

public class ErrorServletTest extends TestCase {
	private AppServer server;

	protected void setUp() throws Exception {
		super.setUp();

		server = new AppServer();

		ServletContextHandler ctx = server.addContext("/");
		ctx.addServlet(new ServletHolder(new ErrorServlet(404))
		ctx.addServlet(new ServletHolder(new ErrorServlet(500))

		server.setUp();
	}

	protected void tearDown() throws Exception {
		if (server != null) {
			server.tearDown();
		}
		super.tearDown();
	}

	public void testHandler() throws Exception {
		final URI uri = server.getURI();
		assertEquals(404
				.openConnection()).getResponseCode());
		assertEquals(500
				.openConnection()).getResponseCode());
	}
}

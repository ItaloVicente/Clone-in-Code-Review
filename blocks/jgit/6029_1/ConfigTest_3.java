
package org.eclipse.jgit.http.test;

import static org.junit.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URI;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jgit.http.server.glue.MetaServlet;
import org.eclipse.jgit.http.server.glue.RegexGroupFilter;
import org.eclipse.jgit.junit.http.AppServer;
import org.eclipse.jgit.junit.http.HttpTestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class RegexPipelineTest extends HttpTestCase {
	private ServletContextHandler ctx;

	private static class Servlet extends HttpServlet {
		private static final long serialVersionUID = 1L;

		private final String name;

		private Servlet(String name) {
			this.name = name;
		}

		@Override
		protected void doGet(HttpServletRequest req
				throws IOException {
			res.setStatus(200);
			PrintWriter out = new PrintWriter(res.getOutputStream());
			out.write(name);
			out.write("\n");
			out.write(String.valueOf(req.getServletPath()));
			out.write("\n");
			out.write(String.valueOf(req.getPathInfo()));
			out.write("\n");
			out.flush();
		}
	}

	@Before
	public void setUp() throws Exception {
		server = new AppServer();
		ctx = server.addContext("/");
	}

	@After
	public void tearDown() throws Exception {
		server.tearDown();
	}

	@Test
	public void testSimpleRegex() throws Exception {
		MetaServlet s = new MetaServlet();
		s.serveRegex("^(/a|/b)$").with(new Servlet("test"));
		ctx.addServlet(new ServletHolder(s)
		server.setUp();

		final URI uri = server.getURI();
		HttpURLConnection c;
		BufferedReader r;

		c = ((HttpURLConnection) uri.resolve("/a").toURL()
				.openConnection());
		assertEquals(200
		r = new BufferedReader(new InputStreamReader(c.getInputStream()));
		assertEquals("test"
		assertEquals(""
		assertEquals("/a"
		assertEquals(null

		c = ((HttpURLConnection) uri.resolve("/b").toURL()
				.openConnection());
		assertEquals(200
		r = new BufferedReader(new InputStreamReader(c.getInputStream()));
		assertEquals("test"
		assertEquals(""
		assertEquals("/b"
		assertEquals(null

		c = ((HttpURLConnection) uri.resolve("/c").toURL()
				.openConnection());
		assertEquals(404
	}

	@Test
	public void testServeOrdering() throws Exception {
		MetaServlet s = new MetaServlet();
		s.serveRegex("^(/a)$").with(new Servlet("test1"));
		s.serveRegex("^(/a+)$").with(new Servlet("test2"));
		ctx.addServlet(new ServletHolder(s)
		server.setUp();

		final URI uri = server.getURI();
		HttpURLConnection c;
		BufferedReader r;

		c = ((HttpURLConnection) uri.resolve("/a").toURL()
				.openConnection());
		assertEquals(200
		r = new BufferedReader(new InputStreamReader(c.getInputStream()));
		assertEquals("test1"
		assertEquals(""
		assertEquals("/a"
		assertEquals(null
	}

	@Test
	public void testRegexGroupFilter() throws Exception {
		MetaServlet s = new MetaServlet();
		s.serveRegex("^(/a)(/b)$")
				.with(new Servlet("test1"));
		s.serveRegex("^(/c)(/d)$")
				.through(new RegexGroupFilter(1))
				.with(new Servlet("test2"));
		s.serveRegex("^(/e)/f(/g)$")
				.through(new RegexGroupFilter(2))
				.with(new Servlet("test3"));
		ctx.addServlet(new ServletHolder(s)
		server.setUp();

		final URI uri = server.getURI();
		HttpURLConnection c;
		BufferedReader r;

		c = ((HttpURLConnection) uri.resolve("/a/b").toURL()
				.openConnection());
		assertEquals(200
		r = new BufferedReader(new InputStreamReader(c.getInputStream()));
		assertEquals("test1"
		assertEquals(""
		assertEquals("/a"
		assertEquals(null

		c = ((HttpURLConnection) uri.resolve("/c/d").toURL()
				.openConnection());
		assertEquals(200
		r = new BufferedReader(new InputStreamReader(c.getInputStream()));
		assertEquals("test2"
		assertEquals(""
		assertEquals("/c"
		assertEquals(null

		c = ((HttpURLConnection) uri.resolve("/e/f/g").toURL()
				.openConnection());
		assertEquals(200
		r = new BufferedReader(new InputStreamReader(c.getInputStream()));
		assertEquals("test3"
		assertEquals("/e/f"
		assertEquals("/g"
		assertEquals(null
	}
}
